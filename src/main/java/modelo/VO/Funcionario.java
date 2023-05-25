package modelo.VO;

import jakarta.persistence.*;

@Entity

@Table(name = "Funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFunc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @Column(name = "func", nullable = false, columnDefinition = "1")
    private Funcoes func;

    public Funcionario() {
    }

    public Funcionario(Funcoes func, Pessoa pessoa) {
        this.func = func;
        this.pessoa = pessoa;
    }

    public Integer getId() {
        return Math.toIntExact(idFunc);
    }

    public void setId(Long id) {
        this.idFunc = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Funcoes getFunc() {
        return func;
    }

    public void setFunc(Funcoes func) {
        this.func = func;
    }
}
