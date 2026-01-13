package handler;

import db.Database;
import db.SessionManager;
import exception.HttpException;
import http.converter.HttpMessageConverter;
import http.converter.HttpMessageConverterMapper;
import http.converter.ImageForm;
import http.converter.MultipartData;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Base64;
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

    public ArticleHandler() {
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) {
        String sid = request.getCookieValue("sid");
        Optional<User> user = SessionManager.getInstance().getAttribute(sid);
        if (user.isPresent()) {
            HttpMessageConverter<MultipartData> converter =
                    HttpMessageConverterMapper.findHttpMessageConverter(MultipartData.class, request.getContentType());
            MultipartData multipartData = converter.read(request);

            String title = multipartData.getTexts("title");
            String content = multipartData.getTexts("content");
            ImageForm imageForm = multipartData.getFileBytes("image");

            Image image = Image.from(imageForm);
            Database.addImage(image);
            Article article = new Article(UUID.randomUUID().toString(), title, content, user.get().getUserId(), image.imageId());
            Database.addArticle(article);

            Map<String, Object> model = new HashMap<>();

            model.put("name", user.get().getName());
            model.put("title", title);
            model.put("content", content);
            model.put("image", "data:" + imageForm.contentType().getMimeType() + ";base64," + Base64.getEncoder().encodeToString(imageForm.bytes()));

            View view = new TemplateView("/main/index.html");
            view.render(model, request, response);
        } else {
            View view = new StaticResourceView("/login/index.html");
            view.render(Collections.emptyMap(), request, response);
        }
    }

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
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
