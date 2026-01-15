package db;

import model.Comment;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static model.Comment.newComment;

public class CommentDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into COMMENT (CONTENT, USER_ID, ARTICLE_ID) values (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update COMMENT set CONTENT = ?, USER_ID = ?, ARTICLE_ID = ? where COMMENT_ID = ?";
    private static final String SELECT_SQL = "select * from COMMENT where COMMENT_ID = ?";
    private static final String SELECT_SQL_BY_USER_ID = "select * from COMMENT where USER_ID = ?";

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

    public void update(Comment comment) {
        jdbcTemplate.update(UPDATE_SQL,
                comment.getContent(),
                comment.getUserId(),
                comment.getArticleId(),
                comment.getCommentId()
        );
    }

    public Optional<Comment> findById(String commentId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new CommentRowMapper(), commentId);
    }

    public List<Comment> findAllByUserId(String userId) {
        return jdbcTemplate.query(SELECT_SQL_BY_USER_ID, new CommentRowMapper(), userId);
    }

    static class CommentRowMapper implements RowMapper<Comment>{
        @Override
        public Comment mapRow(ResultSet rs) throws SQLException {
            return newComment(
                    rs.getString("CONTENT"),
                    rs.getString("USER_ID"),
                    rs.getString("ARTICLE_ID")
            );
        }
    }
}
