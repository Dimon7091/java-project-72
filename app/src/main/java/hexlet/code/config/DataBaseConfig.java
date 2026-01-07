package hexlet.code.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.App;
import hexlet.code.Repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataBaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DataBaseConfig.class.getName());

    public static void init() {
        var hikariConfig = new HikariConfig();


        hikariConfig.setJdbcUrl(getUrl());
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(10000);

        var dataSource = new HikariDataSource(hikariConfig);

        try (var connection = dataSource.getConnection()) {
            // 1. Простая проверка соединения
            if (connection.isValid(3)) { // 3 сек таймаут
                log.info("✅ Connection is VALID");
            }

            // 2. Проверка метаданных БД
            var meta = connection.getMetaData();
            log.info("✅ Connected to: {} v{} (driver: {})",
                    meta.getDatabaseProductName(),
                    meta.getDatabaseProductVersion(),
                    meta.getDriverName());

            // 3. Выполнение схемы
            var sqlShema = parseSchema();
            try (var statement = connection.createStatement()) {
                statement.execute(sqlShema);
                log.info("✅ Schema applied successfully");
            }

        } catch (SQLException e) {
            log.error("❌ Database connection FAILED: {}", e.getMessage());
            throw new RuntimeException("Cannot connect to database", e);
        }

        BaseRepository.dataSource = dataSource;
        log.info("✅ HikariCP pool initialized");
    }

    static String getUrl() {
        var url = System.getenv("JDBC_DATABASE_URL"); // Render DATABASE_URL
        if (url != null && url.contains("postgresql")) {
            // Парсим Render URL: postgresql://user:pass@host/db
            String cleanUrl = url.replace("postgresql://", "");
            String[] parts = cleanUrl.split("@");
            String credentials = parts[0];  // user:pass
            String hostDb = parts[1];       // host/db

            String[] credParts = credentials.split(":", 2);
            String user = credParts[0];
            String password = credParts[1];

            String[] hostDbParts = hostDb.split("/", 2);
            String host = hostDbParts[0];
            String database = hostDbParts[1];

            // Формируем JDBC URL
            return String.format("jdbc:postgresql://%s:5432/%s?user=%s&password=%s",
                    host, database, user, password);
        }
        return "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
    }

    static String parseSchema() {
        var urlStream = App.class.getClassLoader().getResourceAsStream("db/schema.sql");
        if (urlStream == null) {
            throw new RuntimeException("Resource schema.sql not found");
        }
        return new BufferedReader(new InputStreamReader(urlStream))
                .lines().collect(Collectors.joining("\n"));
    }
}
