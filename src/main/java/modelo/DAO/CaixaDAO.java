package modelo.DAO;

import com.google.gson.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.ConexaoHibernate;

import java.util.List;
import java.util.stream.Collectors;

public class CaixaDAO{

    EntityManager entityManager;

    public CaixaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public List<JsonObject> exibirDetalhes(String id, String numMesa) {
        String queryString = "SELECT " +
                                "JSON_OBJECT(" +
                                    "'idMesa', m.idMesa, " +
                                    "'numMesa', m.numero, " +
                                    "'hrChegada', CONCAT(HOUR(m.iniMesa), ':', MINUTE(m.iniMesa), ':', round(SECOND(m.iniMesa),0)), " +
                                    "'pedidos', JSON_ARRAYAGG(" +
                                    "JSON_OBJECT(" +
                                        "'idItenPedido', i.idItenPedido, " +
                                        "'produto', c.titulo, " +
                                        "'quantidade', i.qtd, " +
                                        "'valor', c.val " +
                                        ")" +
                                    ")" +
                                ")" +
                            "FROM Mesa m " +
                                "INNER JOIN Pedido p ON m.idMesa = p.mesa.id " +
                                "INNER JOIN ItenPedido i ON p.idPedido = i.pedido.id " +
                                "INNER JOIN Cardapio c ON i.cardapio.id = c.idCardapio " +
                            "WHERE m.fimMesa IS NULL";

        if (id != null)
            queryString += " AND m.idMesa = :idMesa";

        if(numMesa != null)
            queryString += " AND m.numero = :num";

        queryString += " GROUP BY m.idMesa";

        TypedQuery<JsonObject> query = entityManager.createQuery(queryString, JsonObject.class);

        if (id != null)
            query.setParameter("idMesa", id);

        if(numMesa != null)
            query.setParameter("num", numMesa);

        System.out.println("Dale query: numero da mesa: "+numMesa);
        return query.getResultStream().collect(Collectors.toList());

    }

    public void pagarUnico(String idMesa, String idItenPedido, String qtd){

        EntityTransaction transaction = this.entityManager.getTransaction();

        AdminDAO adm = new AdminDAO();

        Integer q = (Integer) this.entityManager.createQuery("SELECT qtd from ItenPedido i WHERE i.idItenPedido = "+idItenPedido).getSingleResult();

        if( Integer.parseInt(qtd) > q)
            adm.insertVenda(idMesa, idItenPedido,q);
        else
            adm.insertVenda(idMesa, idItenPedido, Integer.parseInt(qtd));

        System.out.println("Quantidade do banco: "+ q);
        System.out.println("Quantidade enviada: "+ qtd);
        transaction.begin();
        entityManager.createQuery(
    "UPDATE ItenPedido i " +
                "SET i.qtd = CASE " +
                "    WHEN (" + Integer.parseInt(qtd) + " >= "+q+") THEN 0 " +
                "    WHEN (" + Integer.parseInt(qtd) + " < "+q+") THEN "+q+" - " + qtd +
                " END, " +
                "i.status = CASE " +
                "    WHEN (" + Integer.parseInt(qtd) + " >= "+q+") THEN 0 " +
                "    ELSE i.status " +
                " END " +
                "WHERE i.idItenPedido = " + idItenPedido + " AND i.status = 1")
            .executeUpdate();

        transaction.commit();

        transaction.begin();
        entityManager.createQuery("DELETE FROM ItenPedido i WHERE i.status = 0").executeUpdate();
        transaction.commit();
    }

    public void pagarTodos(String idMesa){
        AdminDAO admin = new AdminDAO();

        List ids = this.entityManager.createQuery("SELECT i.idItenPedido FROM ItenPedido i JOIN i.pedido p JOIN p.mesa m WHERE m.idMesa = "+idMesa).getResultList();
        System.out.println(ids);
        //exit(0);

        EntityTransaction transaction = this.entityManager.getTransaction();

        //Pra salvar quando encerrou a mesa
        transaction.begin();
        entityManager.createQuery(" UPDATE Mesa m "+ " SET m.fimMesa = CURRENT_DATE " + " WHERE m.idMesa = "+idMesa).executeUpdate();
        transaction.commit();

        // registrar a venda
        admin.insertVendas(idMesa);

        //Excluir as recorrencias
        transaction.begin();
        // Excluir os itens de pedido dos pedidos da mesa
        for (Object id: ids) {
            entityManager.createQuery("DELETE FROM ItenPedido ip WHERE ip.idItenPedido = " + id).executeUpdate();
        }
        // Excluir os pedidos da mesa
        entityManager.createQuery("DELETE FROM Pedido p WHERE p.mesa.id = "+idMesa).executeUpdate();
        // Atualizar a coluna fimMesa da mesa para a data atual
        entityManager.createQuery("DELETE FROM Mesa m WHERE m.idMesa = "+idMesa).executeUpdate();
        transaction.commit();

    }

}

