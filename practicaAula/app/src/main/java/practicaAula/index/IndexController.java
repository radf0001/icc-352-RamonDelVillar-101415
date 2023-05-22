package practicaAula.index;

import io.javalin.http.Handler;
import practicaAula.util.Path;
import practicaAula.util.ViewUtil;

import static practicaAula.Main.*;

import java.util.Map;

public class IndexController {
    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("users", userDao.getAllUserNames());
        ctx.render(Path.Template.INDEX, model);
    };
}
