package db;

import db.config.CustomJdbcTemplate;
import db.config.RowMapper;
import model.Article;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static model.Article.newArticle;

public class ArticleDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "select * from ARTICLE where ARTICLE_ID = ?";
    private static final String INSERT_SQL = "insert into ARTICLE (CONTENT, USER_ID, IMAGE_ID, LIKE_COUNT) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update ARTICLE set CONTENT = ?, USER_ID = ?, IMAGE_ID = ?, LIKE_COUNT = ? where ARTICLE_ID = ?";
    private static final String UPDATE_LIKE_COUNT_SQL = "update ARTICLE set LIKE_COUNT = LIKE_COUNT + 1 where ARTICLE_ID = ?";

    public ArticleDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(Article article) {
        jdbcTemplate.update(INSERT_SQL,
                article.getContent(),
                article.getUserId(),
                article.getImageId(),
                article.getLikeCount()
        );
    }

    public void update(Article article) {
        jdbcTemplate.update(UPDATE_SQL,
                article.getContent(),
                article.getUserId(),
                article.getImageId(),
                article.getArticleId(),
                article.getLikeCount()
        );
    }

    public Optional<Article> findById(String articleId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new ArticleRowMapper(), articleId);
    }

    public void addLikeCount(String articleId) {
        jdbcTemplate.update(UPDATE_LIKE_COUNT_SQL, articleId);
    }

    static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs) throws SQLException {
            return newArticle(
                    rs.getString("CONTENT"),
                    rs.getString("USER_ID"),
                    rs.getString("IMAGE_ID")
            );
        }
    }
}
