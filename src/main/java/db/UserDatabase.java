package db;

import model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into users (userId, password, name, email, imageId) values (?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "select from users where userId = ?";

    public UserDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(User user) {
        jdbcTemplate.update(INSERT_SQL, user);
    }

    public Optional<User> findById(String userId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), userId);
    }

    static class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs) throws SQLException {
            return new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("imageId")
            );
        }
    }
}
