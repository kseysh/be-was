package db;

import db.config.CustomJdbcTemplate;
import db.config.RowMapper;
import enums.ContentTypes;
import exception.NotFoundException;
import model.Image;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ImageDatabase {

    private final CustomJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "insert into IMAGE (IMAGE_ID, BYTES, FILE_NAME, CONTENT_TYPE) values (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE IMAGE SET BYTES = ?, FILE_NAME = ?, CONTENT_TYPE = ? WHERE IMAGE_ID = ?";
    private static final String SELECT_SQL = "select * from IMAGE where IMAGE_ID = ?";

    public ImageDatabase(DataSource dataSource) {
        jdbcTemplate = new CustomJdbcTemplate(dataSource);
    }

    public void save(Image image) {
        jdbcTemplate.update(INSERT_SQL, image.imageId(), image.bytes(), image.fileName(), image.contentType().getExtension());
    }

    public void update(Image image){
        jdbcTemplate.update(UPDATE_SQL, image.bytes(), image.fileName(), image.contentType().getExtension(), image.imageId());
    }

    public Optional<Image> findById(String imageId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new ImageRowMapper(), imageId);
    }

    public Image findByIdOrThrow(String imageId) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new ImageRowMapper(), imageId)
                .orElseThrow(() -> new NotFoundException(imageId + " image not found"));
    }

    static class ImageRowMapper implements RowMapper<Image> {
        @Override
        public Image mapRow(ResultSet rs) throws SQLException {
            return new Image(
                    rs.getString("IMAGE_ID"),
                    rs.getBytes("BYTES"),
                    rs.getString("FILE_NAME"),
                    ContentTypes.fromExtension(rs.getString("CONTENT_TYPE"))
            );
        }
    }
}
