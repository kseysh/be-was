package db.config;

import db.*;

public class DatabaseConfig {
    private DatabaseConfig(){
    }

    public static final UserDatabase userDatabase = new UserDatabase(new CustomDataSource());
    public static final ArticleDatabase articleDatabase = new ArticleDatabase(new CustomDataSource());
    public static final ImageDatabase imageDatabase = new ImageDatabase(new CustomDataSource());
    public static final CommentDatabase commentDatabase = new CommentDatabase(new CustomDataSource());
}
