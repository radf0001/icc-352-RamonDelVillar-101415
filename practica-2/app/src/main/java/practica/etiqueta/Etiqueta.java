package practica.etiqueta;

import java.util.concurrent.atomic.AtomicInteger;

public class Etiqueta {
    public final long id;
    public final String etiqueta;
    private static final AtomicInteger uniqueId = new AtomicInteger();

    public Etiqueta(String etiqueta) {
        this.id = uniqueId.incrementAndGet();
        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }
    public String getEtiqueta() {
        return etiqueta;
    }
}
