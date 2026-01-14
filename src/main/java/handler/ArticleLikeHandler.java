package handler;

import db.ArticleDatabase;
import db.DatabaseConfig;
import db.ImageDatabase;
import db.SessionManager;
import enums.HttpStatus;
import exception.HttpException;
import http.converter.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Article;
import model.Image;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.TemplateView;
import webserver.view.View;

import java.util.*;

public class ArticleLikeHandler extends AbstractHandler {

    private final ArticleDatabase articleDatabase = DatabaseConfig.articleDatabase;

    public ArticleLikeHandler() {
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) {
        HttpMessageConverter<Form> converter =
                HttpMessageConverterMapper.findHttpMessageConverter(Form.class, request.getContentType());
        Form<String, String> form = converter.read(request);

        String articleId = form.get("articleId");
        articleDatabase.addLikeCount(articleId);

        response.setStatusCode(HttpStatus.NO_CONTENT);
    }
}
