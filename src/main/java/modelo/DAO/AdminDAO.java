package modelo.DAO;

import jakarta.persistence.EntityManager;
import util.ConexaoHibernate;

import java.util.List;

public class AdminDAO {

    EntityManager entityManager;

    public AdminDAO() {
        this.entityManager = ConexaoHibernate.getInstance();
    }

    public List Vendas() {
        return this.entityManager.createQuery(
                "SELECT " +
                        "json_object( " +
                        "'idItenPedido', i.idItenPedido, " +
                        "'nomeFUncionario', p2.nome, " +
                        "'emailFuncionario', p2.email, " +
                        "'tel1Funcionario', p2.tel1, " +
                        "'titulo', c.titulo, " +
                        "'qtd', i.qtd, " +
                        "'val', c.val, " +
                        "'iniPed', p.iniPedido, " +
                        "'fimPed', p.fimPedido, " +
                        "'iniMesa', m.iniMesa, " +
                        "'fimMesa', m.fimMesa " +
                        ")" +
                        "FROM pedido p " +
                        "INNER JOIN itenpedido i on p.idPedido = i.idPedido " +
                        "INNER JOIN cardapio c on i.idCardapio = c.idCardapio " +
                        "INNER JOIN funcionario f on p.idFunc = f.idFunc " +
                        "INNER JOIN mesa m on p.idMesa = m.idMesa " +
                        "INNER JOIN pessoa p2 on f.idPessoa = p2.idPessoa " +
                        "WHERE p.fimPedido IS NOT NULL"
        ).getResultList();
    }

}

