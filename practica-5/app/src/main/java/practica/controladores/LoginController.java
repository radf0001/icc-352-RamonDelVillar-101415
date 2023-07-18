package practica.controladores;
import practica.util.RequestUtil;
import practica.util.ViewUtil;
import io.javalin.http.Handler;
import practica.util.Path;
import static practica.Main.*;

import java.util.Date;
import java.util.Map;

public class LoginController {
    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("loggedOut", RequestUtil.removeSessionAttrLoggedOut(ctx));
        model.put("loginRedirect", RequestUtil.removeSessionAttrLoginRedirect(ctx));
        model.put("ckUser", ctx.cookie("user"));
        model.put("ckPwd", ctx.cookie("pwd"));
        ctx.render(Path.Template.LOGIN, model);
    };

    public static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!UserController.authenticate(RequestUtil.getQueryUsuario(ctx).toLowerCase(), RequestUtil.getQueryClave(ctx))) {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.LOGIN, model);
        } else {
            String user = RequestUtil.getQueryUsuario(ctx).toLowerCase();
            ctx.sessionAttribute("currentUser", user);
            model.put("authenticationSucceeded", true);
            model.put("currentUser", user);
            model.put("layoutUser", usuarioServices.find(user));
            if (RequestUtil.getFormParamRedirect(ctx) != null) {
                ctx.redirect(RequestUtil.getFormParamRedirect(ctx));
            }
            if(RequestUtil.getQueryCookie(ctx)){
                ctx.cookie("user", user, 604800);
                ctx.cookie("pwd", RequestUtil.getQueryClave(ctx), 604800);
            } else{
                ctx.removeCookie("user");
                ctx.removeCookie("pwd");  
            }
            cockroachServices.crearLog(user, new java.sql.Timestamp(new Date().getTime()));
            ctx.render(Path.Template.LOGIN, model);
        }
    };

    public static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.removeCookie("user");
        ctx.removeCookie("pwd"); 
        ctx.redirect(Path.Web.LOGIN);
    };
}
