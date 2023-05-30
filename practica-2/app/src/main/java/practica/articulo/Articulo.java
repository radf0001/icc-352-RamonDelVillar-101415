package practica.articulo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import practica.etiqueta.Etiqueta;
import practica.usuario.Usuario;

public class Articulo {
  private long id;
  private String titulo;
  private String cuerpo;
  private Usuario autor;
  private Date fecha;
  private List<Comentario> listaComentarios;
  private List<Etiqueta> listaEtiquetas;

  private static final AtomicInteger uniqueId = new AtomicInteger();

  public Articulo(String titulo, String cuerpo, Usuario autor, List<Etiqueta> listaEtiquetas) {
    this.id = uniqueId.incrementAndGet();
    this.titulo = titulo;
    this.cuerpo = cuerpo;
    this.autor = autor;
    this.fecha = new Date();
    this.listaComentarios = new ArrayList<>();
    this.listaEtiquetas = listaEtiquetas;
  }

  public long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getCuerpo() {
    return cuerpo;
  }

  public Usuario getAutor() {
    return autor;
  }

  public Date getFecha() {
    return fecha;
  }

  public String getFechaParse() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    String date =sdf.format(fecha);
    return date;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public List<Comentario> getListaComentarios() {
    return listaComentarios;
  }

  public List<Etiqueta> getListaEtiquetas() {
    return listaEtiquetas;
  }

  public String getStringEtiquetas() {
    String etiquetaStr = "";
    for (Etiqueta tag:listaEtiquetas) {
      etiquetaStr += tag.etiqueta + ", ";
    }
    return etiquetaStr;
  }

  public String firstChars() {
    if (cuerpo.length() > 70)
      return cuerpo.substring(0, 70) + "...";
    else
      return cuerpo;
  }

  public void editarArticulo(String title, String desc, Usuario user, List<Etiqueta> tags){
    titulo = title;
    cuerpo = desc;
    autor = user;
    listaEtiquetas = tags;
  }

  public void addComentario(String cuerpo, Usuario autor, Articulo articulo){
    listaComentarios.add(0, new Comentario(cuerpo, autor, articulo));
  }
}
