import hexlet.code.Controllers.UrlController;
import hexlet.code.Repository.UrlChecksRepository;
import hexlet.code.Repository.UrlsRepository;
import hexlet.code.Services.UrlService;
import hexlet.code.models.Url;
import hexlet.code.utils.Result;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public final class UrlControllerTest {

    @Mock
    private UrlsRepository urlsRepository;

    @Mock
    private UrlChecksRepository urlChecksRepository;

    @Mock
    private Context ctx;

    @Mock
    private UrlService urlService;

    @Captor
    private ArgumentCaptor<Map<String, String>> flashCaptor;

    @Captor
    private ArgumentCaptor<String> redirectCaptor;

    private UrlController urlController;

    @BeforeEach
    void setUp() {
        // Создаем spy для urlService чтобы можно было мокать его методы
        urlController = new UrlController(urlsRepository, urlChecksRepository);
        // Используем reflection для замены service на mock
        try {
            var field = UrlController.class.getDeclaredField("urlService");
            field.setAccessible(true);
            field.set(urlController, urlService);
        } catch (Exception e) {
            fail("Failed to inject mock service");
        }
    }

    @ParameterizedTest
    @CsvSource({"https://google.com", "http://google.com", "https://google.com/adfb/",
                "https://docs.gradle.org", "https://docs.gradle.org/current/userguide/declaring_repositories.html",
                "https://docs.gradle.org/", "https://www.perplexity.ai/search/task-che"
    })
    void testAddUrlValidUrls(String testUrls) {
        // Подготовка (Arrange) - создаем "подделки"

        // Настройка мока контекста - говорим ему, что пользователь ввел URL
        when(ctx.formParam("url")).thenReturn(testUrls);

        // Настройка мока сервиса - говорим ему, что всё прошло успешно
        Url fakeUrl = new Url(testUrls); // Поддельный URL
        Result<Url> successResult = Result.success(fakeUrl, "Страница успешно добавлена");
        when(urlService.createUrl(testUrls)).thenReturn(successResult);

        // Действие (Act) - вызываем тестируемый метод
        urlController.add(ctx);

        // Проверка (Assert) - убеждаемся, что всё сработало правильно
        verify(ctx).redirect("/urls"); // Проверяем, что был редирект

        // Проверяем, что установили сообщение об успехе
        ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
        verify(ctx).sessionAttribute(eq("flash"), captor.capture());

        Map<String, String> flashMessage = captor.getValue();
        assertEquals("alert-success", flashMessage.get("status"));
    }

    @ParameterizedTest
    @CsvSource({"google.com", "www.google.com", "httpswww.perplexity.ai/search/task-che",
                "www.google", "https://google"

    })
    void testAddUrlInvalidUrls(String testUrls) {
        // Настройка мока контекста - говорим ему, что пользователь ввел URL
        when(ctx.formParam("url")).thenReturn(testUrls);

        // Настройка мока сервиса - говорим ему, что всё прошло успешно
        Result<Url> successResult = Result.failure("Не корректная ссылка");
        when(urlService.createUrl(testUrls)).thenReturn(successResult);

        // Действие (Act) - вызываем тестируемый метод
        urlController.add(ctx);

        // 2. Проверяем, что установлено флеш-сообщение об ошибке
        ArgumentCaptor<Map> flashCaptor = ArgumentCaptor.forClass(Map.class);
        verify(ctx).sessionAttribute(eq("flash"), flashCaptor.capture());

        // 2. Проверяем, что установлено флеш-сообщение об ошибке
        Map<String, String> flashMessage = flashCaptor.getValue();
        assertEquals("alert-danger", flashMessage.get("status"));
        assertEquals("Не корректная ссылка", flashMessage.get("message"));

        verify(urlService).createUrl(testUrls);
    }
}
