package practica.controladores;

import io.javalin.http.Handler;
import practica.entidades.Articulo;
import practica.entidades.Comentario;
import practica.entidades.Etiqueta;
import practica.entidades.Usuario;
import practica.util.Path;
import practica.util.RequestUtil;
import practica.util.ViewUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static practica.Main.*;
import static practica.util.RequestUtil.*;

public class ArticuloController {

    public static Handler fetchArticulos = ctx -> {
        String pag = RequestUtil.getQueryPagina(ctx);
        String tag = RequestUtil.getQueryEtiqueta(ctx);
        List<Articulo> articulos;
        try{
            articulos = articuloServices.paginationtiquetas(tag, Integer.parseInt(pag));
        }catch (Exception e) {
            // TODO: handle exception
            articulos = articuloServices.paginationtiquetas(tag, 0);
        }
        ctx.json(articulos);
    };

    public static Handler serveIndexPagina = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        String pag = RequestUtil.getQueryPagina(ctx);
        String tag = RequestUtil.getQueryEtiqueta(ctx);
        try{
            model.put("articles", articuloServices.paginationtiquetas(tag, Integer.parseInt(pag)));
            if(articuloServices.paginationtiquetas(tag, Integer.parseInt(pag)+5).isEmpty()){
                model.put("pagina", Integer.parseInt(pag));    
            }else{
                model.put("pagina", Integer.parseInt(pag)+5);
            }
            if(pag.equals("0")){
                model.put("paginaprev", Integer.parseInt(pag));    
            }else{
                model.put("paginaprev", Integer.parseInt(pag)-5);
            }
        }catch (Exception e) {
            // TODO: handle exception
            model.put("articles", articuloServices.paginationtiquetas(tag, 0));
            model.put("pagina", 5);
        }
        model.put("etiquetas", etiquetaServices.findAll());
        model.put("tag", etiquetaServices.find(tag));
        ctx.render(Path.Template.BLOG, model);
    };

    public static Handler fetchAllArticulosCrud = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("articles", articuloServices.findAll());
        ctx.render(Path.Template.ARTICLES, model);
    };

    public static Handler fetchEditarOneArticuloCrud = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Articulo article = articuloServices.find(getParamId(ctx));
        if(article != null){
            model.put("etiquetas", etiquetaServices.findAll());
            model.put("article", article);
            ctx.render(Path.Template.ONE_ARTICLE, model);
        } else{
            ctx.redirect("/autor"+Path.Web.ARTICLES);
        }
    };

    public static Handler handleEditarArticlePost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Articulo tmp = articuloServices.find(getParamId(ctx));
        Usuario user = usuarioServices.find(RequestUtil.getSessionCurrentUser(ctx));
        String descripcion = RequestUtil.getQueryDescripcion(ctx).toLowerCase();
        String titulo = RequestUtil.getQueryTitulo(ctx).toLowerCase();
        Set<Etiqueta> tags = new HashSet<>(etiquetaServices.findAllById(RequestUtil.getQueryEtiquetas(ctx)));
        if(tmp.getCuerpo().equalsIgnoreCase(descripcion) || articuloServices.getArticuloByCuerpo(descripcion).isEmpty()){
            tmp.setAutor(user);
            tmp.setCuerpo(descripcion);
            tmp.setTitulo(titulo);
            tmp.setListaEtiquetas(tags);
            articuloServices.editar(tmp);
            model.put("articleSucceeded", true);
        }else{
            model.put("articleFailed", true);
        }
        model.put("articles", articuloServices.findAll());
        ctx.render(Path.Template.ARTICLES, model);
    };

    public static Handler serveCrearPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("etiquetas", etiquetaServices.findAll());
        ctx.render(Path.Template.ONE_ARTICLE, model);
    };

    public static Handler handleArticlePost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        String titulo = RequestUtil.getQueryTitulo(ctx).toLowerCase();
        String descripcion = RequestUtil.getQueryDescripcion(ctx).toLowerCase();
        Usuario user = usuarioServices.find(RequestUtil.getSessionCurrentUser(ctx));
        Set<Etiqueta> tags = new HashSet<>(etiquetaServices.findAllById(RequestUtil.getQueryEtiquetas(ctx)));
        if(articuloServices.getArticuloByCuerpo(descripcion).isEmpty() && articuloServices.crear(new Articulo(titulo, descripcion, user, tags)) != null){
            model.put("articleSucceeded", true);
        }else{
            model.put("articleFailed", true);
        }
        model.put("articles", articuloServices.findAll());
        ctx.render(Path.Template.ARTICLES, model);
    };

    public static Handler handleEliminar = ctx -> {
        articuloServices.eliminar(RequestUtil.getParamId(ctx));
        ctx.redirect("/autor"+Path.Web.ARTICLES);
    };

    public static Handler fetchOneArticuloBlog = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Articulo art = articuloServices.find(getParamId(ctx));
        if(art != null){
            model.put("article", art);
            model.put("comments", art.getListaComentarios());
            ctx.render(Path.Template.POST, model);
        }else{
            ctx.status(404);
        }
    };

    public static Handler handleCommentPost = ctx -> {
        Articulo article = articuloServices.find(getParamId(ctx));
        String comentario = RequestUtil.getQueryComment(ctx).toLowerCase();
        Usuario user = usuarioServices.find(RequestUtil.getSessionCurrentUser(ctx));
        article.addComment(new Comentario(comentario, user));
        articuloServices.editar(article);
        
        ctx.redirect("/articulo/"+article.getId());
    };
}
