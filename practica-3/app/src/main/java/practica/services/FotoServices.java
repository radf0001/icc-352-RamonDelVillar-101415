package practica.services;

import practica.entidades.Foto;

/**
 *
 */
public class FotoServices extends GestionDb<Foto> {

    private static FotoServices instancia;

    private FotoServices(){
        super(Foto.class);
    }

    public static FotoServices getInstancia(){
        if(instancia==null){
            instancia = new FotoServices();
        }
        return instancia;
    }

}
