package practica;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinVelocity;

import org.eclipse.jetty.websocket.api.Session;
import practica.util.RequestUtil;
import practica.entidades.Articulo;
import practica.entidades.Etiqueta;
import practica.entidades.Usuario;
import practica.services.ArticuloServices;
import practica.services.BootStrapServices;
import practica.services.CockroachServices;
import practica.services.EtiquetaServices;
import practica.services.FotoServices;
import practica.services.UsuarioServices;
import practica.util.ViewUtil;
import practica.util.Path;
import practica.controladores.ArticuloController;
import practica.controladores.ChatController;
import practica.controladores.LoginController;
import practica.controladores.UserController;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Main {
    public static ArticuloServices articuloServices;
    public static EtiquetaServices etiquetaServices;
    public static UsuarioServices usuarioServices;
    public static FotoServices fotoServices;
    public static CockroachServices cockroachServices;
    public static Map<Session, String> userUsernameMapReceiv = new ConcurrentHashMap<>();
    public static Map<Session, String> userUsernameMapSender = new ConcurrentHashMap<>();

    public static void main(String[] args) throws SQLException{

        //Iniciando la base de datos.
        BootStrapServices.getInstancia().init();

        JavalinRenderer.register(new JavalinVelocity(), ".vm");
        articuloServices = ArticuloServices.getInstancia();
        etiquetaServices = EtiquetaServices.getInstancia();
        usuarioServices = UsuarioServices.getInstancia();
        fotoServices = FotoServices.getInstancia();
        cockroachServices = CockroachServices.getInstancia();
        cockroachServices.crearTablas();
        if(usuarioServices.findAll().isEmpty()){
            usuarioServices.crear(new Usuario("admin", "admin", "admin", true, true));
            for(int i=0;i<50;i++){
                usuarioServices.crear(new Usuario("admin"+i, "admin"+i, "admin"+i, true, true));
            }
        }
        if(etiquetaServices.findAll().isEmpty()){
            etiquetaServices.crear(new Etiqueta("Deportes"));
            etiquetaServices.crear(new Etiqueta("Noticias")); 
            etiquetaServices.crear(new Etiqueta("Ciencias")); 
            etiquetaServices.crear(new Etiqueta("Ecommerce"));
            etiquetaServices.crear(new Etiqueta("Comida"));
            etiquetaServices.crear(new Etiqueta("Arte")); 
            etiquetaServices.crear(new Etiqueta("Belleza")); 
            etiquetaServices.crear(new Etiqueta("Musica")); 
            etiquetaServices.crear(new Etiqueta("Politica")); 
            etiquetaServices.crear(new Etiqueta("Tecnologia"));
        }
        if(articuloServices.findAll().isEmpty()){
            Usuario user = usuarioServices.find("admin");
            Etiqueta etiqueta = etiquetaServices.find(1);
            Set<Etiqueta> lista = new HashSet<>();
            lista.add(etiqueta);
            for(int i=0;i<50;i++){
                articuloServices.crear(new Articulo("titulo "+i, "descripcion "+i, user, lista));
            }
        }

        //Creando la instancia del servidor y configurando.
        Javalin app = Javalin.create(config ->{
            //configurando los documentos estaticos.
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/public";
                staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.precompress=false;
                staticFileConfig.aliasCheck=null;
            });

            //Habilitando el CORS. Ver: https://javalin.io/plugins/cors#getting-started para más opciones.
            config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));

            //habilitando el plugins de las rutas definidas.
            config.plugins.enableRouteOverview("/routes");

        });

        app.start(7000);

        app.routes(() -> {
            path("/admin", () -> {
                before(ctx -> {
                    //recuperando el usuario de la sesión,en caso de no estar, redirecciona la pagina 401.
                    String username = RequestUtil.getSessionCurrentUser(ctx);
                    Usuario usuario = null;
                    if(username != null){
                        usuario = usuarioServices.find(RequestUtil.getSessionCurrentUser(ctx));
                    }
                    if(usuario == null || !usuario.isAdministrador()){
                        ctx.redirect("/401");
                    }
                });
//          GET CRUD USUARIOS
                get(Path.Web.USERS, UserController.fetchAllUsuarios);
//          GET CREAR USUARIO
                get(Path.Web.CREAR_USER, UserController.serveCrearPage);
//          POST CREAR USER
                post(Path.Web.CREAR_USER, UserController.handleUsuarioPost);
//          GET EDITAR USER
                get(Path.Web.ONE_USER, UserController.fetchEditarOneUsuarioCrud);
//          POST EDITAR USER
                post(Path.Web.ONE_USER, UserController.handleEditarUsuarioPost);
//          GET ELIMINAR USER
                get(Path.Web.ELIMINAR_USER, UserController.handleEliminar);
            });

            path("/autor", () -> {
                before(ctx -> {
                    //recuperando el usuario de la sesión,en caso de no estar, redirecciona la pagina 401.
                    String username = RequestUtil.getSessionCurrentUser(ctx);
                    Usuario usuario = null;
                    if(username != null){
                        usuario = usuarioServices.find(RequestUtil.getSessionCurrentUser(ctx));
                    }
                    if(usuario == null || (!usuario.isAdministrador() && !usuario.isAutor())){
                        ctx.redirect("/401");
                    }
                });
//          GET CHATTO
                get(Path.Web.CHATTO, ChatController.fetchChatTo);
//          GET CHAT
                get(Path.Web.CHAT, ChatController.fetchChat);
//          GET EDITAR ARTICULOS
                get(Path.Web.ONE_ARTICLE, ArticuloController.fetchEditarOneArticuloCrud);
//          POST EDITAR ARTICULOS
                post(Path.Web.ONE_ARTICLE, ArticuloController.handleEditarArticlePost);
//          GET ELIMINAR ARTICULOS
                get(Path.Web.EIMINAR_ARTICLE, ArticuloController.handleEliminar);
//          GET CREAR ARTICULOS
                get(Path.Web.CREAR_ARTICLE, ArticuloController.serveCrearPage);
//          POST CREAR ARTICULOS
                post(Path.Web.CREAR_ARTICLE, ArticuloController.handleArticlePost);
//          GET CRUD ARTICULOS
                get(Path.Web.ARTICLES, ArticuloController.fetchAllArticulosCrud);
            });

            path("/", () -> {
//              CHATS
                get(Path.Web.FETCH_CHATS, ChatController.fetchChatsUser);
//              ARTICULOS
                get(Path.Web.FETCH_ARTICLES, ArticuloController.fetchArticulos);
//              BLOG / INDEX
                get(Path.Web.BLOG, ArticuloController.serveIndexPagina);
//              LOGIN
                get(Path.Web.LOGIN, LoginController.serveLoginPage);
                post(Path.Web.LOGIN, LoginController.handleLoginPost);
                post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
//              GET POST
                get(Path.Web.POST, ArticuloController.fetchOneArticuloBlog);
                post(Path.Web.POST, ArticuloController.handleCommentPost);
                get("/401", ViewUtil.unauthorized);
            });
        });

        app.error(404, ViewUtil.notFound);
