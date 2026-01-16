package hexlet.code.Controllers;

import hexlet.code.Repository.UrlChecksRepository;
import hexlet.code.Repository.UrlsRepository;
import hexlet.code.Services.UrlService;
import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.models.viewModels.UrlPage;
import hexlet.code.models.viewModels.UrlsPage;
import hexlet.code.utils.Result;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public final class UrlController extends BaseController {
    private final UrlService urlService;

    public UrlController(UrlsRepository urlsRepository, UrlChecksRepository urlChecksRepository) {
        this.urlService = new UrlService(urlsRepository, urlChecksRepository);
    }

    public void add(Context ctx) {
        String rawUrl = ctx.formParam("url");

        if (rawUrl == null || rawUrl.trim().isEmpty()) {
            ctx.sessionAttribute("flash", Map.of("status", "alert-warning", "message", "Ссылка обязательна"));
            ctx.redirect("/urls");
            return;
        }

        Result<Url> result = urlService.createUrl(rawUrl);
        Map<String, String> message = (result.isSuccess()
                ? Map.of("status", "alert-success", "message", result.getMessage())
                : Map.of("status", "alert-danger", "message", result.getMessage()));
        ctx.sessionAttribute("flash", message);
        ctx.redirect("/urls");
    }

    public void index(Context ctx) {
        Optional<List<Url>> optUrls = urlService.getAllUrls();
        var page = new UrlsPage(null);
        if (optUrls.isPresent()) {
            page = new UrlsPage(optUrls.get());
        }
        setFlash(ctx, page);
        ctx.render("urls/indexUrls.jte", model("page", page));
    }

    public void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        var optUrl = urlService.getUrlById(id);
        var optUrlChecks = urlService.getUrlChecks(id);
        if (optUrl.isPresent()) {
            var page = new UrlPage(optUrl.get(), optUrlChecks);
            setFlash(ctx, page);
            ctx.render("urls/showUrl.jte", model("page", page));
            return;
        }
        ctx.sessionAttribute("flash", Map.of(
                "status", "alert-danger",
                "message", "Страница не найдена, ошибка на сервере или неполадки с интернетом"
        ));

    }

    public void check(Context ctx) {
        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        var optUrl = urlService.getUrlById(urlId);
        String url = "";

        if (optUrl.isPresent()) {
            url = optUrl.get().getName();
        }  else {
            Map<String, String> error = Map.of("status", "alert-danger", "message", "Ошибка при проверке url");
            ctx.sessionAttribute("flash", error);
            ctx.redirect("/urls/" + urlId);
            return;
        }

        String htmlBody = "";
        int statusCode = 0;

        if (url == null || url.trim().isEmpty()) {
            Map<String, String> error = Map.of("status", "alert-danger", "message", "URL не может быть пустым");
            ctx.sessionAttribute("flash", error);
            ctx.redirect("/urls/" + urlId);
            return;
        }

        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            statusCode = response.getStatus();
            htmlBody = response.getBody(); // Может быть ""
        } catch (UnirestException e) {
            log.warn("Не удалось достучаться до {}: {}", url, e.getMessage());
            statusCode = 0;
            htmlBody = ""; // ✅ Явно пустая строка!
        }

        Result<UrlCheck> result = urlService.createUrlCheck(htmlBody, statusCode, urlId);
        Map<String, String> message = result.isSuccess()
                ? Map.of("status", "alert-success", "message", result.getMessage())
                : Map.of("status", "alert-danger", "message", result.getMessage());
        ctx.sessionAttribute("flash", message);
        ctx.redirect("/urls/" + urlId);
    }
}
