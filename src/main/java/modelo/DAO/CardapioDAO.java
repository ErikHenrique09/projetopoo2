package modelo.DAO;

import com.google.gson.JsonArray;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Cardapio;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class CardapioDAO implements CRUD<Cardapio> {

    EntityManager entityManager;

    public CardapioDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

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
        return this.entityManager.createQuery("SELECT c FROM Cardapio c WHERE c.id =" + id, Cardapio.class).getSingleResult();
    }

    @Override
    public List<Cardapio> findAll() {
        return this.entityManager.createQuery("SELECT c FROM Cardapio c", Cardapio.class).getResultList();
    }

    public List<JsonArray> getCardapio(){
       return  this.entityManager.createQuery(
            "SELECT " +
                        "json_object('categoria', c.categoria,'produtos', json_arrayagg(JSON_OBJECT('idItenCardapio', c.idCardapio,'titulo', c.titulo))) "+
                    "FROM Cardapio c "+
                    "group by c.categoria", JsonArray.class).getResultList();
    }

}

