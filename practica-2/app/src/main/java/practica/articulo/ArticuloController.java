package practica.articulo;

import io.javalin.http.Handler;
import practica.etiqueta.Etiqueta;
import practica.usuario.Usuario;
import practica.util.Path;
import practica.util.RequestUtil;
import practica.util.ViewUtil;

import java.util.List;
import java.util.Map;

import static practica.Main.*;
import static practica.util.RequestUtil.*;

public class ArticuloController {

    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("articles", articuloDao.getAllArticulos());
        ctx.render(Path.Template.BLOG, model);
    };

    public static Handler fetchAllArticulosCrud = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("articles", articuloDao.getAllArticulos());
        ctx.render(Path.Template.ARTICLES, model);
    };

    public static Handler fetchEditarOneArticuloCrud = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Articulo article = articuloDao.getArticuloById(getParamId(ctx));
        if(article != null){
            model.put("etiquetas", etiquetaDao.getAllEiquetas());
            model.put("article", article);
            ctx.render(Path.Template.ONE_ARTICLE, model);
        } else{
            ctx.redirect("/autor"+Path.Web.ARTICLES);
        }
    };

    public static Handler handleEditarArticlePost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("articles", articuloDao.getAllArticulos());
        Articulo article = articuloDao.getArticuloById(getParamId(ctx));
        String titulo = RequestUtil.getQueryTitulo(ctx);
        String descripcion = RequestUtil.getQueryDescripcion(ctx);
        Usuario user = usuarioDao.getUsuarioByUsername(RequestUtil.getSessionCurrentUser(ctx));
        List<Etiqueta> tags = etiquetaDao.crearListaEtiquetas(RequestUtil.getQueryEtiquetas(ctx));
        if(article.getCuerpo().equalsIgnoreCase(descripcion) || articuloDao.getArticuloByCuerpo(descripcion) == null){
            article.editarArticulo(titulo, descripcion, user, tags);
            model.put("articleSucceeded", true);
        }else{
            model.put("articleFailed", true);
        }
        ctx.render(Path.Template.ARTICLES, model);
    };

    public static Handler serveCrearPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("etiquetas", etiquetaDao.getAllEiquetas());
        ctx.render(Path.Template.ONE_ARTICLE, model);
    };

    public static Handler handleArticlePost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("articles", articuloDao.getAllArticulos());
        String titulo = RequestUtil.getQueryTitulo(ctx);
        String descripcion = RequestUtil.getQueryDescripcion(ctx);
        Usuario user = usuarioDao.getUsuarioByUsername(RequestUtil.getSessionCurrentUser(ctx));
        List<Etiqueta> tags = etiquetaDao.crearListaEtiquetas(RequestUtil.getQueryEtiquetas(ctx));
        if(articuloDao.crearArticulo(titulo, descripcion, user, tags)){
            model.put("articleSucceeded", true);
        }else{
            model.put("articleFailed", true);
        }
        ctx.render(Path.Template.ARTICLES, model);
    };

    public static Handler handleEliminar = ctx -> {
        Articulo tmp = articuloDao.getArticuloById(RequestUtil.getParamId(ctx));
        if(tmp != null){
            articuloDao.EliminarArticulo(tmp);
        }
        ctx.redirect("/autor"+Path.Web.ARTICLES);
    };

    public static Handler handleCommentPost = ctx -> {
        Articulo article = articuloDao.getArticuloById(getParamId(ctx));
        String comentario = RequestUtil.getQueryComment(ctx);
        Usuario user = usuarioDao.getUsuarioByUsername(RequestUtil.getSessionCurrentUser(ctx));
        article.addComentario(comentario, user, article);
        ctx.redirect("/articulo/"+article.getId());
    };

    public static Handler fetchOneArticuloBlog = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Articulo art = articuloDao.getArticuloById(getParamId(ctx));
        model.put("article", art);
        model.put("comments", art.getListaComentarios());
        ctx.render(Path.Template.POST, model);
    };
}
