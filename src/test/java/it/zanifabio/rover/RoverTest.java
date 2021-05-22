package it.zanifabio.rover;

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
        Assertions.assertEquals(rover.getPlanet(), planet);
        Assertions.assertEquals(rover.getCurrentPosition(), (Coordinate.of(15, 10)));
        Assertions.assertEquals(rover.getFacingDirection(), (Direction.NORTH));
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
        Assertions.assertEquals(rover.getFacingDirection(), Direction.EAST);
        rover.move(Action.RIGHT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.SOUTH);
        rover.move(Action.RIGHT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.WEST);
        rover.move(Action.RIGHT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.NORTH);
        rover.move(Action.LEFT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.WEST);
        rover.move(Action.LEFT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.SOUTH);
        rover.move(Action.LEFT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.EAST);
        rover.move(Action.LEFT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testMoveForwardBackwardsNormalCase() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.FORWARD);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 9));
        rover.move(Action.BACKWARDS);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 10));
        rover.move(Action.RIGHT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.EAST);
        rover.move(Action.FORWARD);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(11, 10));
        rover.move(Action.BACKWARDS);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 10));
    }

    @Test
    public void testMoveOnEdges() {
        // Edge on X axis
        Coordinate initialPosition = Coordinate.of(19, 10);
        Direction initialDirection = Direction.EAST;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.FORWARD);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(0, 10));
        rover.move(Action.BACKWARDS);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(19, 10));
        rover.move(Action.LEFT);
        rover.move(Action.LEFT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.WEST);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(19, 10));
        rover.move(Action.BACKWARDS);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(0, 10));
        rover.move(Action.FORWARD);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(19, 10));

        // Edge on Y axis
        initialPosition = Coordinate.of(10, 0);
        initialDirection = Direction.NORTH;
        rover = Rover.newInstance(planet, initialPosition, initialDirection);
        rover.move(Action.FORWARD);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 14));
        rover.move(Action.BACKWARDS);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 0));
        rover.move(Action.LEFT);
        rover.move(Action.LEFT);
        Assertions.assertEquals(rover.getFacingDirection(), Direction.SOUTH);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 0));
        rover.move(Action.BACKWARDS);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 14));
        rover.move(Action.FORWARD);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(10, 0));
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

        ObstacleEncounteredException exception = Assertions.assertThrows(ObstacleEncounteredException.class,
                () -> rover.move(Action.FORWARD));
        Assertions.assertEquals(exception.getObstacle(), obstacle2);
        Assertions.assertEquals(exception.getRoverPosition(), initialPosition);
        Assertions.assertEquals(exception.getRoverFacingDirection(), Direction.SOUTH);
        Assertions.assertEquals(exception.getGivenCommand(), Action.FORWARD);

        rover.move(Action.LEFT);
        exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.FORWARD));
        Assertions.assertEquals(exception.getObstacle(), obstacle1);
        Assertions.assertEquals(exception.getRoverPosition(), initialPosition);
        Assertions.assertEquals(exception.getRoverFacingDirection(), Direction.EAST);
        Assertions.assertEquals(exception.getGivenCommand(), Action.FORWARD);

        rover.move(Action.LEFT);
        exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.BACKWARDS));
        Assertions.assertEquals(exception.getObstacle(), obstacle2);
        Assertions.assertEquals(exception.getRoverPosition(), initialPosition);
        Assertions.assertEquals(exception.getRoverFacingDirection(), Direction.NORTH);
        Assertions.assertEquals(exception.getGivenCommand(), Action.BACKWARDS);

        rover.move(Action.LEFT);
        exception = Assertions.assertThrows(ObstacleEncounteredException.class, () -> rover.move(Action.BACKWARDS));
        Assertions.assertEquals(exception.getObstacle(), obstacle1);
        Assertions.assertEquals(exception.getRoverPosition(), initialPosition);
        Assertions.assertEquals(exception.getRoverFacingDirection(), Direction.WEST);
        Assertions.assertEquals(exception.getGivenCommand(), Action.BACKWARDS);

    }

    @Test
    public void testCommandSequenceCharArray() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);

        char[] sequence = {'l', 'F', 'f', 'R', 'f', 'r', 'b', 'B'};
        rover.move(sequence);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(6, 9));
        Assertions.assertEquals(rover.getFacingDirection(), Direction.EAST);
    }

    @Test
    void testCommandSequenceString() {
        Coordinate initialPosition = Coordinate.of(10, 10);
        Direction initialDirection = Direction.NORTH;
        Rover rover = Rover.newInstance(planet, initialPosition, initialDirection);

        String sequence = "LFFRFRBB";
        rover.move(sequence);
        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(6, 9));
        Assertions.assertEquals(rover.getFacingDirection(), Direction.EAST);
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

        Assertions.assertEquals(rover.getCurrentPosition(), Coordinate.of(8, 10));
        Assertions.assertEquals(rover.getFacingDirection(), Direction.NORTH);
        String expectedErrorMessage = String.format("The rover encountered an obstacle at %s." +
                        "\nIt stopped at %s, facing %s and cannot go %s. Command sequence aborted.",
                Coordinate.of(8, 9), Coordinate.of(8, 10), Direction.NORTH, Action.FORWARD);
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        System.err.println(exception.getMessage());
    }
}
