package modelo.VO;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    private final List<Endereco> enderecos = new ArrayList<Endereco>();

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @Column(name = "idade", nullable = false)
    private int idade;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "tel1", nullable = false)
    private String tel1;

    @Column(name = "tel2")
    private String tel2;

    @Column(name = "access")
    private boolean access;

    public Pessoa() {
    }

    public Pessoa(String nome, String sobrenome, Integer idade, String email, String senha, String tel1, String tel2) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.email = email;
        this.senha = senha;
        this.tel1 = tel1;
        this.tel2 = tel2;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getSobrenome() {
        return nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public void setTest() {
        this.nome = "Teste";
        this.sobrenome = "Teste";
        this.idade = 18;
        this.email = "emailTeste";
        this.senha = "senhaTeste";
        this.tel1 = "tel1teste";
        this.tel2 = "tel2Teste";
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}
