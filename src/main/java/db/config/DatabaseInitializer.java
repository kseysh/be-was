package db.config;

import db.ArticleDatabase;
import db.CommentDatabase;
import db.ImageDatabase;
import db.UserDatabase;
import model.Article;
import model.Comment;
import model.Image;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static model.Article.newArticle;
import static model.Comment.newComment;
import static model.User.newUser;

public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    private static final UserDatabase userDatabase = DatabaseConfig.userDatabase;
    private static final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;
    private static final ArticleDatabase articleDatabase = DatabaseConfig.articleDatabase;
    private static final CommentDatabase commentDatabase = DatabaseConfig.commentDatabase;

    private static final String DDL_SQL_PATH = "./src/main/resources/sql/schema.sql";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void init() {
        try {
            String ddlSql = Files.readString(Paths.get(DDL_SQL_PATH));

            DataSource dataSource = new CustomDataSource();
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement()) {
                stmt.execute(ddlSql);
                logger.info("Database initialize Complete");
            } catch (SQLException e) {
                throw new RuntimeException("DB Initialize Error: ", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("DB Initialize File Not Found: ", e);
        }

        createUser(5);
        createArticle(10);
        createComments(20);
    }

    private static void createComments(int count) {
        List<User> users = userDatabase.findAll();
        List<Article> articles = articleDatabase.findAll();
        for (int i = 0; i < count; i++) {
            Comment comment = newComment(
                    "hello" + i,
                    users.get(i % users.size()).getUserId(),
                    articles.get(i % articles.size()).getArticleId()
            );
            commentDatabase.save(comment);
        }
    }

    private static void createArticle(int count) {
        List<User> users = userDatabase.findAll();
        for (int i = 0; i < count; i++) {
            Image image = Image.defaultImage();
            imageDatabase.save(image);
            Article article = newArticle(
                    "hello" + i,
                    users.get(i % users.size()).getUserId(),
                    image.imageId()
            );
            articleDatabase.save(article);
        }
    }

    private static void createUser(int count) {
        for (int i = 0; i < count; i++) {
            Image image = Image.defaultImage();
            imageDatabase.save(image);
            User user = newUser("password" + i, "name" + i, null, image.imageId());
            userDatabase.save(user);
        }
    }
}
