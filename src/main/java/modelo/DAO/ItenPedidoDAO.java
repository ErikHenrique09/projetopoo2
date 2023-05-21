package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.ItenPedido;
import util.CRUD;
import util.ConexaoHibernate;

import java.util.List;

public class ItenPedidoDAO implements CRUD<ItenPedido> {

    EntityManager entityManager;

    public ItenPedidoDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

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
        return this.entityManager.createQuery("SELECT ip FROM ItenPedido ip WHERE ip.id =" + id, ItenPedido.class).getSingleResult();
    }

    @Override
    public List<ItenPedido> findAll() {
        return this.entityManager.createQuery("SELECT i FROM ItenPedido i", ItenPedido.class).getResultList();
    }

    public JsonArray exibir(Integer status) {
        //O status 0 é pra vendas, o status 1 é pra cozinha
        JsonArray jsonElements = new Gson().fromJson(
                (String) this.entityManager.createQuery(
                        "SELECT " +
                                "JSON_ARRAYAGG( " +
                                "JSON_OBJECT( " +
                                "'idItenPedido', i.idItenPedido, " +
                                "'func', p2.nome, " +
                                "'emailfunc', p2.email, " +
                                "'tel1func', p2.tel1, " +
                                "'produto', c.titulo, " +
                                "'quantidade', i.qtd, " +
                                "'valFinal', round(c.val*i.qtd,2), " +
                                "'iniPed', concat(hour(p.iniPedido), ':', minute(p.iniPedido), ':', second(p.iniPedido)), " +
                                "'fimPed', concat(hour(p.fimPedido), ':', minute(p.fimPedido), ':', second(p.fimPedido)), " +
                                "'mesa', m.numero, " +
                                "'dataPed', concat(day(p.iniPedido), '/', month(p.iniPedido), '/', year(p.iniPedido)), " +
                                "'status', if(p.mesa.id = 1, 'Active', 'False') " +
                                ") " +
                                ") vendas " +
                                "FROM Pedido p " +
                                "INNER JOIN ItenPedido i on p.idPedido = i.pedido.id " +
                                "INNER JOIN Cardapio c on i.cardapio.id = c.idCardapio " +
                                "INNER JOIN Funcionario f on p.funcionario.id = f.idFunc " +
                                "INNER JOIN Mesa m on p.mesa.idMesa = m.idMesa " +
                                "INNER JOIN Pessoa p2 on f.pessoa.id = p2.idPessoa " +
                                "WHERE i.status = "+ status +
                                "ORDER BY i.idItenPedido  ASC").getSingleResult(),
                JsonArray.class);
        return jsonElements;
    }

    public void close(ItenPedido item) {
        item.setStatus(0);
        update(item);
    }

    public List<ItenPedido> findAllByPed(Long idPedido) {
        return this.entityManager.createQuery("SELECT * FROM ItemPedido i WHERE i.pedido.id = "+idPedido,ItenPedido.class).getResultList();
    }

    /*public JsonObject attTime(){
        return this.entityManager.createQuery("SELECT " +
                                                                    "JSON_OBJECT(" +
                                                                        "'time', concat(hour((timediff(p.iniPedido), ':', minute(p.iniPedido), ':', second(p.iniPedido)), \" +;
    }*/

}

