package practicaAula.welcome;

import java.util.Map;

import io.javalin.http.Handler;
import practicaAula.util.Path;
import practicaAula.util.ViewUtil;

public class WelcomeController {
  public static Handler bienvenida = ctx -> {
    Map<String, Object> model = ViewUtil.baseModel(ctx);
    ctx.render(Path.Template.WELCOME, model);
  };
}
