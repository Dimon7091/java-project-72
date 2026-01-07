package hexlet.code;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
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

        app.start(DataBaseConfig.getPort());
        return app;
    }
}