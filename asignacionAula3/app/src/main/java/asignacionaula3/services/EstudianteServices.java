package asignacionaula3.services;

import asignacionaula3.encapsulaciones.Estudiante;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Clase para encapsular los metodos de CRUD
 * Created by vacax on 30/04/16.
 */
public class EstudianteServices extends GestionDb<Estudiante> {

    private static EstudianteServices instancia;

    private EstudianteServices() {
        super(Estudiante.class);
    }

    public static EstudianteServices getInstancia() {
        if (instancia == null) {
            instancia = new EstudianteServices();
        }
        return instancia;
    }

    /**
     *
     * @return
     */
    public List<Estudiante> listaEstudiantes() {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from estudiante ", Estudiante.class);
        // query.setParameter("nombre", apellido+"%");
        List<Estudiante> lista = query.getResultList();
        return lista;
    }

    /**
     * Retorna un estudiante dado su matricula
     * 
     * @param matricula
     * @return
     */
    public Estudiante getEstudiante(int matricula) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Estudiante e where e.nombre like :matricula");
        query.setParameter("matricula", matricula + "%");
        Estudiante est = (Estudiante) query.getSingleResult();
        return est;
    }
}