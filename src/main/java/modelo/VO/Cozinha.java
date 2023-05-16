package modelo.VO;

import jakarta.persistence.*;

import java.util.*;

@Entity

@Table(name = "Cozinha")
public class Cozinha {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCozinha;

    private Calendar date;

    public Cozinha() {
    }

    public Cozinha(Calendar date) {
        this.date = date;
    }

    public Long getId() {
        return idCozinha;
    }

    public void setId(Long idCozinha) {
        this.idCozinha = idCozinha;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
