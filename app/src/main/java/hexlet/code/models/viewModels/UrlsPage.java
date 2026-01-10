package hexlet.code.models.viewModels;

import hexlet.code.models.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter

public class UrlsPage extends BasePage {
    List<Url> urls;
}
