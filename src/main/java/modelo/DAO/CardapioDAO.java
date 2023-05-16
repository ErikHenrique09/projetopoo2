package modelo.DAO;

import modelo.VO.Cardapio;

import jakarta.persistence.*;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class CardapioDAO implements CRUD<Cardapio> {

    EntityManager entityManager;

    public CardapioDAO() { this.entityManager = ConexaoHibernate.getInstance(); }

    @Override
    public void save(Cardapio cardapio) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(cardapio);

        transaction.commit();
    }

    @Override
    public void update(Cardapio cardapio) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(cardapio);

        transaction.commit();
    }

    @Override
    public void delete(Cardapio cardapio) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Cardapio cardapioExcluir = this.entityManager.merge(cardapio);
        this.entityManager.remove(cardapioExcluir);

        transaction.commit();
    }

    @Override
    public Cardapio find(Integer id) {
        return this.entityManager.createQuery("SELECT c FROM Cardapio c WHERE c.id ="+id, Cardapio.class).getSingleResult();
    }

    @Override
    public List<Cardapio> findAll() {
        return this.entityManager.createQuery("SELECT c FROM Cardapio c", Cardapio.class).getResultList();
    }
}

