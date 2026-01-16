package hexlet.code.Repository;

import hexlet.code.models.Url;
import lombok.extern.slf4j.Slf4j;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static hexlet.code.Repository.BaseRepository.dataSource;

@Slf4j
public final class UrlsRepository {
    public void save(Url url) {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, NOW())";
        if (url.getId() == null) {
            try (var connection = dataSource.getConnection();
                 var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, url.getName());
                preparedStatement.executeUpdate();

                var generatedKey = preparedStatement.getGeneratedKeys();
                if (generatedKey.next()) {
                    url.setId(generatedKey.getLong(1));
                    log.info("Созданна запись Url, id = {}:", generatedKey);
                } else {
                    log.error("Не удалось создать запись Url");
                }
            } catch (SQLException e) {
                log.error("Ошибка при создании записи UrlCheck");
            }
        }
    }

    public Optional<Url> findById(Long urlId) {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, urlId);

            var result = preparedStatement.executeQuery();
            if (result.next()) {
                var id = result.getLong("id");
                var name = result.getString("name");
                LocalDateTime createdAt = result.getObject("created_at", LocalDateTime.class);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = createdAt.format(formatter);

                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(formattedDate);

                return Optional.of(url);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Url> findByName(String urlName) {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, urlName);
            var result = preparedStatement.executeQuery();
            if (result.next()) {
                var id = result.getLong(1);
                var name = result.getString("name");
                LocalDateTime createdAt = result.getObject("created_at", LocalDateTime.class);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = createdAt.format(formatter);

                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(formattedDate);

                return Optional.of(url);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    public Optional<List<Url>> getEntities() {
        var sql = "SELECT * FROM urls";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var result = preparedStatement.executeQuery()) {

            List<Url> urlList = new LinkedList<>();
            while (result.next()) {
                var id = result.getLong("id");
                var name = result.getString("name");
                LocalDateTime createdAt = result.getObject("created_at", LocalDateTime.class);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = createdAt.format(formatter);

                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(formattedDate);
                urlList.add(url);
            }
            if (urlList.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(urlList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
