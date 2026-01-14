package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String ddlSQLPath = "./src/main/resources/sql/schema.sql";
    private static final String dmlSQLPath = "./src/main/resources/sql/test_data.sql";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void init(){
        try {
            String ddlSql = Files.readString(Paths.get(ddlSQLPath));
            String dmlSql = Files.readString(Paths.get(dmlSQLPath));

            DataSource dataSource = new CustomDataSource();
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement()) {
                stmt.execute(ddlSql + dmlSql);
                logger.info("Database initialize Complete");
            } catch (SQLException e) {
                throw new RuntimeException("DB Initialize Error: ", e);
            }
        }catch (IOException e){
            throw new RuntimeException("DB Initialize File Not Found: ", e);
        }
    }
}
