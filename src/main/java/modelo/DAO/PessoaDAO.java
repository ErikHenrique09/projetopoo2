package modelo.DAO;

import modelo.RN.PessoaRN;
import modelo.VO.Pessoa;

import jakarta.persistence.*;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class PessoaDAO implements CRUD<Pessoa> {
    //PessoaRN pesRN = new PessoaRN();
    EntityManager entityManager;

    public PessoaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public boolean validaEmail(String email){

        try{
            this.entityManager.createQuery("SELECT p FROM Pessoa p WHERE email = '"+ email+"'", Pessoa.class).getSingleResult();
            System.out.println("Caiu aqui e o email ja existe, logo falso: invalido");
            return false;
        }catch(NoResultException nre){
            System.out.println("Caiu aqui e o emai não existe, logo true: valido");
            return true;
        }



    }

    @Override
    public void save(Pessoa pessoa) throws Exception {

        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(pessoa);

        transaction.commit();

    }

    public Pessoa findByEmailSenha(String email, String senha) throws Exception {
        Pessoa p;
        TypedQuery<Pessoa> query = this.entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.email = :email AND p.senha = :senha", Pessoa.class);
        query.setParameter("email", email);
        query.setParameter("senha", senha);

        return query.getSingleResult();
    }

    @Override
    public void update(Pessoa pessoa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(pessoa);

        transaction.commit();
    }

    @Override
    public void delete(Pessoa pessoa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Pessoa pessoaExcluir = this.entityManager.merge(pessoa);
        this.entityManager.remove(pessoaExcluir);

        transaction.commit();
    }

    @Override
    public List<Pessoa> findAll() {
        return this.entityManager.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
    }

    @Override
    public Pessoa find(Integer id) {
        return this.entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.id ="+id, Pessoa.class).getSingleResult();
    }
}

