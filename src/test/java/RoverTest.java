import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoverTest {
    private Planet planet;

    @Test
    public void testNewInstance() {
        Planet planet = Planet.newInstance(20, 15);
        planet.placeObstacle(10, 10);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Rover.newInstance(planet, Coordinate.of(20, 20), Direction.NORTH));
        assert exception.getMessage().contains("must be placed on the planet");
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Rover.newInstance(planet, Coordinate.of(10, 10), Direction.NORTH));
        assert exception.getMessage().contains("rover on an obstacle");
        Rover rover = Rover.newInstance(planet, Coordinate.of(15, 10), Direction.NORTH);
        assert rover.getPlanet().equals(planet);
        assert rover.getCurrentPosition().equals(Coordinate.of(15, 10));
        assert rover.getFacingDirection().equals(Direction.NORTH);
    }

    @BeforeEach
    public void initializePlanet() {
        planet = Planet.newInstance(20, 15);
    }

    @Test
    public void testTurnLeftRight() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.RIGHT);
        assert rover.getFacingDirection().equals(Direction.EAST);
        rover.move(Action.RIGHT);
        assert rover.getFacingDirection().equals(Direction.SOUTH);
        rover.move(Action.RIGHT);
        assert rover.getFacingDirection().equals(Direction.WEST);
        rover.move(Action.RIGHT);
        assert rover.getFacingDirection().equals(Direction.NORTH);
        rover.move(Action.LEFT);
        assert rover.getFacingDirection().equals(Direction.WEST);
        rover.move(Action.LEFT);
        assert rover.getFacingDirection().equals(Direction.SOUTH);
        rover.move(Action.LEFT);
        assert rover.getFacingDirection().equals(Direction.EAST);
        rover.move(Action.LEFT);
        assert rover.getFacingDirection().equals(Direction.NORTH);
    }

    @Test
    public void testMoveForwardBackwardsNormalCase() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.FORWARD);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 9));
        rover.move(Action.BACKWARDS);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 10));
        rover.move(Action.RIGHT);
        assert rover.getFacingDirection().equals(Direction.EAST);
        rover.move(Action.FORWARD);
        assert rover.getCurrentPosition().equals(Coordinate.of(11, 10));
        rover.move(Action.BACKWARDS);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 10));
    }

    @Test
    public void testMoveOnEdges() {
        // Edge on X axis
        Coordinate initialPosition = Coordinate.of(19, 10);
        Direction initialDirection = Direction.EAST;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.FORWARD);
        assert rover.getCurrentPosition().equals(Coordinate.of(0, 10));
        rover.move(Action.BACKWARDS);
        assert rover.getCurrentPosition().equals(Coordinate.of(19, 10));
        rover.move(Action.LEFT);
        rover.move(Action.LEFT);
        assert rover.getFacingDirection().equals(Direction.WEST);
        assert rover.getCurrentPosition().equals(Coordinate.of(19, 10));
        rover.move(Action.BACKWARDS);
        assert rover.getCurrentPosition().equals(Coordinate.of(0, 10));
        rover.move(Action.FORWARD);
        assert rover.getCurrentPosition().equals(Coordinate.of(19, 10));

        // Edge on Y axis
        initialPosition = Coordinate.of(10, 0);
        initialDirection = Direction.NORTH;
        rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.FORWARD);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 14));
        rover.move(Action.BACKWARDS);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 0));
        rover.move(Action.LEFT);
        rover.move(Action.LEFT);
        assert rover.getFacingDirection().equals(Direction.SOUTH);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 0));
        rover.move(Action.BACKWARDS);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 14));
        rover.move(Action.FORWARD);
        assert rover.getCurrentPosition().equals(Coordinate.of(10, 0));
    }

    @Test
    public void testObstacleEncountered() {
        Coordinate obstacle1 = Coordinate.of(11, 10);
        Coordinate obstacle2 = Coordinate.of(10, 11);
        planet.placeObstacles(Set.of(obstacle1, obstacle2));

        //todo: improve test
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.SOUTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        Exception exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.FORWARD));
//        assert exception.getMessage().equals("The rover encountered an obstacle");
        rover.move(Action.LEFT);
        exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.FORWARD));
//        assert exception.getMessage().equals("The rover encountered an obstacle");
        rover.move(Action.LEFT);
        exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.BACKWARDS));
//        assert exception.getMessage().equals("The rover encountered an obstacle");
        rover.move(Action.LEFT);
        exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.BACKWARDS));
//        assert exception.getMessage().equals("The rover encountered an obstacle");
    }

    @Test
    public void testCommandSequenceCharArray() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);

        char[] sequence = {'l', 'F', 'f', 'R', 'f', 'r', 'b', 'B'};
        rover.move(sequence);
        assert rover.getCurrentPosition().equals(Coordinate.of(6, 9));
        assert rover.getFacingDirection().equals(Direction.EAST);
    }

    @Test
    void testCommandSequenceString() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);

        String sequence = "LFFRFRBB";
        rover.move(sequence);
        assert rover.getCurrentPosition().equals(Coordinate.of(6, 9));
        assert rover.getFacingDirection().equals(Direction.EAST);
    }

    @Test
    public void testCommandSequenceInvalid() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);

        String sequence = "LIFER";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> rover.move(sequence));
        assert exception.getMessage().contains("commands were not valid");
    }

    @Test
    public void testCommandSequenceObstacle() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        planet.placeObstacle(8, 9);
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        String sequence = "LFFRFRBB";
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> rover.move(sequence));
        assert rover.getCurrentPosition().equals(Coordinate.of(8, 10));
        assert rover.getFacingDirection().equals(Direction.NORTH);
        System.err.println(exception.getMessage());
    }
}
