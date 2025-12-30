package handler;

import java.util.Map;

public class HandlerMapper {

    private static Map<String, Handler> handlers;
    private static boolean isInit = false;
    private static final String DEFAULT_PATH = "/";
    private HandlerMapper() {}

    private static void initMapping() {
        handlers = Map.of(
                "/create", new CreateUserHandler(),
                "/registration", new RegisterHandler(),
                "/", new StaticResourceHandler()
        );
    }

    public static Handler getHandler(String path){
        if(!isInit){
            initMapping();
            isInit = true;
        }

        while(handlers.get(path) == null){
            int lastSlashIndex = path.lastIndexOf('/');
            if (lastSlashIndex == 0) {
                break;
            } else {
                path = path.substring(0, lastSlashIndex);
            }
        }

        if(handlers.get(path) == null) return handlers.get(DEFAULT_PATH);
        return handlers.get(path);
    }
}
