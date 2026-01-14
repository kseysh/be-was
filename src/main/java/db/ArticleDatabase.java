package db;

import model.Article;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ArticleDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into article (articleId, title, content, userId, imageId) values (?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "select from article where articleId = ?";

    public ArticleDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(Article article) {
        jdbcTemplate.update(INSERT_SQL, article);
    }

    public Optional<Article> findById(String articleId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new ArticleRowMapper(), articleId);
    }

    static class ArticleRowMapper implements RowMapper<Article>{
        @Override
        public Article mapRow(ResultSet rs) throws SQLException {
            return new Article(
                    rs.getString("articleId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("userId"),
                    rs.getString("imageId")
            );
        }
    }
}
