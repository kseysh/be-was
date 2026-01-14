package db;

import model.Comment;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CommentDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into COMMENT (COMMENT_ID, CONTENT, USER_ID, ARTICLE_ID) values (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update COMMENT set CONTENT = ?, USER_ID = ?, ARTICLE_ID = ? where COMMENT_ID = ?";
    private static final String SELECT_SQL = "select * from COMMENT where COMMENT_ID = ?";

    public CommentDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(Comment comment) {
        jdbcTemplate.update(INSERT_SQL,
                comment.commentId(),
                comment.content(),
                comment.userId(),
                comment.articleId()
        );
    }

    public void update(Comment comment) {
        jdbcTemplate.update(UPDATE_SQL,
                comment.content(),
                comment.userId(),
                comment.articleId(),
                comment.commentId()
        );
    }

    public Optional<Comment> findById(String commentId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new CommentRowMapper(), commentId);
    }

    static class CommentRowMapper implements RowMapper<Comment>{
        @Override
        public Comment mapRow(ResultSet rs) throws SQLException {
            return new Comment(
                    rs.getString("COMMENT_ID"),
                    rs.getString("CONTENT"),
                    rs.getString("USER_ID"),
                    rs.getString("ARTICLE_ID")
            );
        }
    }
}
