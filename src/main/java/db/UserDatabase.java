package db;

import model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into USERS (USER_ID, PASSWORD, NAME, EMAIL, IMAGE_ID) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update USERS set PASSWORD = ?, NAME = ?, EMAIL = ?, IMAGE_ID = ? where USER_ID = ?";
    private static final String SELECT_SQL = "select * from USERS u where u.USER_ID = ?";
    private static final String SELECT_SQL_BY_NAME = "select * from USERS where NAME = ?";

    public UserDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(User user) {
        jdbcTemplate.update(
                INSERT_SQL,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getProfileImageId()
        );
    }

    public void update(User user) {
        jdbcTemplate.update(
                UPDATE_SQL,
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getProfileImageId(),
                user.getUserId()
        );
    }

    public Optional<User> findById(String userId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), userId);
    }

    public Optional<User> findByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_SQL_BY_NAME, new UserRowMapper(), name);
    }

    static class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs) throws SQLException {
            return new User(
                    rs.getString("USER_ID"),
                    rs.getString("PASSWORD"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("IMAGE_ID")
            );
        }
    }
}
