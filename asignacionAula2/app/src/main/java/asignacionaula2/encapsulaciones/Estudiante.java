package asignacionaula2.encapsulaciones;

/**
 * Objeto con estructura POJO.
 */
public class Estudiante {

    private int matricula;
    private String nombre;
    private String carrera;

    public Estudiante() {
    }

    public Estudiante(int matricula, String nombre, String carrera) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.carrera = carrera;
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

    public void mezclar(Estudiante e){
        matricula = e.getMatricula();
        nombre = e.getNombre();
        carrera = e.getCarrera();
    }

}
