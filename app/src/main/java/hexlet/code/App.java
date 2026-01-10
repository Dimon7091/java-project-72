package hexlet.code;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.Controllers.UrlController;
import hexlet.code.config.DataBaseConfig;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        DataBaseConfig.init();
        getApp();
    }

    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            config.staticFiles.add("static", Location.CLASSPATH);
        });


        app.get("/", ctx -> {
            ctx.render("index.jte");
        });
        app.post("/urls", UrlController::add);
        app.get("/urls", UrlController::index);
        app.get("/urls/{id}", UrlController::show);


        app.start(getPort());
        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
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