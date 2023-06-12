package asignacionaula3;
import asignacionaula3.controladores.*;
import asignacionaula3.services.BootStrapServices;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    public static void main(String[] args) throws SQLException {

        //Iniciando la base de datos.
        BootStrapServices.getInstancia().init();

        String mensaje = "Hola Mundo en Javalin :-D";
        System.out.println(mensaje);
        JavalinRenderer.register(new JavalinThymeleaf(), ".html");
        //Creando la instancia del servidor y configurando.
        Javalin app = Javalin.create(config ->{
            //configurando los documentos estaticos.
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.precompress=false;
                staticFileConfig.aliasCheck=null;
            });

            //Habilitando el CORS. Ver: https://javalin.io/plugins/cors#getting-started para más opciones.
            config.plugins.enableCors(corsContainer -> {
                corsContainer.add(corsPluginConfig -> {
                    corsPluginConfig.anyHost();
                });
            });

            //habilitando el plugins de las rutas definidas.
            config.plugins.enableRouteOverview("/rutas");

        });


        //
        app.start(getHerokuAssignedPort());

        //creando el manejador
        app.get("/", ctx -> ctx.result("Hola Mundo en Javalin :-D"));

        //
        new CrudTradicionalControlador(app).aplicarRutas();


        //Endpoint ejemplos html5.
        app.get("/fecha", ctx -> {
            ctx.result(""+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        });

        //Filtro para enviar el header de validación
        app.after(ctx -> {
            if(ctx.path().equalsIgnoreCase("/serviceworkers.js")){
                System.out.println("Enviando el header de seguridad para el Service Worker");
                ctx.header("Content-Type","application/javascript");
                ctx.header("Service-Worker-Allowed", "/");
            }

        });

    }

    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    /*private static OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("My Application");
        return new OpenApiOptions(applicationInfo).path("/openapi").swagger(new SwaggerOptions("/openapi-ui"));
    }*/
}
