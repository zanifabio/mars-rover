import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point extends Coordinate {

    private boolean hasObstacle;

    public Point(int x, int y) {
        super(x, y);
        this.hasObstacle = false;
    }
}
