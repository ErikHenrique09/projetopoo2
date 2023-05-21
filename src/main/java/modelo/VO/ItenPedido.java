package modelo.VO;

import jakarta.persistence.*;

@Entity

@Table(name = "ItenPedido")
public class ItenPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItenPedido;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCardapio")
    private Cardapio cardapio;

    @Column(name = "qtd", nullable = false)
    private Integer qtd;

    @Column(name = "status", columnDefinition = "1", nullable = false)
    private Integer status;

    public ItenPedido() {
    }

    public ItenPedido(Integer qtd) {
        this.qtd = qtd;
    }

    public Long getIdItenPedido() {
        return idItenPedido;
    }

    public void setIdItenPedido(Long idItenPedido) {
        this.idItenPedido = idItenPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
