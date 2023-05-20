package main.java.asignacion.aula;

public class user {
  private String user;
  private String pwd;

  public String getUser() {
    return user;
  }
  public void setUser(String user) {
    this.user = user;
  }
  public String getPwd() {
    return pwd;
  }
  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
  @Override
  public String toString() {
    return "user [user=" + user + ", pwd=" + pwd + "]";
  }
}


