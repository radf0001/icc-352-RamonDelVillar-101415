package pucmm.edu.encapsulaciones;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity("formularios")
@Indexes(@Index(fields = @Field(value = "nombre")))
public class Formulario {

    @Id
    private ObjectId id;
    private String nombre;
    private String sector;
    private String nivelEscolar;
    private String fecha;
    @Reference(idOnly = true)
    private Usuario user;

    public Formulario(){};
    public Formulario(String nombre, String sector, String nivelEscolar, Usuario user) {
        this.nombre = nombre;
        this.sector = sector;
        this.nivelEscolar = nivelEscolar;
        this.user= user;
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formulario that = (Formulario) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(sector, that.sector) && Objects.equals(nivelEscolar, that.nivelEscolar) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, sector, nivelEscolar, user);
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "nombre='" + nombre + '\'' +
                ", sector='" + sector + '\'' +
                ", nivelEscolar='" + nivelEscolar + '\'' +
                ", user=" + user +
                '}';
    }
}
