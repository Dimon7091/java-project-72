package hexlet.code.models;

import lombok.Getter;
import lombok.Setter;

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
