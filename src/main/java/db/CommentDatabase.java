package db;

import db.config.CustomJdbcTemplate;
import db.config.RowMapper;
import model.Comment;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into COMMENT (CONTENT, USER_ID, ARTICLE_ID) values (?, ?, ?)";
    private static final String SELECT_SQL_BY_ARTICLE_ID = "select * from COMMENT where ARTICLE_ID = ?";

    public CommentDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(Comment comment) {
        jdbcTemplate.update(INSERT_SQL,
                comment.getContent(),
                comment.getUserId(),
                comment.getArticleId()
        );
    }

    public List<Comment> findAllByArticleId(Long articleId) {
        return jdbcTemplate.query(SELECT_SQL_BY_ARTICLE_ID, new CommentRowMapper(), articleId);
    }

    static class CommentRowMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs) throws SQLException {
            return new Comment(
                    rs.getLong("COMMENT_ID"),
                    rs.getString("CONTENT"),
                    rs.getString("USER_ID"),
                    rs.getLong("ARTICLE_ID")
            );
        }
    }
}
