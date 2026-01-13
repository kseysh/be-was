package handler;

import db.SessionManager;
import enums.HttpMethod;
import exception.HttpException;
import exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.TemplateView;
import webserver.view.View;

public class HomeHandler extends AbstractHandler {

    public HomeHandler() {
    }

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getPath().equals("/") || request.getPath().equals("/index.html") || request.getPath().equals("/main")) {
            String sid = request.getCookieValue("sid");
            Optional<User> user = SessionManager.getInstance().getAttribute(sid);
            if (user.isPresent()) {
                Map<String, Object> model = new HashMap<>();
                model.put("name", user.get().getName());
                View view = new TemplateView("/main/index.html");
                view.render(model, request, response);
            } else {
                View view = new StaticResourceView("/index.html");
                view.render(Collections.emptyMap(), request, response);
            }
        } else {
            View view = new StaticResourceView(request.getPath());
            view.render(Collections.emptyMap(), request, response);
        }
    }
}
