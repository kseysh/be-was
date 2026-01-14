package handler;

import db.*;
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
import java.util.UUID;
import model.Article;
import model.Image;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.TemplateView;
import webserver.view.View;

public class ArticleHandler extends AbstractHandler {

    private final ArticleDatabase articleDatabase = DatabaseConfig.articleDatabase;
    private final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;

    public ArticleHandler() {
    }

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
            Article article = new Article(UUID.randomUUID().toString(), content, user.get().getUserId(), image.imageId(), 0);
            articleDatabase.save(article);

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.get().getName());
            model.put("content", content);
            model.put("image", image.toImageString());

            // TODO: 새로운 게시물의 화면으로 이동할 수 있어야 한다.
            View view = new TemplateView("/main/index.html");
            view.render(model, request, response);
        } else {
            View view = new StaticResourceView("/login/index.html");
            view.render(Collections.emptyMap(), request, response);
        }
    }
}
