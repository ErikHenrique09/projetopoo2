package modelo.DAO;

import modelo.VO.Funcionario;

import jakarta.persistence.*;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class FuncionarioDAO implements CRUD<Funcionario> {

    EntityManager entityManager;

    public FuncionarioDAO() { this.entityManager = ConexaoHibernate.getInstance(); }

    public void assignFunction(){

    }

    @Override
    public void save(Funcionario funcionario) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(funcionario);

        transaction.commit();
    }

    @Override
    public void update(Funcionario funcionario) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(funcionario);

        transaction.commit();
    }

    @Override
    public void delete(Funcionario funcionario) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Funcionario funcionarioExcluir = this.entityManager.merge(funcionario);
        this.entityManager.remove(funcionarioExcluir);

        transaction.commit();
    }

    @Override
    public Funcionario find(Integer id) {
        return this.entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.id ="+id, Funcionario.class).getSingleResult();
    }

    @Override
    public List<Funcionario> findAll() {
        return this.entityManager.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
    }

}

