package practica.entidades;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Comentario implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String comentario;
  @OneToOne
  private Usuario autor;
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Articulo articulo;

  public Comentario() {
  }

  public Comentario(String comentario, Usuario autor) {
    this.comentario = comentario;
    this.autor = autor;
  }

  public long getId() {
    return id;
  }

  public String getComentario() {
    return comentario;
  }

  public Usuario getAutor() {
    return autor;
  }

  public Articulo getArticulo() {
    return articulo;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public void setArticulo(Articulo articulo) {
    this.articulo = articulo;
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario comentario = (Comentario) o;
        return id.equals(comentario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
