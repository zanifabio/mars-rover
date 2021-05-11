import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rover {
    private Planet planet;
    private Point currentPosition;
    private Direction facingDirection;

    // create instance and check it's not on obstacle
    // move method
    // move sequence method

    //todo: make helper classes in different package and main class to make the application work
    //todo: write unit tests
}
