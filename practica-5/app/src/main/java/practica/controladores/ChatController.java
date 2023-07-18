package practica.controladores;

import io.javalin.http.Handler;
import practica.util.Path;
import practica.util.RequestUtil;
import practica.util.ViewUtil;
import static practica.Main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;

public class ChatController {
    public static Handler fetchChat = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.CHAT, model);
    };

    public static Handler fetchChatTo = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("to", ctx.pathParam("to"));
        ctx.render(Path.Template.CHATTO, model);
    };

    public static Handler fetchChatsUser = ctx -> {
        List<String> chats = new ArrayList<>();;
        for (Map.Entry<Session, String> entry : userUsernameMapReceiv.entrySet()) {
            if(entry.getValue().equalsIgnoreCase(RequestUtil.getSessionCurrentUser(ctx))){
                chats.add(userUsernameMapSender.get(entry.getKey()));
            }
        }
        ctx.json(chats);
    };
}
