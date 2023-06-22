package practica.util;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import practica.services.UsuarioServices;

import java.util.HashMap;
import java.util.Map;

import static practica.util.RequestUtil.*;

public class ViewUtil {
    public static Map<String, Object> baseModel(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("currentUser", getSessionCurrentUser(ctx));
        model.put("layoutUser", UsuarioServices.getInstancia().find(getSessionCurrentUser(ctx)));
        return model;
    }

    public static Handler notFound = ctx -> {
        ctx.render(Path.Template.NOT_FOUND, baseModel(ctx));
    };

    public static Handler unauthorized = ctx -> {
        ctx.render("/velocity/Unauthorized.vm", baseModel(ctx));
    };
}
