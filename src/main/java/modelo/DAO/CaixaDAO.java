package modelo.DAO;

import modelo.VO.Caixa;

import jakarta.persistence.*;
import util.CRUD;
import util.ConexaoHibernate;
import java.time.LocalDateTime;
import com.google.gson.*;
import java.util.List;

public class CaixaDAO implements CRUD<Caixa> {

    EntityManager entityManager;

    public CaixaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public boolean validaCaixa(Caixa caixa){

        try {
            Caixa c = this.entityManager.createQuery("SELECT c FROM Caixa c WHERE DATE(c.date) = '" + LocalDateTime.now().toLocalDate() + "'", Caixa.class).getSingleResult();
            return false;
        }catch(NoResultException nre){
            return true;
        }

    }

    public void exibirDetalhes(Integer idMesa){

        JsonArray detalhes = new Gson().fromJson(
            this.entityManager.createQuery(
        "SELECT " +
                    "JSON_ARRAYAGG( " +
                        "JSON_OBJECT( "+
                            "'pedido', c.titulo, " +
                            "'valunit', round(c.val,2), " +
                            "'qtd', ip.qtd, " +
                            "'ValorFinal', round((c.val*ip.qtd),2) " +
                        ")" +
                    ")" +
                "FROM Pedido p " +
                "INNER JOIN ItenPedido ip on ip.pedido.id = p.idPedido " +
                "INNER JOIN Cardapio c on c.idCardapio = ip.cardapio.id " +
                "WHERE p.mesa.id = " +idMesa, String.class).getSingleResult(),
            JsonArray.class);

        System.out.println("-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=");

        for (JsonElement pedido: detalhes.getAsJsonArray()) {
            System.out.println(pedido);
        }

        System.out.println("Valor Total: "+calcularTodos(idMesa));

    }

    public double calcularTodos(Integer idMesa){
        return this.entityManager.createQuery("SELECT " +
                "round(sum(c.val*ip.qtd),2) val " +
                "FROM Pedido p " +
                "INNER JOIN ItenPedido ip on ip.pedido.id = p.idPedido " +
                "INNER JOIN Cardapio c on c.idCardapio = ip.cardapio.id " +
                "WHERE p.mesa.id = " +idMesa, double.class).getSingleResult();
    }

    public float calcularUnico(Integer idItenPedido){
        return this.entityManager.createQuery("SELECT " +
                "round(c.val*ip.qtd,2) val " +
                "FROM ItenPedido ip " +
                "INNER JOIN Cardapio c on c.idCardapio = ip.cardapio.id " +
                "WHERE ip.id = " +idItenPedido, float.class).getSingleResult();
    }

    public float calcularTroco(float valTotal, float valRecebido){
        double troco = Math.round((valRecebido-valTotal) * 100.0) / 100.0;

        return (float) Math.abs(troco);
    }

    @Override
    public void save(Caixa caixa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(caixa);

        transaction.commit();
    }

    @Override
    public void update(Caixa caixa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(caixa);

        transaction.commit();
    }

    @Override
    public void delete(Caixa caixa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Caixa caixaExcluir = this.entityManager.merge(caixa);
        this.entityManager.remove(caixaExcluir);

        transaction.commit();
    }

    @Override
    public Caixa find(Integer id) {
        return this.entityManager.createQuery("SELECT c FROM Caixa c WHERE c.id = " +id, Caixa.class).getSingleResult();
    }

    @Override
    public List<Caixa> findAll() {
        return this.entityManager.createQuery("SELECT p FROM Caixa p", Caixa.class).getResultList();
    }
}

