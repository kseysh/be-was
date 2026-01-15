package handler;

import db.*;
import db.cache.SessionManager;
import db.config.DatabaseConfig;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.HttpException;
import http.converter.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.TemplateView;
import webserver.view.View;

import java.util.*;

import static model.Comment.newComment;

public class CommentHandler extends AbstractHandler {

    private static final CommentDatabase commentDatabase = DatabaseConfig.commentDatabase;

    public CommentHandler() {
    }

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        String sid = request.getCookieValue("sid");
        Optional<User> user = SessionManager.getInstance().getAttribute(sid);
        if (user.isPresent()) {
            Map<String, Object> model = new HashMap<>();
            model.put("name", user.get().getName());
            model.put("articleId", request.getQuery().get("articleId"));
            View view = new TemplateView("/comment/index.html");
            view.render(model, request, response);
        } else {
            View view = new StaticResourceView("/login/index.html");
            view.render(Collections.emptyMap(), request, response);
        }
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) {
        String sid = request.getCookieValue("sid");
        Optional<User> user = SessionManager.getInstance().getAttribute(sid);
        if (user.isPresent()) {
            HttpMessageConverter<Form> converter =
                    HttpMessageConverterMapper.findHttpMessageConverter(Form.class, request.getContentType());
            Form<String, String> form = converter.read(request);

            String comment = form.get("content");
            Long articleId = Long.parseLong(form.get("articleId"));
            String userId = user.get().getUserId();

            commentDatabase.save(newComment(comment, userId, articleId));

            response.setStatusCode(HttpStatus.FOUND)
                    .setHeader(HttpHeader.LOCATION.getValue(), "/?articleId=" + articleId);
        } else {
            View view = new StaticResourceView("/login/index.html");
            view.render(Collections.emptyMap(), request, response);
        }
    }
}
