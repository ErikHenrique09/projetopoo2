package modelo.VO;

import jakarta.persistence.*;

import java.util.Calendar;

@Entity

@Table(name = "Caixa")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaixa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFunc")
    private Funcionario funcionario;

    private Calendar date;

    public Caixa() {
    }

    public Caixa(Calendar date) {
        this.date = date;
    }

    public Long getId() {
        return idCaixa;
    }

    public void setId(Long idCaixa) {
        this.idCaixa = idCaixa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
