package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        getApp();
    }

    public static Javalin getApp() {
        var codeResolver   = new ResourceCodeResolver("templates");
        var templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(templateEngine));
            config.staticFiles.add("static", Location.CLASSPATH);
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });
        var port = getPort();
        app.start(port);
        return app;
    }

    private static int getPort() {
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