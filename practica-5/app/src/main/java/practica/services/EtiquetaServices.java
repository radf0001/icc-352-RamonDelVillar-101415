package practica.services;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import practica.entidades.Etiqueta;

public class EtiquetaServices extends GestionDb<Etiqueta>{

    private static EtiquetaServices instancia;

    private EtiquetaServices() {
        super(Etiqueta.class);
    }

    public static EtiquetaServices getInstancia() {
        if (instancia == null) {
            instancia = new EtiquetaServices();
        }
        return instancia;
    }

    // public List<Etiqueta> crearListaEtiquetas(List<String> etiquetas){
    //     List<Etiqueta> tmp = new ArrayList<>();
    //     Etiqueta tag;
    //     for(String myStr: etiquetas) {
    //         tag=find(Long.parseLong(myStr));
    //         if(tag != null){
    //             tmp.add(tag);
    //         }
    //     }
    //     return tmp;
    // }

    /**
     *
     * @param nombre
     * @return
     */
    public List<Etiqueta> findAllById(List<String> etiquetas){
        List<Long> longs = new ArrayList<>();
        for(String s : etiquetas) longs.add(Long.parseLong(s));
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Etiqueta e where e.id IN (:ids)");
        query.setParameter("ids", longs);
        List<Etiqueta> lista = query.getResultList();
        return lista;
    }

}
