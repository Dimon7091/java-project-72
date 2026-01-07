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
    private static final Logger log = LoggerFactory.getLogger(DataBaseConfig.class);

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
        var url = System.getenv("JDBC_DATABASE_URL");
        // Подключение локальной базы данных если ссылка не найдена
        if (url != null) {
            log.info("✅ Database env var found");
            return "jdbc:" + url;
        }
        log.info("❎ Database env var not found!");
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

    public static int getPort() {
        // 1. JVM property (из Dockerfile)
        String propPort = System.getProperty("server.port");
        if (propPort != null) return Integer.parseInt(propPort);

        // 2. ENV PORT
        String envPort = System.getenv("PORT");
        if (envPort != null) return Integer.parseInt(envPort);

        // 3. Дефолт
        return 8080;
    }
}
