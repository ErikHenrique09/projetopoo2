package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import modelo.VO.Caixa;
import util.CRUD;
import util.ConexaoHibernate;

import java.time.LocalDateTime;
import java.util.List;

public class CaixaDAO implements CRUD<Caixa> {

    EntityManager entityManager;

    public CaixaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public boolean validaCaixa(Caixa caixa) {

        try {
            Caixa c = this.entityManager.createQuery("SELECT c FROM Caixa c WHERE DATE(c.date) = '" + LocalDateTime.now().toLocalDate() + "'", Caixa.class).getSingleResult();
            return false;
        } catch (NoResultException nre) {
            return true;
        }

    }

    public JsonArray exibirDetalhes(String id) {
        if (id == null) {
            return new Gson().fromJson(
                    this.entityManager.createQuery(
                            "SELECT JSON_OBJECT(" +
                                    "'idMesa', m.idMesa, " +
                                    "'numMesa', m.numero, " +
                                    "'hrChegada', concat(hour(m.iniMesa), ':', minute(m.iniMesa), ':', second(m.iniMesa)), " +
                                    "'pedidos', json_arrayagg(" +
                                    "json_object(" +
                                    "'idItenPedido', i.idItenPedido, " +
                                    "'produto', c.titulo, " +
                                    "'quantidade', i.qtd, " +
                                    "'valor', c.val " +
                                    ")" +
                                    ")" +
                                    ")" +
                                    "FROM Mesa m " +
                                    "INNER JOIN Pedido p on m.idMesa = p.mesa.id " +
                                    "INNER JOIN ItenPedido i on p.idPedido = i.pedido.id " +
                                    "INNER JOIN Cardapio c on i.cardapio.id = c.idCardapio " +
                                    "WHERE m.fimMesa IS NOT NULL GROUP BY m.idMesa", JsonArray.class).getResultList().toString(),
                    JsonArray.class);
        }else{
            return new Gson().fromJson(
                    this.entityManager.createQuery(
                            "SELECT JSON_OBJECT(" +
                                    "'idMesa', m.idMesa, " +
                                    "'numMesa', m.numero, " +
                                    "'hrChegada', concat(hour(m.iniMesa), ':', minute(m.iniMesa), ':', second(m.iniMesa)), " +
                                    "'pedidos', json_arrayagg(" +
                                    "json_object(" +
                                    "'idItenPedido', i.idItenPedido, " +
                                    "'produto', c.titulo, " +
                                    "'quantidade', i.qtd, " +
                                    "'valor', c.val " +
                                    ")" +
                                    ")" +
                                    ")" +
                                    "FROM Mesa m " +
                                    "INNER JOIN Pedido p on m.idMesa = p.mesa.id " +
                                    "INNER JOIN ItenPedido i on p.idPedido = i.pedido.id " +
                                    "INNER JOIN Cardapio c on i.cardapio.id = c.idCardapio " +
                                    "WHERE m.fimMesa IS NOT NULL AND m.numero = "+id+" GROUP BY m.idMesa", JsonArray.class).getResultList().toString(),
                    JsonArray.class);
            }
    }

    public double calcularTodos(Integer idMesa) {
        return this.entityManager.createQuery("SELECT " +
                "round(sum(c.val*ip.qtd),2) val " +
                "FROM Pedido p " +
                "INNER JOIN ItenPedido ip on ip.pedido.id = p.idPedido " +
                "INNER JOIN Cardapio c on c.idCardapio = ip.cardapio.id " +
                "WHERE p.mesa.id = " + idMesa, double.class).getSingleResult();
    }

    public float calcularUnico(Integer idItenPedido) {
        return this.entityManager.createQuery("SELECT " +
                "round(c.val*ip.qtd,2) val " +
                "FROM ItenPedido ip " +
                "INNER JOIN Cardapio c on c.idCardapio = ip.cardapio.id " +
                "WHERE ip.id = " + idItenPedido, float.class).getSingleResult();
    }

    public float calcularTroco(float valTotal, float valRecebido) {
        double troco = Math.round((valRecebido - valTotal) * 100.0) / 100.0;

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
        return this.entityManager.createQuery("SELECT c FROM Caixa c WHERE c.id = " + id, Caixa.class).getSingleResult();
    }

    @Override
    public List<Caixa> findAll() {
        return this.entityManager.createQuery("SELECT p FROM Caixa p", Caixa.class).getResultList();
    }
}

