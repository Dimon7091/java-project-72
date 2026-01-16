package hexlet.code.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

public class Url {
    private Long id;
    private String name;
    private String createdAt;
    private String lastCheck;
    private Integer statusCode;

    public Url(String name) {
        this.name = name;
    }
}
