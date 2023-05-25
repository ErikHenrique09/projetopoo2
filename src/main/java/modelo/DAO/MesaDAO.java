package modelo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Mesa;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class MesaDAO implements CRUD<Mesa> {

    EntityManager entityManager;

    public MesaDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    @Override
    public void save(Mesa mesa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(mesa);

        transaction.commit();
    }

    @Override
    public void update(Mesa mesa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.merge(mesa);

        transaction.commit();
    }

    @Override
    public void delete(Mesa mesa) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        Mesa mesaExcluir = this.entityManager.merge(mesa);
        this.entityManager.remove(mesaExcluir);

        transaction.commit();
    }

    @Override
    public Mesa find(Integer id) {
        return this.entityManager.createQuery("SELECT m FROM Mesa m WHERE m.id =" + id, Mesa.class).getSingleResult();
    }

    public Mesa findByNum(Integer num) {
        Mesa mesa = this.entityManager.createQuery("SELECT m FROM Mesa m WHERE m.numero =" + num, Mesa.class).getSingleResult();

        if (mesa.getIniMesa() == null){
            EntityTransaction transaction = this.entityManager.getTransaction();

            transaction.begin();
            this.entityManager.createQuery("UPDATE Mesa m SET m.iniMesa = CURRENT_DATE WHERE m.idMesa = "+mesa.getId());
            transaction.commit();

        }

        mesa = this.entityManager.createQuery("SELECT m FROM Mesa m WHERE m.numero =" + num, Mesa.class).getSingleResult();

        return mesa;
    }

    @Override
    public List<Mesa> findAll() {
        return this.entityManager.createQuery("SELECT m FROM Mesa m", Mesa.class).getResultList();
    }


}

