import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Rover {

    private final Planet planet;
    private Coordinate currentPosition;
    private Direction facingDirection;

    // create instance and check it's not on obstacle
    public Rover newInstance(Planet planet, Coordinate initialPosition, Direction initialFacingDirection) {
        if (planet.isOutOfBounds(initialPosition.getX(), initialPosition.getY())) {
            throw new IllegalArgumentException("It's a land rover. It must be placed on the planet.");
        }
        if (planet.getObstacles().contains(initialPosition)) {
            throw new IllegalArgumentException("Oops, you cannot place the rover on an obstacle.");
        }
        return new Rover(planet, initialPosition, initialFacingDirection);
    }

    public void move(Movement movement) {
        int step = 0;
        switch (movement) {
            case LEFT:
                facingDirection = Direction.left(facingDirection);
                return;
            case RIGHT:
                facingDirection = Direction.right(facingDirection);
                return;
            case FORWARD:
                step = 1;
                break;
            case BACKWARDS:
                step = -1;
        }
        Axis axis = null;
        switch (facingDirection) {
            case NORTH:
                step *= -1;
                axis = Axis.Y;
                break;
            case EAST:
                axis = Axis.X;
                break;
            case SOUTH:
                axis = Axis.Y;
                break;
            case WEST:
                step *= -1;
                axis = Axis.X;
        }
        nextPosition(step, axis);
    }

    private void nextPosition(final int step, final Axis axis) {
        Coordinate nextPosition;
        if (axis.equals(Axis.X)) {
            if (currentPosition.getX() + step >= planet.getWidth()) {
                nextPosition = Coordinate.of(0 , currentPosition.getY());
            } else if (currentPosition.getX() + step < 0) {
                nextPosition = Coordinate.of(getPlanet().getWidth() - 1, currentPosition.getY());
            } else {
                nextPosition = Coordinate.of(currentPosition.getX() + step, currentPosition.getY());
            }
        } else {
            // TODO: implement more realistic geographical movement on sphere (cannot go north to south)
            if (currentPosition.getY() + step >= planet.getHeight()) {
                nextPosition = Coordinate.of(currentPosition.getX(), 0);
            } else if (currentPosition.getY() + step < 0) {
                nextPosition = Coordinate.of(currentPosition.getX(), getPlanet().getHeight() - 1);
            } else {
                nextPosition = Coordinate.of(currentPosition.getX(), currentPosition.getY() + step);
            }
        }
        if (planet.getObstacles().contains(nextPosition)) {
            throw new IllegalStateException("The rover encountered an obstacle, the command sequence was aborted");
        } else {
            currentPosition = nextPosition;
        }
    }


    // move sequence method

    //todo: make helper classes in different package and main class to make the application work
    //todo: write unit tests
}

enum Axis {X, Y}