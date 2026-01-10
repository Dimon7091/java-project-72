package hexlet.code.Controllers;

import hexlet.code.models.viewModels.BasePage;
import io.javalin.http.Context;

import java.util.Map;

public class BaseController {
    public static void setFlash(Context ctx, BasePage page) {
        Map<String, String> flash = ctx.consumeSessionAttribute("flash");
        if (flash != null) {
            page.setFlash(flash);
        }
    }
}
