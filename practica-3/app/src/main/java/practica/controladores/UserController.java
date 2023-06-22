package practica.controladores;
import static practica.Main.*;
import static practica.util.RequestUtil.getParamId;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import practica.entidades.Foto;
import practica.entidades.Usuario;
import practica.util.Path;
import practica.util.RequestUtil;
import practica.util.ViewUtil;

import java.util.Base64;
import java.util.Map;

public class UserController {

    public static boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Usuario user = usuarioServices.find(username);
        if (user == null) {
            return false;
        }
//        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return password.equals(user.getPassword());
    }

    public static Handler fetchAllUsuarios = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("users", usuarioServices.findAll());
        ctx.render(Path.Template.USERS, model);
    };

    public static Handler serveCrearPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.ONE_USER, model);
    };

    public static Handler handleUsuarioPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        String nombre = RequestUtil.getQueryNombre(ctx);
        String username = RequestUtil.getQueryUsuario(ctx).toLowerCase();
        String password = RequestUtil.getQueryClave(ctx);
        boolean autor = RequestUtil.getQueryAutor(ctx);
        boolean admin = RequestUtil.getQueryAdmin(ctx);
        if(usuarioServices.find(username) == null){
            Usuario user = usuarioServices.crear(new Usuario(username, nombre, password, admin, autor));
            if(user != null){
                model.put("usuarioSucceeded", true);
                UploadedFile pic = RequestUtil.getUploadedFile(ctx);
                try {
                    byte[] bytes = pic.content().readAllBytes();
                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                    Foto foto = new Foto(pic.filename(), pic.contentType(), encodedString);
                    fotoServices.crear(foto);
                    user.setFoto(foto);
                    foto.setUser(user);
                    fotoServices.editar(foto);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else{
                model.put("usuarioFailed", true);
            }
        }else{
            model.put("usuarioFailed", true);
        }
        model.put("users", usuarioServices.findAll());
        ctx.render(Path.Template.USERS, model);
    };

    public static Handler fetchEditarOneUsuarioCrud = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Usuario user = usuarioServices.find(getParamId(ctx));
        if(user != null){
            model.put("user", user);
            ctx.render(Path.Template.ONE_USER, model);
        } else{
            ctx.redirect("/admin"+Path.Web.USERS);
        }
    };

    public static Handler handleEditarUsuarioPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Usuario user = usuarioServices.find(getParamId(ctx));
        user.setNombre(RequestUtil.getQueryNombre(ctx));
        user.setPassword(RequestUtil.getQueryClave(ctx));
        user.setAutor(RequestUtil.getQueryAutor(ctx));
        user.setAdministrador(RequestUtil.getQueryAdmin(ctx));
        if(usuarioServices.editar(user) != null){
            model.put("usuarioSucceeded", true);
        }else{
            model.put("usuarioFailed", true);
        }
        model.put("users", usuarioServices.findAll());
        ctx.render(Path.Template.USERS, model);
    };

    public static Handler handleEliminar = ctx -> {
        if(!RequestUtil.getSessionCurrentUser(ctx).equalsIgnoreCase(RequestUtil.getParamId(ctx))){
            usuarioServices.eliminar(RequestUtil.getParamId(ctx));
        }
        ctx.redirect("/admin"+Path.Web.USERS);
    };


}