//        app.error(401, ViewUtil.unauthorized);

        // Filtro para activarse antes de la llamadas al contexto.
        app.wsBefore("/mensajeServidor", wsHandler -> {
            System.out.println("Filtro para WS antes de la llamada ws");
            //ejecutar cualquier evento antes...
        });

        /*
          Definición del WS en Javalin en contexto
         */
        app.ws("/mensajeServidor/{from}/{to}", ws -> {

            ws.onConnect(ctx -> {
                userUsernameMapReceiv.put(ctx.session, ctx.pathParam("to"));
                System.out.println("TO " + ctx.pathParam("to"));
                userUsernameMapSender.put(ctx.session, ctx.pathParam("from"));
                broadcastMessage(userUsernameMapReceiv.get(ctx.session),(ctx.pathParam("from") + " joined the chat"), userUsernameMapSender.get(ctx.session));
                System.out.println(ctx.pathParam("from") + " joined the chat " + userUsernameMapReceiv.get(ctx.session));
            });

            ws.onMessage(ctx -> { 
                broadcastMessage(userUsernameMapReceiv.get(ctx.session), ctx.message(), userUsernameMapSender.get(ctx.session));
                System.out.println(ctx.message() + " Para: " + userUsernameMapReceiv.get(ctx.session)); 
            });

            ws.onClose(ctx -> {
                try{
                    userUsernameMapReceiv.remove(ctx.session);
                    userUsernameMapSender.remove(ctx.session);
                }catch(Exception e){
                    e.printStackTrace();
                }
            });

            ws.onError(ctx -> System.out.println("Ocurrió un error en el WS"));
        });

        /*
          Filtro para activarse despues de la llamadas al contexto.
         */
        app.wsAfter("/mensajeServidor", wsHandler -> {
            System.out.println("Filtro para WS despues de la llamada al WS");
            //ejecutar cualquier evento antes...
        });
    }

    // Sends a message from one user to all users, along with a list of current usernames
    private static void broadcastMessage(String receiver, String message, String sender) {
        for (Map.Entry<Session, String> entry : userUsernameMapSender.entrySet()) {
            if(entry.getValue().equalsIgnoreCase(receiver) && userUsernameMapReceiv.get(entry.getKey()).equalsIgnoreCase(sender)){
               try {
                   entry.getKey().getRemote().sendString(message);
               } catch (Exception e) {
                   e.printStackTrace();
               }
            }
        }
    }
}
