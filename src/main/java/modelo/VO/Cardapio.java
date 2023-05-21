package modelo.VO;

import jakarta.persistence.*;

@Entity

@Table(name = "Cardapio")
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCardapio;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "val", nullable = false)
    private Float val;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name= "categoria", nullable = false)
    private String categoria;

    public Cardapio() {
    }

    public Cardapio(String titulo, String desc, Float val) {
        this.titulo = titulo;
        this.descricao = desc;
        this.val = val;
    }

    public Long getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(Long idCardapio) {
        this.idCardapio = idCardapio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Float getVal() {
        return val;
    }

    public void setVal(Float val) {
        this.val = val;
    }

    public String getDesc() {
        return descricao;
    }

    public void setDesc(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}