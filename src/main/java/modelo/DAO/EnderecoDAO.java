package modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Endereco;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class EnderecoDAO implements CRUD<Endereco> {

    EntityManager entityManager;

    public EnderecoDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    @Override
    public void save(Endereco endereco) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(endereco);

        transaction.commit();
    }

    @Override
    public void update(Endereco endereco) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(endereco);

        transaction.commit();
    }

    @Override
    public void delete(Endereco endereco) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Endereco pessoaExcluir = this.entityManager.merge(endereco);
        this.entityManager.remove(pessoaExcluir);

        transaction.commit();
    }

    @Override
    public Endereco find(Integer id) {
        return this.entityManager.createQuery("SELECT e FROM Endereco e WHERE e.idEndereco = " + id, Endereco.class).getSingleResult();
    }

    @Override
    public List<Endereco> findAll() {
        return this.entityManager.createQuery("SELECT e FROM Endereco e", Endereco.class).getResultList();
    }

}

