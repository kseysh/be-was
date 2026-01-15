package handler;

import db.ArticleDatabase;
import db.config.DatabaseConfig;
import enums.HttpStatus;
import http.converter.*;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class ArticleLikeHandler extends AbstractHandler {

    private final ArticleDatabase articleDatabase = DatabaseConfig.articleDatabase;

    public ArticleLikeHandler() {
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) {
        HttpMessageConverter<Form> converter =
                HttpMessageConverterMapper.findHttpMessageConverter(Form.class, request.getContentType());
        Form<String, String> form = converter.read(request);

        articleDatabase.addLikeCount(Long.parseLong(form.get("articleId")));

        response.setStatusCode(HttpStatus.NO_CONTENT);
    }
}
