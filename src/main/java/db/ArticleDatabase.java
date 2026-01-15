package db;

import db.config.CustomJdbcTemplate;
import db.config.RowMapper;
import model.Article;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ArticleDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "select * from ARTICLE where ARTICLE_ID = ?";
    private static final String SELECT_RECENT_SQL = "select * from ARTICLE order by ARTICLE_ID desc limit 1";
    private static final String SELECT_PREV_ARTICLE_SQL = "SELECT * FROM ARTICLE WHERE ARTICLE_ID < ? ORDER  BY ARTICLE_ID DESC LIMIT 1";
    private static final String SELECT_NEXT_ARTICLE_SQL = "SELECT * FROM ARTICLE WHERE ARTICLE_ID > ? ORDER BY ARTICLE_ID ASC LIMIT 1";
    private static final String INSERT_SQL = "insert into ARTICLE (CONTENT, USER_ID, IMAGE_ID, LIKE_COUNT) values (?, ?, ?, ?)";
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

    public Optional<Article> findById(Long articleId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new ArticleRowMapper(), articleId);
    }

    public Optional<Article> findRecentArticle() {
        return jdbcTemplate.queryForObject(SELECT_RECENT_SQL, new ArticleRowMapper());
    }

    public void addLikeCount(Long articleId) {
        jdbcTemplate.update(UPDATE_LIKE_COUNT_SQL, articleId);
    }

    public Optional<Article> findPreviousArticle(Long articleId) {
        return jdbcTemplate.queryForObject(SELECT_PREV_ARTICLE_SQL, new ArticleRowMapper(), articleId);
    }

    public Optional<Article> findNextArticle(Long articleId) {
        return jdbcTemplate.queryForObject(SELECT_NEXT_ARTICLE_SQL, new ArticleRowMapper(), articleId);
    }

    static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs) throws SQLException {
            return new Article(
                    rs.getLong("ARTICLE_ID"),
                    rs.getString("CONTENT"),
                    rs.getString("USER_ID"),
                    rs.getString("IMAGE_ID"),
                    rs.getLong("LIKE_COUNT")
            );
        }
    }
}
