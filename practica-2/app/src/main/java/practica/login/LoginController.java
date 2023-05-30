package practica.login;
import practica.usuario.UserController;
import practica.util.RequestUtil;
import practica.util.ViewUtil;
import io.javalin.http.Handler;
import practica.util.Path;

import java.util.Map;

public class LoginController {
    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("loggedOut", RequestUtil.removeSessionAttrLoggedOut(ctx));
        model.put("loginRedirect", RequestUtil.removeSessionAttrLoginRedirect(ctx));
        ctx.render(Path.Template.LOGIN, model);
    };

    public static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!UserController.authenticate(RequestUtil.getQueryUsuario(ctx), RequestUtil.getQueryClave(ctx))) {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.LOGIN, model);
        } else {
            ctx.sessionAttribute("currentUser", RequestUtil.getQueryUsuario(ctx));
            model.put("authenticationSucceeded", true);
            model.put("currentUser", RequestUtil.getQueryUsuario(ctx));
            if (RequestUtil.getFormParamRedirect(ctx) != null) {
                ctx.redirect(RequestUtil.getFormParamRedirect(ctx));
            }
            ctx.render(Path.Template.LOGIN, model);
        }
    };

    public static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.redirect(Path.Web.LOGIN);
    };
}
