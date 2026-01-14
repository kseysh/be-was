package handler;

import java.util.Map;

public class HandlerMapper {

    private static final Map<String, Handler> handlers;
    private static final String DEFAULT_PATH = "/";

    static {
        handlers = Map.of(
                "/login", new LoginHandler(),
                "/logout", new LogoutHandler(),
                "/create", new CreateUserHandler(),
                "/registration", new RegisterHandler(),
                "/mypage", new MyPageHandler(),
                "/article", new ArticleHandler(),
                "/article/like", new ArticleLikeHandler(),
                "/comment", new CommentHandler(),
                "/", new HomeHandler()
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
