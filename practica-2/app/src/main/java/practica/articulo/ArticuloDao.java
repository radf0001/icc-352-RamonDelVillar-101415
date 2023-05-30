package practica.articulo;

import com.google.common.collect.ImmutableList;
import practica.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

public class ArticuloDao {
    private final List<Articulo> listaArticulos = new ArrayList<>();


    public Iterable<Articulo> getAllArticulos() {
        return listaArticulos;
    }

    public Articulo getArticuloById(String id) {
        return listaArticulos.stream().filter(b -> Long.toString(b.getId()).equals(id)).findFirst().orElse(null);
    }

    public Articulo crearArticulo(Articulo newArticulo){
        listaArticulos.add(0, newArticulo);
        return newArticulo;
    }

    public boolean EliminarArticulo(Articulo newArticulo){
        return listaArticulos.remove(newArticulo);
    }
}
