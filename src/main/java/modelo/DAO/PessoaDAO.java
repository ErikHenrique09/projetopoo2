package modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.VO.Pessoa;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

@SuppressWarnings("ALL")
public class PessoaDAO implements CRUD<Pessoa> {
    //PessoaRN pesRN = new PessoaRN();
    EntityManager entityManager;

    public PessoaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public boolean validaEmail(String email) {
        try {
            this.entityManager.createQuery(String.format("SELECT p FROM Pessoa p WHERE email = '%s'", email), Pessoa.class).getSingleResult();
            System.out.println("Caiu aqui e o email ja existe, logo falso: invalido");
            return false;
        } catch (NoResultException nre) {
            System.out.println("Caiu aqui e o emai não existe, logo true: valido");
            return true;
        }
    }

    public boolean validaUpdateEmail(Pessoa pessoa) {
        try {
            this.entityManager.createQuery(String.format("SELECT p FROM Pessoa p WHERE p.email = '%s' AND p.idPessoa != %d", pessoa.getEmail(), pessoa.getIdPessoa()), Pessoa.class).getSingleResult();
            System.out.println("Caiu aqui e o email ja existe um fora o da pessoa, logo falso: invalido");
            return false;
        } catch (NoResultException nre) {
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
        return this.entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.id =" + id, Pessoa.class).getSingleResult();
    }

    public void deletById(Integer id) {

        EntityTransaction transaction = this.entityManager.getTransaction();

        transaction.begin();

        entityManager.createQuery("DELETE FROM Funcionario f WHERE f.pessoa.id = " + id).executeUpdate();
        entityManager.createQuery("DELETE FROM Pessoa p WHERE p.id = " + id).executeUpdate();

        transaction.commit();

    }

}

