package it.zanifabio.rover;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class representing a specific rover on a planet and how to move it around.
 * The rover can have a unique planet and in each moment has a given position on the planet and a given facing direction.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Rover {

    private final Planet planet;
    private Coordinate currentPosition;
    private Direction facingDirection;


    /**
     * Static factory method to generate a new instance of a rover.
     * The rover must be placed inside the boundaries of the planet and not on any obstacle.
     * @param planet it.zanifabio.rover.Planet where the rover operates.
     * @param initialPosition Initial position of the rover on the planet. Must be inside the planet and not on obstacles.
     * @param initialFacingDirection Initial direction the rover is facing (N,E,S,W).
     * @return A new instance of the rover.
     * @throws IllegalArgumentException If the rover initial position is outside the planet or on an obstacle.
     */
    public static Rover newInstance(Planet planet, Coordinate initialPosition, Direction initialFacingDirection) {
        if (planet.isOutOfBounds(initialPosition.getX(), initialPosition.getY())) {
            throw new IllegalArgumentException("It's a land rover. It must be placed on the planet.");
        }
        if (planet.getObstacles().contains(initialPosition)) {
            throw new IllegalArgumentException("Oops, you cannot place the rover on an obstacle.");
        }
        return new Rover(planet, initialPosition, initialFacingDirection);
    }

    /**
     * Method to move the rover on the planet given an action to perform.
     * @param action The action the rover must perform. It has to be one of: FORWARD, BACKWARDS, LEFT, RIGHT
     * @throws ObstacleEncounteredException Exception indicating an obstacle was encountered, its position and the status of the rover.
     */
    public void move(Action action) {
        int step = 0;
        // Check the action, if it is a turn just change the direction,
        // otherwise set a positive or negative move depending on FORWARD or BACKWARDS action
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
        // In case of FORWARD/BACKWARDS action, check the facing direction to determine where to move.
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
        // compute the next position the rover should go
        nextPosition(action, step, axis);
    }

    /**
     * Computes the next position the rover should move to, considering edges and throwing exception if an obstacle is encountered.
     * @param action it.zanifabio.rover.Action that is being performed.
     * @param step Step to take on the given axis (+1 or -1).
     * @param axis it.zanifabio.rover.Axis the rover should move onto (X or Y).
     * @throws ObstacleEncounteredException Exception indicating an obstacle was encountered, its position and the status of the rover.
     */
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

    /**
     * Method to input the rover a sequence of actions it should perform.
     * In case of exceptions (obstacle encountered or illegal command) it moves until the sequence is valid and then aborts the sequence.
     * @param sequence Character sequence representing the sequence of commands the rover should perform.
     * @throws ObstacleEncounteredException Exception indicating an obstacle was encountered, its position and the status of the rover.
     * @throws IllegalArgumentException Exception indicating an illegal command was given in the sequence.
     */
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

    /**
     * Method to input the rover a sequence of actions it should perform.
     * In case of exceptions (obstacle encountered or illegal command) it moves until the sequence is valid and then aborts the sequence.
     * @param sequence String representing the sequence of commands the rover should perform.
     * @throws ObstacleEncounteredException Exception indicating an obstacle was encountered, its position and the status of the rover.
     * @throws IllegalArgumentException Exception indicating an illegal command was given in the sequence.
     */
    public void move(String sequence) {
        move(sequence.toCharArray());
    }

}

enum Axis {X, Y}