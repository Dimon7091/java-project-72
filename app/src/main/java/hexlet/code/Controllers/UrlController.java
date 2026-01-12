package hexlet.code.Controllers;

import hexlet.code.Repository.UrlsRepository;
import hexlet.code.Services.UrlService;
import hexlet.code.models.Url;
import hexlet.code.models.viewModels.UrlPage;
import hexlet.code.models.viewModels.UrlsPage;
import hexlet.code.utils.Result;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static hexlet.code.utils.UrlValidator.validateUrl;
import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController extends BaseController{
    private final UrlService urlService;

    public UrlController(UrlsRepository urlsRepository) {
        this.urlService = new UrlService(urlsRepository);
    }

    public void add(Context ctx) {
        String rawUrl = ctx.formParam("url");

        if (rawUrl == null || rawUrl.trim().isEmpty()) {
            ctx.sessionAttribute("flash", Map.of("status", "alert-warning", "message", "Ссылка обязательна"));
            ctx.redirect("/urls");
            return;
        }

        Result<Url> result = urlService.createUrl(rawUrl);
        Map<String, String> message = (result.isSuccess() ?
                Map.of("status", "alert-success", "message", result.getMessage()) :
                Map.of("status", "alert-danger", "message", result.getMessage()));
        ctx.sessionAttribute("flash", message);
        ctx.redirect("/urls");
    }

    public void index(Context ctx) {
        Optional<List<Url>> OptUrls = urlService.getAllUrls();
        var page = new UrlsPage(null);
        if (OptUrls.isPresent()) {
            page = new UrlsPage(OptUrls.get());
        }
        setFlash(ctx, page);
        ctx.render("urls/indexUrls.jte", model("page", page));
    }

    public void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        var OptUrl = urlService.getUrlById(id);
        if (OptUrl.isPresent()) {
            var page = new UrlPage(OptUrl.get());
            ctx.render("urls/showUrl.jte", model("page", page));
            return;
        }
        ctx.sessionAttribute("flash" , Map.of("status", "alert-danger",
                "message", "Страница не найдена, ошибка на сервере или неполадки с интернетом"));
        ctx.redirect("/urls");
    }
}
