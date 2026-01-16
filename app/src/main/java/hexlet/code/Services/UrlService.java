package hexlet.code.Services;

import hexlet.code.Repository.UrlChecksRepository;
import hexlet.code.Repository.UrlsRepository;
import hexlet.code.models.Url;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import hexlet.code.models.UrlCheck;
import hexlet.code.utils.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlService {
    private final UrlsRepository urlsRepository;
    private final UrlChecksRepository urlChecksRepository;
    public UrlService(UrlsRepository urlsRepository, UrlChecksRepository urlChecksRepository) {
        this.urlsRepository = urlsRepository;
        this.urlChecksRepository = urlChecksRepository;
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

    public Result<UrlCheck> createUrlCheck(String htmlBody, Integer statusCode, Long urlId) {
        // ✅ Пустая htmlBody = нормальная ситуация (сервер недоступен)
        Document doc = Jsoup.parse(htmlBody != null ? htmlBody : "");

        String title = doc.title(); // ""
        String h1 = Optional.ofNullable(doc.selectFirst("h1"))
                .map(el -> el.text())
                .orElse(""); // ✅ Пустая строка вместо null!

        String description = Optional.ofNullable(doc.selectFirst("meta[name=description]"))
                .map(el -> el.attr("content"))
                .orElse("");

        UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
        urlChecksRepository.save(urlCheck);

        return Result.success(urlCheck, "Страница успешно проверена");
    }

    public Optional<List<Url>> getAllUrls() {
        var OptUrls = urlsRepository.getEntities();
        OptUrls.ifPresent(urls -> urls
                .forEach(url -> {
                    var urlId = url.getId();
                    var check = urlChecksRepository.findLastCheckByUrlId(urlId);
                    if (check.isPresent()) {
                        var lastCheck = check.get().getCreatedAt();
                        var statusCode = check.get().getStatusCode();
                        url.setLastCheck(lastCheck);
                        url.setStatusCode(statusCode);
                    }
                }));
        return OptUrls;
    }

    public Optional<Url> getUrlById(Long id) {
        return urlsRepository.findById(id);
    }

    public Optional<List<UrlCheck>> getUrlChecks(Long urlId) {
        return urlChecksRepository.findEntitiesByUrlId(urlId);
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
            return (uri.getPort() == -1) ? uri.getScheme() + "://" + uri.getHost() : uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
