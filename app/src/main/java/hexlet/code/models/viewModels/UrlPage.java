package hexlet.code.models.viewModels;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UrlPage extends BasePage {
    private Url url;
    private Optional<List<UrlCheck>> urlChecks;
}
