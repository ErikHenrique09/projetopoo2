package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modelo.VO.Vendas;
import util.ConexaoHibernate;

import java.sql.Timestamp;
import java.util.Calendar;

public class AdminDAO {

    EntityManager entityManager;

    public AdminDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public void saveVendas(Vendas venda){
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(venda);

        transaction.commit();
    }

    public JsonArray listVendas(){
        return new Gson().fromJson(
                (String) this.entityManager.createQuery(
                "SELECT " +
                            "json_arrayagg( " +
                                "json_object( " +
                                    "'idItenPedido', idItenPedido, " +
                                    "'funcionario', funcionario, " +
                                    "'emailFuncionario', email, " +
                                    "'tel1Funcionario', telefone, " +
                                    "'produto',titulo, " +
                                    "'qtd',qtd, " +
                                    "'val',val, " +
                                    "'iniPed',  CONCAT(hour(iniPed), ':', minute(iniPed), ':', ROUND(second(iniPed),0)), " +
                                    "'fimPed',CONCAT(hour(fimPed), ':', minute(fimPed), ':', ROUND(second(fimPed),0)), " +
                                    "'dataPed',CONCAT(day(iniPed), '/', month(iniPed), '/', year(iniPed)) " +
                                ")" +
                            ")" +
                        "FROM Vendas").getSingleResult(), JsonArray.class);
    }

    public void insertVendas(String idMesa){
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        entityManager.createQuery(
        "INSERT INTO Vendas (idItenPedido, funcionario, email, telefone, titulo, qtd, val, iniPed, fimPed, iniMesa, fimMesa) " +
                "SELECT  CAST(i.idItenPedido AS integer), p2.nome, p2.email, p2.tel1, c.titulo, i.qtd, CAST(ROUND(c.val * i.qtd, 2) AS Double), " +
                " CAST(p.iniPedido AS java.util.Calendar), CAST(p.fimPedido AS java.util.Calendar), " +
                " CAST(m.iniMesa AS java.util.Calendar), CAST(m.fimMesa AS java.util.Calendar) " +
                " FROM Pedido p " +
                "INNER JOIN ItenPedido i ON p.idPedido = i.pedido.id " +
                "INNER JOIN Cardapio c ON i.cardapio.id = c.idCardapio " +
                "INNER JOIN Funcionario f ON p.funcionario.id = f.idFunc " +
                "INNER JOIN Mesa m ON p.mesa.id = m.idMesa " +
                "INNER JOIN Pessoa p2 ON f.pessoa.id = p2.idPessoa " +
                "WHERE m.idMesa = "+idMesa+" AND m.fimMesa IS NOT NULL"
        );

        transaction.commit();

    }

    public void insertVenda(String idMesa, String idItenPedido, int qtd){

        Object[] result = (Object[]) this.entityManager.createNativeQuery(
                "SELECT i.idItenPedido, p2.nome, p2.email, p2.tel1, c.titulo, "+qtd+", CAST(ROUND(c.val * " +qtd+ ", 2) AS Double), " +
                " p.iniPedido, p.fimPedido, " +
                " m.iniMesa, m.fimMesa " +
                " FROM Pedido p " +
                "INNER JOIN ItenPedido i ON p.idPedido = i.idPedido " +
                "INNER JOIN Cardapio c ON i.idCardapio = c.idCardapio " +
                "INNER JOIN Funcionario f ON p.idFunc = f.idFunc " +
                "INNER JOIN Mesa m ON p.idMesa = m.idMesa " +
                "INNER JOIN Pessoa p2 ON f.idPessoa = p2.idPessoa " +
                "WHERE m.idMesa = "+idMesa+" AND i.idItenPedido = "+idItenPedido).getSingleResult();

        Vendas venda = new Vendas();
        venda.setIdItenPedido(Integer.parseInt(idItenPedido));
        venda.setFuncionario((String) result[1]);
        venda.setEmail((String) result[2]);
        venda.setTelefone((String) result[3]);
        venda.setTitulo((String) result[4]);
        venda.setQtd(qtd);
        venda.setVal((Double) result[6]);

        // Converter os valores de Timestamp para Calendar
        Timestamp iniPedTimestamp = (Timestamp) result[7];
        Timestamp fimPedTimestamp = (Timestamp) result[8];
        Timestamp iniMesaTimestamp = (Timestamp) result[9];
        Timestamp fimMesaTimestamp = (Timestamp) result[10];

        Calendar iniPedCalendar = Calendar.getInstance();
        iniPedCalendar.setTime(iniPedTimestamp);

        Calendar iniMesaCalendar = Calendar.getInstance();
        iniMesaCalendar.setTime(iniMesaTimestamp);

        Calendar fimMesaCalendar;
        if(fimMesaTimestamp != null) {
            fimMesaCalendar = Calendar.getInstance();
            fimMesaCalendar.setTime(fimMesaTimestamp);
        }else {
            fimMesaCalendar = null;
        }

        Calendar fimPedCalendar;
        if(fimPedTimestamp != null) {
            fimPedCalendar = Calendar.getInstance();
            fimPedCalendar.setTime(fimPedTimestamp);
        }else{
            fimPedCalendar = null;
        }

        venda.setIniPed(iniPedCalendar);
        venda.setFimPed(fimPedCalendar);
        venda.setIniMesa(iniMesaCalendar);
        venda.setFimMesa(fimMesaCalendar);

        this.saveVendas(venda);

        System.out.println("Era pra ter dado certo");

    }

}

