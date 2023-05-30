package practica;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinVelocity;
import practica.util.RequestUtil;
import practica.articulo.ArticuloDao;
import practica.usuario.UserController;
import practica.etiqueta.EtiquetaDao;
import practica.login.LoginController;
import practica.usuario.Usuario;
import practica.usuario.UsuarioDao;
import practica.util.ViewUtil;
import practica.util.Path;
import practica.articulo.ArticuloController;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Main {
    public static UsuarioDao usuarioDao;
    public static ArticuloDao articuloDao;
    public static EtiquetaDao etiquetaDao;

    public static void main(String[] args) {

        JavalinRenderer.register(new JavalinVelocity(), ".vm");
        usuarioDao = new UsuarioDao();
        usuarioDao.crearUsuario(new Usuario("admin", "admin", "admin", true, true));
        articuloDao = new ArticuloDao();
        etiquetaDao = new EtiquetaDao();

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
            config.plugins.enableCors(corsContainer -> {
                corsContainer.add(corsPluginConfig -> {
                    corsPluginConfig.anyHost();
                });
            });

            //habilitando el plugins de las rutas definidas.
            config.plugins.enableRouteOverview("/routes");

        });

        app.start(7000);

        app.routes(() -> {
            path("/admin", () -> {
                before(ctx -> {
                    //recuperando el usuario de la sesión,en caso de no estar, redirecciona la pagina 401.
                    Usuario usuario = usuarioDao.getUsuarioByUsername(RequestUtil.getSessionCurrentUser(ctx));
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
                    Usuario usuario = usuarioDao.getUsuarioByUsername(RequestUtil.getSessionCurrentUser(ctx));
                    if(usuario == null || (!usuario.isAdministrador() && !usuario.isAutor())){
                        ctx.redirect("/401");
                    }
                });
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
//          BLOG / INDEX
                get(Path.Web.BLOG, ArticuloController.serveIndexPage);
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
    }

}
