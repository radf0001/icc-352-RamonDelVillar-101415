package practica.services;

import org.postgresql.ds.PGSimpleDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class CockroachServices {
    private static CockroachServices instancia;

    /**
     *Implementando el patron Singleton
     */

    /**
     * Retornando la instancia.
     * @return
     */
    public static CockroachServices getInstancia(){
        if(instancia==null){
            instancia = new CockroachServices();
        }
        return instancia;
    }

    /**
     * Metodo para el registro de driver de conexión.
     */

    public Connection getConexion() {
        Connection con = null;
        // Esta opcion funciona si la variable esta configurada
        try {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setUrl(System.getenv("JDBC_DATABASE_URL"));
            
            con = ds.getConnection();
        
        } catch (Exception ex) {
            Logger.getLogger(CockroachServices.class.getName()).log(Level.SEVERE, null, "CockroachDB");
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            Logger.getLogger(CockroachServices.class.getName()).log(Level.SEVERE, null, "CockroachDB");
        }
    }

    public void crearTablas() throws  SQLException{
        try {
            String sql = "CREATE TABLE IF NOT EXISTS TABLALOGIN\n" +
                    "(\n" +
                    "  USERNAME VARCHAR(255) NOT NULL,\n" +
                    "  dateLog TIMESTAMP NOT NULL,\n" +
                    "  PRIMARY KEY(USERNAME, DATELOG)\n" +
                    ");";
            Connection con = getConexion();
            Statement statement = con.createStatement();
            statement.execute(sql);
            statement.close();
            con.close();
            System.out.println("CockroachDB: Se crearon las tablas en la base de datos cockroach");
        } catch (Exception e) {
            Logger.getLogger(CockroachServices.class.getName()).log(Level.SEVERE, null, "CockroachDB");
        }

    }

    public boolean crearLog(String username, Timestamp timestamp){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into TABLALOGIN(USERNAME, DATELOG) values(?,?)";
            con = getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, username);
            prepareStatement.setTimestamp(2, timestamp);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;
            if(ok){
                System.out.println("CockroachDB: Se inserto la fila en la base de datos cockroach");
            }

        } catch (Exception ex) {
            Logger.getLogger(CockroachServices.class.getName()).log(Level.SEVERE, null, "CockroachDB");
        } finally{
            try {
                con.close();
            } catch (Exception ex) {
                Logger.getLogger(CockroachServices.class.getName()).log(Level.SEVERE, null, "CockroachDB");
            }
        }

        return ok;
    }
}