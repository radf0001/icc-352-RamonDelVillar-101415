package practica.entidades;


import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario implements Serializable{
  
  @Id
  private String username;
  private String nombre;
  private String password;
  private boolean administrador;
  private boolean autor;
//  private String salt;
//  private String hashedPassword;
  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  private Foto foto;

    public Usuario() {
    }

  public Usuario(String username, String nombre, String password, boolean administrador, boolean autor) {
    this.username = username;
    this.nombre = nombre;
    this.password = password;
    this.administrador = administrador;
    this.autor = autor;
    this.password = password;
//    salt = BCrypt.gensalt();
//    hashedPassword = BCrypt.hashpw(salt, password);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAdministrador() {
    return administrador;
  }

  public void setAdministrador(boolean administrador) {
    this.administrador = administrador;
  }

  public boolean isAutor() {
    return autor;
  }

  public void setAutor(boolean autor) {
    this.autor = autor;
  }

//  public String getSalt() {
//    return salt;
//  }
//
//  public void setSalt(String salt) {
//    this.salt = salt;
//  }
//
//  public String getHashedPassword() {
//    return hashedPassword;
//  }
//
//  public void setHashedPassword(String hashedPassword) {
//    this.hashedPassword = hashedPassword;
//  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return username.equalsIgnoreCase(usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Foto getFoto() {
      return foto;
    }

    public void setFoto(Foto foto) {
      this.foto = foto;
    }
}
