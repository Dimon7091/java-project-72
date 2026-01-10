package hexlet.code.models.viewModels;

import hexlet.code.models.Url;
import hexlet.code.models.dto.UrlDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class UrlPage extends BasePage{
    private Url url;

}
