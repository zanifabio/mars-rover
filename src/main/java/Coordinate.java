import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class Coordinate {
    private final int x;
    private final int y;

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }
}
