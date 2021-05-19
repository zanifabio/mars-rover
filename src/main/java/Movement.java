import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Movement {
    FORWARD("F"),
    BACKWARDS("B"),
    LEFT("L"),
    RIGHT("R");

    private final String shortName;
}
