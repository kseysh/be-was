package db.config;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomJdbcTemplate {
    private final DataSource dataSource;

    public CustomJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        List<T> results = query(sql, rowMapper, args);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            setParameters(pstmt, args);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB Query Error: " + sql, e);
        }
    }

    public int update(String sql, Object... args) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            setParameters(pstmt, args);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB Update Error: " + sql, e);
        }
    }

    private void setParameters(PreparedStatement pstmt, Object[] args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
    }
}
