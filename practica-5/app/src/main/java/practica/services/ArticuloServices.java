package practica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import practica.entidades.Articulo;
import practica.entidades.Etiqueta;

public class ArticuloServices extends GestionDb<Articulo>{

    private static ArticuloServices instancia;

    private ArticuloServices() {
        super(Articulo.class);
    }

    public static ArticuloServices getInstancia() {
        if (instancia == null) {
            instancia = new ArticuloServices();
        }
        return instancia;
    }

    /**
     *
     * @param cuerpo
     * @return
     */
    public List<Articulo> getArticuloByCuerpo(String cuerpo) {
        EntityManager em = getEntityManager();
        List<Articulo> lista;
        try{
            Query query = em.createQuery("select e from Articulo e where e.cuerpo like :cuerpo");
            query.setParameter("cuerpo", cuerpo+"%");
            lista = query.getResultList();
        }finally{
            em.close();
        }
        return lista;
    }

    public List<Articulo> paginationtiquetas(String etiquetaID, int offset) {
        // Initialize the query
        offset = offset * 5;
        EntityManager em = getEntityManager(); 
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Articulo> cq = cb.createQuery(Articulo.class);
        Root<Articulo> articulo = cq.from(Articulo.class);
        List<Predicate> predicates = new ArrayList<>();
        cq.orderBy(cb.desc(articulo.get("fecha")));

        // Iterate over each courseName we want to match with
            // Initialize the subquery
        if(EtiquetaServices.getInstancia().find(etiquetaID) != null){
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Articulo> subqueryArticulo = subquery.from(Articulo.class);
            Join<Etiqueta, Articulo> subqueryEtiqueta = subqueryArticulo.join("listaEtiquetas");
            
            // Select the Student ID where one of their courses matches
            subquery.select(subqueryArticulo.get("id")).where(
                cb.equal(subqueryEtiqueta.get("id"), etiquetaID));

            // Filter by Students that match one of the Students found in the subquery
            predicates.add(cb.in(articulo.get("id")).value(subquery));
            
            // Use all predicates above to query
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }else{
            cq.select(articulo);
        }
        List<Articulo> result = 
        em
            .createQuery(cq)
            .setMaxResults(5)
            .setFirstResult(offset)
            .getResultList();
            
        em.close();

        return result;
    }

    public String stringEtiquetas(Long id) {
        EntityManager em = getEntityManager();
        List<String> lista;
        try{
            Query query = em.createQuery("SELECT l.etiqueta FROM Articulo e JOIN e.listaEtiquetas l WHERE e.id LIKE :id");
            query.setParameter("id", id);
            lista = query.getResultList();
        }finally{
            em.close();
        }
        return lista.stream().collect(Collectors.joining(","));
    }

    public List<Etiqueta> etiquetas(Long id) {
        EntityManager em = getEntityManager();
        List<Etiqueta> lista;
        try{
            Query query = em.createQuery("SELECT l FROM Articulo e JOIN e.listaEtiquetas l WHERE e.id LIKE :id");
            query.setParameter("id", id);
            lista = query.getResultList();
        }finally{
            em.close();
        }
        return lista;
    }
}
