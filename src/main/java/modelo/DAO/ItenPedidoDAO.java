package modelo.DAO;

import modelo.VO.ItenPedido;

import jakarta.persistence.*;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class ItenPedidoDAO implements CRUD<ItenPedido> {

    EntityManager entityManager;

    public ItenPedidoDAO() { this.entityManager = ConexaoHibernate.getInstance(); }

    @Override
    public void save(ItenPedido itenPedido) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(itenPedido);

        transaction.commit();
    }

    @Override
    public void update(ItenPedido itenPedido) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(itenPedido);

        transaction.commit();
    }

    @Override
    public void delete(ItenPedido itenPedido) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        ItenPedido itenPedidoExcluir = this.entityManager.merge(itenPedido);
        this.entityManager.remove(itenPedidoExcluir);

        transaction.commit();
    }

    @Override
    public ItenPedido find(Integer id) {
        return this.entityManager.createQuery("SELECT ip FROM ItenPedido ip WHERE ip.id ="+id, ItenPedido.class).getSingleResult();
    }

    @Override
    public List<ItenPedido> findAll() {
        return this.entityManager.createQuery("SELECT i FROM ItenPedido i", ItenPedido.class).getResultList();
    }
}

