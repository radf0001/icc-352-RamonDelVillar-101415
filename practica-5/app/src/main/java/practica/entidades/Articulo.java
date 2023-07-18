package practica.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import practica.services.ArticuloServices;

import java.util.Objects;

@Entity
public class Articulo implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;
  private String cuerpo;
  private Usuario autor;
  private Date fecha;
  private String stringEtiquetas;
  private String firstChars;

  public String getFirstChars() {
    return firstChars;
  }

  public void setFirstChars(String firstChars) {
    this.firstChars = firstChars;
  }

  @OneToMany(
        mappedBy = "articulo",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
  )
  @JsonIgnore
  private List<Comentario> listaComentarios = new ArrayList<>();
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<Etiqueta> listaEtiquetas;

  public Articulo() {
  }

  public Articulo(String titulo, String cuerpo, Usuario autor, Set<Etiqueta> listaEtiquetas) {
    this.titulo = titulo;
    this.cuerpo = cuerpo;
    this.autor = autor;
    this.fecha = new Date();
    this.listaEtiquetas = listaEtiquetas;
    this.stringEtiquetas = listaEtiquetas.stream().map(Etiqueta::getEtiqueta).collect(Collectors.toList()).stream().collect(Collectors.joining(","));
    if (cuerpo.length() > 70)
      this.firstChars =  cuerpo.substring(0, 70) + "...";
    else
      this.firstChars =  cuerpo;
  }  

  public void setId(long id) {
    this.id = id;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setCuerpo(String cuerpo) {
    this.cuerpo = cuerpo;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public void setListaComentarios(List<Comentario> listaComentarios) {
    this.listaComentarios = listaComentarios;
  }

  public void setListaEtiquetas(Set<Etiqueta> listaEtiquetas) {
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

  public void addComment(Comentario comment) {
    listaComentarios.add(comment);
    comment.setArticulo(this);
  }

  public Set<Etiqueta> getListaEtiquetas() {
    return listaEtiquetas;
  }

  public List<Etiqueta> getEtiquetas(Long id) {
    return ArticuloServices.getInstancia().etiquetas(id);
  }

  public String getStringEtiquetas() {
    return stringEtiquetas;
  }

  public void setStringEtiquetas(String stringEtiquetas) {
    this.stringEtiquetas = stringEtiquetas;
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articulo articulo = (Articulo) o;
        return id.equals(articulo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
