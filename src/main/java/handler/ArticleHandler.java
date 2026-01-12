package handler;

import db.SessionManager;
import enums.HttpMethod;
import exception.HttpException;
import exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collections;
import java.util.Optional;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.View;

public class ArticleHandler implements Handler {

    private static final ArticleHandler INSTANCE = new ArticleHandler();

    private ArticleHandler() {
    }

    public static ArticleHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getMethod() == HttpMethod.GET) {
            get(request, response);
        } else {
            throw new MethodNotAllowedException("Not Supported Method");
        }
    }

    private void get(HttpRequest request, HttpResponse response) throws HttpException {
        String sid = request.getCookieValue("sid");
        Optional<User> user = SessionManager.getInstance().getAttribute(sid);
        if (user.isPresent()) {
            View view = new StaticResourceView("/article/index.html");
            view.render(Collections.emptyMap(), request, response);
        } else {
            View view = new StaticResourceView("/login/index.html");
            view.render(Collections.emptyMap(), request, response);
        }
    }
}
