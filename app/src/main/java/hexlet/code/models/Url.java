package hexlet.code.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter

public class Url {
    private long id;
    private String name;
    private LocalDateTime createdAt;
}
