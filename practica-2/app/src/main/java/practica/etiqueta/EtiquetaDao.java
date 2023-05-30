package practica.etiqueta;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;

public class EtiquetaDao {
    private final List<Etiqueta> listaEtiquetas = ImmutableList.of(
        new Etiqueta("Deportes"), new Etiqueta("Noticias"), new Etiqueta("Ciencias"), new Etiqueta("Ecommerce"), new Etiqueta("Comida"),
        new Etiqueta("Arte"), new Etiqueta("Belleza"), new Etiqueta("Musica"), new Etiqueta("Politica"), new Etiqueta("Tecnologia")
    );

    public List<Etiqueta> getAllEiquetas() {
        return listaEtiquetas;
    }

    public Etiqueta getEtiquetaById(long id) {
        return listaEtiquetas.stream().filter(b -> b.id == id).findFirst().orElse(null);
    }

    public List<Etiqueta> crearListaEtiquetas(List<String> etiquetas){
        List<Etiqueta> tmp = new ArrayList<>();
        Etiqueta tag;
        for(String myStr: etiquetas) {
            tag=getEtiquetaById(Long.parseLong(myStr));
            if(tag != null){
                tmp.add(tag);
            }
        }
        return tmp;
    }

}
