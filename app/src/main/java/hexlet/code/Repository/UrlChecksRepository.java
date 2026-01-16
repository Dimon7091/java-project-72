package hexlet.code.Repository;


import hexlet.code.models.UrlCheck;
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
public class UrlChecksRepository {
    public void save(UrlCheck urlCheck) {
        var sql = """
        INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at) 
        VALUES (?, ?, ?, ?, ?, NOW())
        """;
            try (var connection = dataSource.getConnection();
                 var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setLong(1, urlCheck.getUrlId());
                preparedStatement.setInt(2, urlCheck.getStatusCode());
                preparedStatement.setString(3, urlCheck.getH1());
                preparedStatement.setString(4, urlCheck.getTitle());
                preparedStatement.setString(5, urlCheck.getDescription());
                preparedStatement.executeUpdate();

                var generatedKey = preparedStatement.getGeneratedKeys();
                if (generatedKey.next()) {
                    urlCheck.setId(generatedKey.getLong(1));
                } else {

                }
            } catch (SQLException e) {
                log.error("ОШИБКА SAVE UrlCheck: {}", e.getMessage());
                throw new RuntimeException("Не удалось сохранить UrlCheck", e);
            }
    }

    public Optional<UrlCheck> findById(Long id) {
        var sql = "SELECT * FROM url_checks WHERE id = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            var result = preparedStatement.executeQuery();
            if (result.next())  {
                var checkId = result.getLong("id");
                var urlId = result.getLong("url_id");
                var statusCode = result.getInt("status_code");
                var h1 = result.getString("h1");
                var title = result.getString("title");
                var description = result.getString("description");
                LocalDateTime createdAt = result.getObject("created_at", LocalDateTime.class);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = createdAt.format(formatter);

                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
                urlCheck.setId(checkId);
                urlCheck.setCreatedAt(formattedDate);

                return Optional.of(urlCheck);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<UrlCheck> findLastCheckByUrlId(Long urlId) {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, urlId);

            var result = preparedStatement.executeQuery();
            if (result.next())  {
                var checkId = result.getLong("id");
                var url_Id = result.getLong("url_id");
                var statusCode = result.getInt("status_code");
                var h1 = result.getString("h1");
                var title = result.getString("title");
                var description = result.getString("description");
                LocalDateTime createdAt = result.getObject("created_at", LocalDateTime.class);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = createdAt.format(formatter);

                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, url_Id);
                urlCheck.setId(checkId);
                urlCheck.setCreatedAt(formattedDate);


                return Optional.of(urlCheck);
            }
        } catch (SQLException e) {

        }

        return Optional.empty();
    }

    public Optional<List<UrlCheck>> findEntitiesByUrlId(Long urlId) {
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, urlId);

            var result = preparedStatement.executeQuery();
            List<UrlCheck> checksList = new LinkedList<>();
            while (result.next()) {
                var id = result.getLong("id");
                var url_id = result.getLong("url_id");
                var statusCode = result.getInt("status_code");
                var h1 = result.getString("h1");
                var title = result.getString("title");
                var description = result.getString("description");
                LocalDateTime createdAt = result.getObject("created_at", LocalDateTime.class);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = createdAt.format(formatter);

                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, url_id);
                urlCheck.setId(id);
                urlCheck.setCreatedAt(formattedDate);
                checksList.add(urlCheck);
            }
            if (checksList.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(checksList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
