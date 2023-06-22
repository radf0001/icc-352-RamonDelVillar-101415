package practica.entidades;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Etiqueta implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String etiqueta;

    public Etiqueta() {
    }

    public Etiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return id.equals(etiqueta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
