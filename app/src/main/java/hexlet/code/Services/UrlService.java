package hexlet.code.Services;

import hexlet.code.Repository.UrlsRepository;
import hexlet.code.models.Url;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import hexlet.code.utils.Result;

public class UrlService {
    private final UrlsRepository urlsRepository;

    public UrlService(UrlsRepository urlsRepository) {
        this.urlsRepository = urlsRepository;
    }

    public Result<Url> createUrl(String rawUrl) {
        // Валидация и нормализация URL
        String baseUrl = normalizeUrl(rawUrl);
        if (baseUrl == null) {
            return Result.failure("Не корректная ссылка");
        }

        // Проверка дубликата
        if (urlsRepository.findByName(baseUrl).isPresent()) {
            return Result.failure("Страница уже существует");
        }

        Url url = new Url(baseUrl);
        urlsRepository.save(url);
        return Result.success(url, "Страница успешно добавлена");
    }

    public Optional<List<Url>> getAllUrls() {
        return urlsRepository.getEntities();
    }

    public Optional<Url> getUrlById(Long id) {
        return urlsRepository.findById(id);
    }

    public Optional<Url> getUrlByName(String name) {
        return urlsRepository.findByName(name);
    }

    private String normalizeUrl(String rawUrl) {
        try {
            URI uri = new URI(rawUrl);
            if (uri.getScheme() == null || uri.getHost() == null) {
                return null;
            }
            return uri.getScheme() + "://" + uri.getHost();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
