package asignacionaula3.encapsulaciones;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Objeto con estructura POJO.
 */
@Entity
public class Estudiante implements Serializable {

    @Id
    private int matricula;
    private String nombre;
    private String carrera;

    public Estudiante() {
    }

    public Estudiante(int matricula, String nombre, String carrera) {
        this.setMatricula(matricula);
        this.setNombre(nombre);
        this.setCarrera(carrera);
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
