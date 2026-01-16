package handler;

import db.ArticleDatabase;
import db.CommentDatabase;
import db.ImageDatabase;
import db.UserDatabase;
import db.cache.SessionManager;
import db.config.DatabaseConfig;
import exception.HttpException;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Article;
import model.Image;
import model.User;
import webserver.view.TemplateView;
import webserver.view.View;

public class HomeHandler extends AbstractHandler {

    private static final UserDatabase userDatabase = DatabaseConfig.userDatabase;
    private static final ArticleDatabase articleDatabase = DatabaseConfig.articleDatabase;
    private static final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;
    private static final CommentDatabase commentDatabase = DatabaseConfig.commentDatabase;

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getPath().equals("/") || request.getPath().equals("/index.html") || request.getPath().equals("/main")) {
            String sid = request.getCookieValue("sid");
            Optional<User> user = SessionManager.getInstance().getAttribute(sid);
            provideHomeView(request, response, user);
        } else {
            provideResource(request, response);
        }
    }

    private void provideResource(HttpRequest request, HttpResponse response) {
        response.respondWithStaticFile(request.getPath());
    }

    private void provideHomeView(HttpRequest request, HttpResponse response, Optional<User> user) {
        Map<String, Object> model = new HashMap<>();
        user.ifPresent(u -> model.put("name", u.getName()));

        String articleIdStr = request.getQuery().get("articleId");

        if (articleIdStr == null) { // 첫 페이지를 제공
            articleDatabase.findRecentArticle()
                    .ifPresent(article -> collectArticleModel(model, article));
        } else {
            articleDatabase.findById(Long.parseLong(articleIdStr))
                    .ifPresent(article -> collectArticleModel(model, article));
        }

        View view = new TemplateView("/main/index.html");
        view.render(model, request, response);
    }

    private void collectArticleModel(Map<String, Object> model, Article article) {
        collectArticleWriterInfo(model, article.getUserId());
        collectArticleInfo(model, article);
        collectCommentsInfo(model, article);
    }

    private void collectCommentsInfo(Map<String, Object> model, Article article) {
        List<CommentResponse> comments = commentDatabase.findAllByArticleId(article.getArticleId())
                .stream().map(comment -> {
                    User writer = userDatabase.findByIdOrElseThrow(comment.getUserId());
                    Image writerProfileImage = imageDatabase.findByIdOrElseThrow(writer.getProfileImageId());
                    return new CommentResponse(
                            writer.getName(),
                            writerProfileImage,
                            comment.getContent()
                    );
                }).toList();
        model.put("comments", comments);
        model.put("commentCount", comments.size());
    }

    private void collectArticleInfo(Map<String, Object> model, Article article) {
        model.put("likeCount", article.getLikeCount());
        model.put("articleContent", article.getContent());
        Image articleImage = imageDatabase.findByIdOrElseThrow(article.getImageId());
        model.put("articleImage", articleImage.toImageString());
        model.put("articleId", article.getArticleId());
        articleDatabase.findPreviousArticle(article.getArticleId())
                .ifPresent(pa -> model.put("previousArticleId", pa.getArticleId()));
        articleDatabase.findNextArticle(article.getArticleId())
                .ifPresent(na -> model.put("nextArticleId", na.getArticleId()));
    }

    private void collectArticleWriterInfo(Map<String, Object> model, String userId) {
        User author = userDatabase.findByIdOrElseThrow(userId);
        Image authorProfileImage = imageDatabase.findByIdOrElseThrow(author.getProfileImageId());
        model.put("authorName", author.getName());
        model.put("authorProfileImage", authorProfileImage.toImageString());
    }
}
