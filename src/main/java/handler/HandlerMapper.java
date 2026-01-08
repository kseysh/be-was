package handler;

import java.util.Map;

public class HandlerMapper {

    private static final Map<String, Handler> handlers;
    private static final String DEFAULT_PATH = "/";

    static {
        handlers = Map.of(
                "/login", LoginHandler.getInstance(),
                "/create", CreateUserHandler.getInstance(),
                "/registration", RegisterHandler.getInstance(),
                "/", HomeHandler.getInstance()
        );
    }

    private HandlerMapper() {
    }


    public static Handler getHandler(String path) {

        while (handlers.get(path) == null) {
            int lastSlashIndex = path.lastIndexOf('/');
            if (lastSlashIndex == 0) {
                break;
            } else {
                path = path.substring(0, lastSlashIndex);
            }
        }

        if (handlers.get(path) == null) {
            return handlers.get(DEFAULT_PATH);
        }
        return handlers.get(path);
    }
}
