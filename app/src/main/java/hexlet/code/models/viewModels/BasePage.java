package hexlet.code.models.viewModels;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class BasePage {
    private Map<String, String> flash;
}
