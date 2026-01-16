package hexlet.code;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.Controllers.UrlController;
import hexlet.code.Repository.UrlChecksRepository;
import hexlet.code.Repository.UrlsRepository;
import hexlet.code.config.DataBaseConfig;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Random;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static volatile Javalin appInstance = null;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        getApp();
    }

    public static Javalin getApp() {
        // ✅ КЛЮЧЕВОЕ - детект тестов по имени потока
        if (isRunningInTest()) {
            return createConfiguredApp(false); // НЕ запускать сервер
        }

        // Production - singleton
        if (appInstance == null) {
            synchronized (lock) {
                if (appInstance == null) {
                    appInstance = createConfiguredApp(true); // Запускать сервер
                }
            }
        }
        return appInstance;
    }
    private static boolean isRunningInTest() {
        String threadName = Thread.currentThread().getName();
        return threadName.contains("Test") ||
                System.getProperty("test") != null ||
                "true".equalsIgnoreCase(System.getenv("TEST"));
    }

    public static Javalin createConfiguredApp(boolean startServer) {
        DataBaseConfig.init();

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            // ✅ Безопасные static файлы
            try {
                config.staticFiles.add("static", Location.CLASSPATH);
                log.info("Static files enabled");
            } catch (Exception e) {
                log.debug("Static files directory not found - skipped");
            }
        });


        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        UrlsRepository urlsRepository = new UrlsRepository();
        UrlChecksRepository urlChecksRepository = new UrlChecksRepository();
        UrlController urlController = new UrlController(urlsRepository, urlChecksRepository);

        app.post("/urls", urlController::add);
        app.get("/urls", urlController::index);
        app.get("/urls/{id}", urlController::show);
        app.post("/urls/{id}/checks", urlController::check);

        if (startServer) {
            try {
                app.start(getPort());
                log.info("🚀 Javalin started on port {}", getPort());
            } catch (Exception e) {
                log.debug("Javalin already running - skipping start");
            }
        }

        return app;
    }

    public static TemplateEngine createTemplateEngine() {
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
        return 8000 + new Random().nextInt(1000);
    }
}