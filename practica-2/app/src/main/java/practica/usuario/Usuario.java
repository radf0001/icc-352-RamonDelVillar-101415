package practica.usuario;

import java.util.concurrent.atomic.AtomicInteger;

public class Usuario {
  private long id;
  private String username;
  private String nombre;
  private String password;
  private boolean administrador;
  private boolean autor;
  private static final AtomicInteger uniqueId = new AtomicInteger();
//  private String salt;
//  private String hashedPassword;


  public Usuario(String username, String nombre, String password, boolean administrador, boolean autor) {
    this.id = uniqueId.incrementAndGet();
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void editarUsuario(String user,String name, String pwd, boolean author, boolean admin){
    username = user;
    nombre = name;
    password = pwd;
    autor = author;
    administrador = admin;
  }
}
