package handler;

import db.*;
import db.cache.SessionManager;
import db.config.DatabaseConfig;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.HttpException;
import http.converter.HttpMessageConverter;
import http.converter.HttpMessageConverterMapper;
import http.converter.ImageForm;
import http.converter.MultipartData;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import model.Image;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.TemplateView;
import webserver.view.View;

import static model.Article.newArticle;

public class ArticleHandler extends AbstractHandler {

    private static final ArticleDatabase articleDatabase = DatabaseConfig.articleDatabase;
    private static final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        String sid = request.getCookieValue("sid");
        Optional<User> user = SessionManager.getInstance().getAttribute(sid);
        if (user.isPresent()) {
            Map<String, Object> model = new HashMap<>();
            model.put("name", user.get().getName());

            View view = new TemplateView("/article/index.html");
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
            HttpMessageConverter<MultipartData> converter =
                    HttpMessageConverterMapper.findHttpMessageConverter(MultipartData.class, request.getContentType());
            MultipartData multipartData = converter.read(request);

            String content = multipartData.getTexts("content");
            ImageForm imageForm = multipartData.getFileBytes("image");

            Image image = Image.from(imageForm);
            imageDatabase.save(image);
            Long articleId = articleDatabase.saveAndGetKey(newArticle(content, user.get().getUserId(), image.imageId()));

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.get().getName());
            model.put("content", content);
            model.put("image", image.toImageString());

            response.setStatusCode(HttpStatus.FOUND)
                    .setHeader(HttpHeader.LOCATION.getValue(), "/main?articleId=" + articleId);
        } else {
            response.setStatusCode(HttpStatus.FOUND)
                    .setHeader(HttpHeader.LOCATION.getValue(), "/login");
        }
    }
}
