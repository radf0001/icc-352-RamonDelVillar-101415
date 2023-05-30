package practica.usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private final List<Usuario> listaUsuarios = new ArrayList<>();

    public Iterable<Usuario> getAllUsuarios() {
        return listaUsuarios;
    }

    public Usuario getUsuarioByUsername(String username) {
        return listaUsuarios.stream().filter(b -> b.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public Usuario getUsuarioById(String id) {
        return listaUsuarios.stream().filter(b -> Long.toString(b.getId()).equals(id)).findFirst().orElse(null);
    }

    public boolean crearUsuario(String username, String nombre, String password, boolean admin, boolean autor){
        if(getUsuarioByUsername(username)!=null){
            return false; //generar una excepcion...
        }
        Usuario tmp = new Usuario(username, nombre, password, admin, autor);
        listaUsuarios.add(tmp);
        return true;
    }

    public boolean eliminandoUsuario(Usuario user){
        return listaUsuarios.remove(user);
    }
}
