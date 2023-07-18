package practica.util;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.util.List;

public class RequestUtil {
    public static UploadedFile getUploadedFile(Context ctx) {
        return ctx.uploadedFile("foto");
    }


    public static String getParamId(Context ctx) {
        return ctx.pathParam("id");
    }

    public static String getParamPagina(Context ctx) {
        return ctx.pathParam("pagina");
    }

    public static String getQueryPagina(Context ctx) {
        return ctx.queryParam("pagina");
    }

    public static String getQueryEtiqueta(Context ctx) {
        return ctx.queryParam("etiqueta");
    }

    public static String getParamUsername(Context ctx) {
        return ctx.pathParam("username");
    }

//  USUARIOS
    public static String getQueryUsuario(Context ctx) {
        return ctx.formParam("usuario");
    }

    public static String getQueryClave(Context ctx) {
        return ctx.formParam("clave");
    }
    public static String getQueryNombre(Context ctx) {
        return ctx.formParam("nombre");
    }
    public static boolean getQueryAutor(Context ctx) {
        return ctx.formParam("autor") != null;
    }
    public static boolean getQueryAdmin(Context ctx) {
        return ctx.formParam("admin") != null;
    }
    public static boolean getQueryCookie(Context ctx) {
        return ctx.formParam("cookie") != null;
    }
    public static String getFormParamRedirect(Context ctx) {
        return ctx.formParam("loginRedirect");
    }

    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }

//  ARTICULOS
    public static String getQueryTitulo(Context ctx) {
    return ctx.formParam("titulo");
}
    public static String getQueryComment(Context ctx) {
        return ctx.formParam("comment");
    }

    public static String getQueryDescripcion(Context ctx) {
        return ctx.formParam("descripcion");
    }

    public static List<String> getQueryEtiquetas(Context ctx) {
        return ctx.formParams("etiquetas[]");
    }

    public static boolean removeSessionAttrLoggedOut(Context ctx) {
        String loggedOut = ctx.sessionAttribute("loggedOut");
        ctx.sessionAttribute("loggedOut", null);
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Context ctx) {
        String loginRedirect = ctx.sessionAttribute("loginRedirect");
        ctx.sessionAttribute("loginRedirect", null);
        return loginRedirect;
    }
}
