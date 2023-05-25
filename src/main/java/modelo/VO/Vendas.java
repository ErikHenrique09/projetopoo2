package modelo.VO;

import jakarta.persistence.*;

import java.util.Calendar;

@Entity
@Table(name = "vendas") // Substitua "nome_da_tabela" pelo nome correto da tabela
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenda")
    private int idVenda;

    @Column(name = "idItenPedido")
    private int idItenPedido;

    @Column(name = "funcionario")
    private String funcionario;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "qtd")
    private int qtd;

    @Column(name = "val")
    private double val;

    @Column(name = "iniPed")
    private Calendar iniPed;

    @Column(name = "fimPed")
    private Calendar fimPed;

    @Column(name = "iniMesa")
    private Calendar iniMesa;

    @Column(name = "fimMesa")
    private Calendar fimMesa;

    // Construtor padrão
    public Vendas() {
    }

    // Construtor com parâmetros
    public Vendas(int idItenPedido, String funcionario, String email, String telefone, String titulo, int qtd, double val, Calendar iniPed, Calendar fimPed, Calendar iniMesa, Calendar fimMesa) {
        this.idItenPedido = idItenPedido;
        this.funcionario = funcionario;
        this.email = email;
        this.telefone = telefone;
        this.titulo = titulo;
        this.qtd = qtd;
        this.val = val;
        this.iniPed = iniPed;
        this.fimPed = fimPed;
        this.iniMesa = iniMesa;
        this.fimMesa = fimMesa;
    }
    // Construtores, getters e setters

    public int getIdItenPedido() {
        return idItenPedido;
    }

    public void setIdItenPedido(int idItenPedido) {
        this.idItenPedido = idItenPedido;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public Calendar getIniPed() {
        return iniPed;
    }

    public void setIniPed(Calendar iniPed) {
        this.iniPed = iniPed;
    }

    public Calendar getFimPed() {
        return fimPed;
    }

    public void setFimPed(Calendar fimPed) {
        this.fimPed = fimPed;
    }

    public Calendar getIniMesa() {
        return iniMesa;
    }

    public void setIniMesa(Calendar iniMesa) {
        this.iniMesa = iniMesa;
    }

    public Calendar getFimMesa() {
        return fimMesa;
    }

    public void setFimMesa(Calendar fimMesa) {
        this.fimMesa = fimMesa;
    }
}


