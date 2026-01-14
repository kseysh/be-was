package db;

import enums.ContentTypes;
import model.Image;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ImageDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into image (imageId, bytes, fileName, contentType) values (?, ?, ?, ?)";
    private static final String SELECT_SQL = "select from image where imageId = ?";

    public ImageDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(Image article) {
        jdbcTemplate.update(INSERT_SQL, article);
    }

    public Optional<Image> findById(String articleId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new ImageRowMapper(), articleId);
    }

    static class ImageRowMapper implements RowMapper<Image>{
        @Override
        public Image mapRow(ResultSet rs) throws SQLException {
            return new Image(
                    rs.getString("imageId"),
                    rs.getBytes("bytes"),
                    rs.getString("fileName"),
                    ContentTypes.fromExtension(rs.getString("contentType"))
            );
        }
    }
}
