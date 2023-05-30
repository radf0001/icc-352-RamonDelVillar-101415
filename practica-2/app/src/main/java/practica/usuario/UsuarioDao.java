package practica.usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private final List<Usuario> listaUsuarios = new ArrayList<>();

    public Iterable<Usuario> getAllUsuarios() {
        return listaUsuarios;
    }

    public Usuario getUsuarioByUsername(String username) {
        return listaUsuarios.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Usuario getUsuarioById(String id) {
        return listaUsuarios.stream().filter(b -> Long.toString(b.getId()).equals(id)).findFirst().orElse(null);
    }

    public Usuario crearUsuario(Usuario usuario){
        if(getUsuarioByUsername(usuario.getUsername())!=null){
            return null; //generar una excepcion...
        }
        listaUsuarios.add(usuario);
        return usuario;
    }

    public boolean eliminandoUsuario(Usuario user){
        return listaUsuarios.remove(user);
    }
}
