package practica.articulo;

import java.util.ArrayList;
import java.util.List;

import practica.etiqueta.Etiqueta;
import practica.usuario.Usuario;

public class ArticuloDao {
    private final List<Articulo> listaArticulos = new ArrayList<>();


    public Iterable<Articulo> getAllArticulos() {
        return listaArticulos;
    }

    public Articulo getArticuloById(String id) {
        return listaArticulos.stream().filter(b -> Long.toString(b.getId()).equals(id)).findFirst().orElse(null);
    }

    public Articulo getArticuloByCuerpo(String cuerpo) {
        return listaArticulos.stream().filter(b -> b.getCuerpo().equalsIgnoreCase(cuerpo)).findFirst().orElse(null);
    }

    public boolean crearArticulo(String titulo, String descripcion, Usuario user, List<Etiqueta> tags){
        if(getArticuloByCuerpo(descripcion)!=null){
            return false; //generar una excepcion...
        }
        Articulo tmp = new Articulo(titulo, descripcion, user, tags);
        listaArticulos.add(0, tmp);
        return true;
    }

    public boolean EliminarArticulo(Articulo newArticulo){
        return listaArticulos.remove(newArticulo);
    }
}
