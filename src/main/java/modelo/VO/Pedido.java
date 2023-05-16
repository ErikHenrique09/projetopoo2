package modelo.VO;

import jakarta.persistence.*;

import java.util.*;

@Entity

@Table(name = "Pedido")
public class Pedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFunc")
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCozinha")
    private Cozinha cozinha;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMesa")
    private Mesa mesa;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItenPedido> itensPedido = new ArrayList<>();

    @Column(name = "iniPedido", nullable = false)
    private Calendar iniPedido;

    @Column(name = "fimPedido", nullable = true)
    private Calendar fimPedido;

    public Pedido() {
        this.iniPedido = Calendar.getInstance();
    }

    public Pedido(Calendar iniPedido, Calendar fimPedido, List<ItenPedido> itensPedido) {
        this.iniPedido = iniPedido;
        this.fimPedido = fimPedido;
        this.itensPedido = itensPedido;
    }

    public Long getId() {
        return idPedido;
    }

    public void setId(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<ItenPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItenPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Calendar getIniPedido() {
        return iniPedido;
    }

    public void setIniPedido(Calendar iniPedido) {
        this.iniPedido = iniPedido;
    }

    public Calendar getFimPedido() {
        return fimPedido;
    }

    public void setFimPedido(Calendar fimPedido) {
        this.fimPedido = fimPedido;
    }
}
