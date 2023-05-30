package practica.util;

public class Path {
    public static class Web {
        public static final String BLOG = "/";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String USERS = "/usuariosCrud";
        public static final String ONE_USER = "/editarUsuario/{id}";
        public static final String CREAR_USER = "/crearUsuario";
        public static final String ARTICLES = "/articulosCrud";
        public static final String ONE_ARTICLE = "/editarArticulo/{id}";
        public static final String CREAR_ARTICLE = "/crearArticulo";
        public static final String POST = "/articulo/{id}";
        public static final String EIMINAR_ARTICLE = "/eliminarArticulo/{id}";
        public static final String ELIMINAR_USER = "/eliminarUsuario/{id}";
    }

    public static class Template {
        public static final String BLOG = "/velocity/blog.vm";
        public static final String LOGIN = "/velocity/login.vm";
        public static final String LOGOUT = "/velocity/logout.vm";
        public static final String USERS = "/velocity/usersCrud.vm";
        public static final String ONE_USER = "/velocity/userCrear.vm";
        public static final String ARTICLES = "/velocity/articleCrud.vm";
        public static final String ONE_ARTICLE = "/velocity/articleCrear.vm";
        public static final String POST = "/velocity/post.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }
}
