package modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Pedido;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class PedidoDAO implements CRUD<Pedido> {

    EntityManager entityManager;

    public PedidoDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public List<Pedido> findAllPendents() {
        return this.entityManager.createQuery("SELECT pd FROM Pedido pd WHERE fimPedido IS NULL ORDER BY iniPedido", Pedido.class).getResultList();
    }

    @Override
    public void save(Pedido pedido) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(pedido);

        transaction.commit();
    }

    @Override
    public void update(Pedido pedido) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(pedido);

        transaction.commit();
    }

    @Override
    public void delete(Pedido pedido) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Pedido pedidoExcluir = this.entityManager.merge(pedido);
        this.entityManager.remove(pedidoExcluir);

        transaction.commit();
    }

    @Override
    public Pedido find(Integer id) {
        return this.entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.id =" + id, Pedido.class).getSingleResult();
    }

    @Override
    public List<Pedido> findAll() {
        return this.entityManager.createQuery("SELECT pd FROM Pedido pd", Pedido.class).getResultList();
    }

}

