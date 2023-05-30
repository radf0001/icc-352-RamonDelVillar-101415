package practica.articulo;

import practica.usuario.Usuario;

import java.util.concurrent.atomic.AtomicInteger;

public class Comentario {
  private long id;
  private String comentario;
  private Usuario autor;
  private Articulo articulo;

  private static final AtomicInteger uniqueId = new AtomicInteger();

  public Comentario(String comentario, Usuario autor, Articulo articulo) {
    this.id = uniqueId.incrementAndGet();
    this.comentario = comentario;
    this.autor = autor;
    this.articulo = articulo;
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
}
