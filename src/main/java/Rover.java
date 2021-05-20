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
    public static Rover newInstance(Planet planet, Coordinate initialPosition, Direction initialFacingDirection) {
        if (planet.isOutOfBounds(initialPosition.getX(), initialPosition.getY())) {
            throw new IllegalArgumentException("It's a land rover. It must be placed on the planet.");
        }
        if (planet.getObstacles().contains(initialPosition)) {
            throw new IllegalArgumentException("Oops, you cannot place the rover on an obstacle.");
        }
        return new Rover(planet, initialPosition, initialFacingDirection);
    }

    public void move(Action action) {
        int step = 0;
        switch (action) {
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
        nextPosition(action, step, axis);
    }

    private void nextPosition(Action action, final int step, final Axis axis) {
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
            throw new ObstacleEncounteredException(this, nextPosition, action);
        } else {
            currentPosition = nextPosition;
        }
    }

    public void move(char[] sequence) {
        try {
            for (char command : sequence) {
                Action action = Action.get(String.valueOf(command).toUpperCase());
                move(action);
            }
        } catch (ObstacleEncounteredException e) {
            String message = String.format("The rover encountered an obstacle at %s." +
                    "\nIt stopped at %s, facing %s and cannot go %s. Command sequence aborted.",
                    e.getObstacle(), e.getRoverPosition(), e.getRoverFacingDirection(), e.getGivenCommand());
            throw new IllegalStateException(message, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("One or more commands were not valid. Command sequence aborted.");
        }
    }

    public void move(String sequence) {
        move(sequence.toCharArray());
    }

    //todo: make helper classes in different package and main class to make the application runnable
}

enum Axis {X, Y}