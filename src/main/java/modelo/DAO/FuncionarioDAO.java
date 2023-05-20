package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Funcionario;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class FuncionarioDAO implements CRUD<Funcionario> {

    EntityManager entityManager;

    public FuncionarioDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public void assignFunction() {

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
        Funcionario func = this.entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.id =" + id, Funcionario.class).getSingleResult();

        System.out.println(func.getPessoa().getNome());

        return func;
    }

    @Override
    public List<Funcionario> findAll() {
        return this.entityManager.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
    }

    public JsonArray exibir() {
        return new Gson().fromJson(
                this.entityManager.createQuery(
                        "SELECT " +
                                "JSON_ARRAYAGG( " +
                                "JSON_OBJECT( " +
                                "'idFuncionario', f.idFunc, " +
                                "'idPessoa', p.idPessoa, " +
                                "'nome', p.nome, " +
                                "'email', p.email, " +
                                "'idade', p.email, " +
                                "'func', f.func, " +
                                "'tel1', p.tel1, " +
                                "'tel2', p.tel2 " +
                                ")" +
                                ")" +
                                "FROM Funcionario f " +
                                "INNER JOIN Pessoa p on p.idPessoa = f.pessoa.id ", String.class).getSingleResult(),
                JsonArray.class);
    }

}

