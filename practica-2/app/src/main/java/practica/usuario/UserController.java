package practica.usuario;
import static practica.Main.*;
import static practica.util.RequestUtil.getParamId;
import io.javalin.http.Handler;
import practica.util.Path;
import practica.util.RequestUtil;
import practica.util.ViewUtil;

import java.util.Map;


public class UserController {
    public static boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Usuario user = usuarioDao.getUsuarioByUsername(username);
        if (user == null) {
            return false;
        }
//        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return password.equals(user.getPassword());
    }

//    // This method doesn't do anything, it's just included as an example
    // public static void setPassword(String username, String oldPassword, String newPassword) {
    //     if (authenticate(username, oldPassword)) {
    //         String newSalt = BCrypt.gensalt();
    //         String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
    //         // Update the user salt and password
    //     }
    // }
    public static Handler fetchAllUsuarios = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("users", usuarioDao.getAllUsuarios());
        ctx.render(Path.Template.USERS, model);
    };

    public static Handler serveCrearPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.ONE_USER, model);
    };

    public static Handler handleUsuarioPost = ctx -> {
        String nombre = RequestUtil.getQueryNombre(ctx);
        String username = RequestUtil.getQueryUsuario(ctx);
        String password = RequestUtil.getQueryClave(ctx);
        boolean autor = RequestUtil.getQueryAutor(ctx);
        boolean admin = RequestUtil.getQueryAdmin(ctx);
        Usuario tmp = new Usuario(username, nombre, password, admin, autor);
        usuarioDao.crearUsuario(tmp);
        ctx.redirect("/admin"+Path.Web.USERS);
    };

    public static Handler fetchEditarOneUsuarioCrud = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Usuario user = usuarioDao.getUsuarioById(getParamId(ctx));
        if(user != null){
            model.put("user", user);
            ctx.render(Path.Template.ONE_USER, model);
        } else{
            ctx.redirect("/admin"+Path.Web.USERS);
        }
    };

    public static Handler handleEditarUsuarioPost = ctx -> {
        Usuario user = usuarioDao.getUsuarioById(getParamId(ctx));
        String name = RequestUtil.getQueryNombre(ctx);
        String username = RequestUtil.getQueryUsuario(ctx);
        String pwd = RequestUtil.getQueryClave(ctx);
        boolean autor = RequestUtil.getQueryAutor(ctx);
        boolean admin = RequestUtil.getQueryAdmin(ctx);
        user.editarUsuario(username, name, pwd, autor, admin);
        ctx.redirect("/admin"+Path.Web.USERS);
    };

    public static Handler handleEliminar = ctx -> {
        Usuario tmp = usuarioDao.getUsuarioById(RequestUtil.getParamId(ctx));
        if(tmp != null){
            usuarioDao.eliminandoUsuario(tmp);
        }
        ctx.redirect("/admin"+Path.Web.USERS);
    };


}
