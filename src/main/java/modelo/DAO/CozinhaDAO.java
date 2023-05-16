package modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Cozinha;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class CozinhaDAO implements CRUD<Cozinha> {

    EntityManager entityManager;

    public CozinhaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    @Override
    public void save(Cozinha cozinha) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(cozinha);

        transaction.commit();
    }

    @Override
    public void update(Cozinha cozinha) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(cozinha);

        transaction.commit();
    }

    @Override
    public void delete(Cozinha cozinha) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Cozinha cozinhaExcluir = this.entityManager.merge(cozinha);
        this.entityManager.remove(cozinhaExcluir);

        transaction.commit();
    }

    @Override
    public Cozinha find(Integer id) {
        return this.entityManager.createQuery("SELECT c FROM Cozinha c WHERE c.id ="+id, Cozinha.class).getSingleResult();
    }

    @Override
    public List<Cozinha> findAll() {
        return this.entityManager.createQuery("SELECT c FROM Cozinha c", Cozinha.class).getResultList();
    }
}
