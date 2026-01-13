package db;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.Article;
import model.Image;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {

    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static final Map<String, Article> articles = new ConcurrentHashMap<>();
    private static final Map<String, Image> images = new ConcurrentHashMap<>();

    private Database() {
    }

    public static void addImage(Image image) {
        images.put(image.imageId(), image);
    }

    public static void updateImageById(String imageId, Image updatedImage) {
        images.put(imageId, updatedImage);
    }

    public static Image findImageById(String imageId) {
        return images.get(imageId);
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static void addArticle(Article article) {
        articles.put(article.articleId(), article);
    }

    public static Optional<User> findUserByUserId(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void updateUser(String userId, User user) {
        users.put(userId, user);
    }
}
