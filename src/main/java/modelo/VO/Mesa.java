package modelo.VO;

import jakarta.persistence.*;

import java.util.*;

@Entity

@Table(name = "Mesa")
public class Mesa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = false)
    private final List<Pedido> pedidos = new ArrayList<>();

    private Integer numero;
    private Calendar iniMesa;
    private Calendar fimMesa;

    public Mesa() {
    }

    public Mesa(Integer numero, Calendar iniMesa, Calendar fimMesa) {
        this.numero = numero;
        this.iniMesa = iniMesa;
        this.fimMesa = fimMesa;

    }

    public void iniciarMesa(){

        if(this.getIniMesa() != null){
            System.out.println("Mesa ocupada");
        }else{
            this.iniMesa = Calendar.getInstance();
        }
    }
    public void encerrarMesa(){

        if(this.getFimMesa() != null){
            System.out.println("Mesa ja encerrada");
        }else{
            //encerrar e inserir para dados historicos
            this.fimMesa = Calendar.getInstance();
            System.out.println("Inserir mesa, pedidos, itens do pedido, valor total nos registros historicos");
        }

    }

    public Long getId() {
        return idMesa;
    }

    public void setIda(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Calendar getIniMesa() {
        return this.iniMesa;
    }

    public void setIniMesa() {
        this.iniciarMesa();
    }

    public Calendar getFimMesa() {
        return fimMesa;
    }

    public void setFimMesa(Calendar fimMesa) {
        this.fimMesa = fimMesa;
    }
}
