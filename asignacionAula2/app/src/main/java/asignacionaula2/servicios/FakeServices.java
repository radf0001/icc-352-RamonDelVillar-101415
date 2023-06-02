package asignacionaula2.servicios;

import asignacionaula2.encapsulaciones.Estudiante;
import asignacionaula2.util.NoExisteEstudianteException;

import java.util.*;

/**
 * Ejemplo de servicio patron Singleton
 */
public class FakeServices {

    private static FakeServices instancia;
    private List<Estudiante> listaEstudiante = new ArrayList<>();

    /**
     * Constructor privado.
     */
    private FakeServices(){
        //añadiendo los estudiantes.
        listaEstudiante.add(new Estudiante(20011136, "Carlos Camacho", "ITT"));
    }

    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }

    public List<Estudiante> listarEstudiante(){
        return listaEstudiante;
    }

    public Estudiante getEstudiantePorMatricula(int matricula){
        return listaEstudiante.stream().filter(e -> e.getMatricula() == matricula).findFirst().orElse(null);
    }

    public Estudiante crearEstudiante(Estudiante estudiante){
        if(getEstudiantePorMatricula(estudiante.getMatricula())!=null){
            System.out.println("Estudiante registrado...");
            return null; //generar una excepcion...
        }
        listaEstudiante.add(estudiante);
        return estudiante;
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante){
        Estudiante tmp = getEstudiantePorMatricula(estudiante.getMatricula());
        if(tmp == null){//no existe, no puede se actualizado
            throw new NoExisteEstudianteException("No Existe el estudiante: "+estudiante.getMatricula());
        }
        tmp.mezclar(estudiante);
        return tmp;
    }

    public boolean eliminandoEstudiante(int matricula){
        Estudiante tmp = new Estudiante();
        tmp.setMatricula(matricula);
        return listaEstudiante.remove(tmp);
    }

}
