package hexlet.code.Controllers;

import hexlet.code.Repository.UrlsRepository;
import hexlet.code.models.Url;
import hexlet.code.models.viewModels.UrlPage;
import hexlet.code.models.viewModels.UrlsPage;
import io.javalin.http.Context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController extends BaseController{
    public static void add(Context ctx) {
        var getFormUrl = ctx.formParam("url");

        if (getFormUrl != null) {
            try {
                var uri = new URI(getFormUrl);
                String baseUrl = uri.getScheme() + "://" + uri.getHost();
                System.out.println("✅ Базовый URL: " + baseUrl);
                if (UrlsRepository.findByName(baseUrl).isPresent()) {
                    ctx.sessionAttribute("flash" , Map.of("status", "alert-info", "message", "Страница уже существуе"));
                    ctx.redirect("/urls");
                    return;
                }
                var url = new Url(baseUrl);
                UrlsRepository.save(url);
                ctx.sessionAttribute("flash" , Map.of("status", "alert-success", "message", "Страница успешно добавлена"));
                ctx.redirect("/urls");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void index(Context ctx) {
        Optional<List<Url>> OptUrls = UrlsRepository.getEntities();
        var page = new UrlsPage(null);
        if (OptUrls.isPresent()) {
            page = new UrlsPage(OptUrls.get());
        }
        setFlash(ctx, page);
        ctx.render("urls/indexUrls.jte", model("page", page));
    }

    public static void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        var OptUrl = UrlsRepository.findById(id);
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
