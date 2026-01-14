package db;

public class DatabaseConfig {
    public static UserDatabase userDatabase = new UserDatabase(new CustomDataSource());
    public static ArticleDatabase articleDatabase = new ArticleDatabase(new CustomDataSource());
    public static ImageDatabase imageDatabase = new ImageDatabase(new CustomDataSource());
    public static CommentDatabase commentDatabase = new CommentDatabase(new CustomDataSource());
}
