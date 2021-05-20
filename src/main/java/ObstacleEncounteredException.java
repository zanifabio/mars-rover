import lombok.Getter;

@Getter
public class ObstacleEncounteredException extends IllegalArgumentException {
    private final Coordinate obstacle;
    private final Coordinate roverPosition;
    private final Direction roverFacingDirection;
    private final Action givenCommand;

    public ObstacleEncounteredException(Rover rover, Coordinate obstacle, Action action) {
        super("The rover encountered an obstacle at" + obstacle);
        this.obstacle = obstacle;
        this.roverPosition = rover.getCurrentPosition();
        this.roverFacingDirection = rover.getFacingDirection();
        this.givenCommand = action;
    }
}
