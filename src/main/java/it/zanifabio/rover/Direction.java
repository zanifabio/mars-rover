package it.zanifabio.rover;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum indicating a direction in the planet.
 * As a convention, north indicates lower y axis values and west indicates lower x axis values.
 */
@Getter
@AllArgsConstructor
public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    private final String shortName;

    public static Direction right(Direction direction) {
        switch (direction) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: throw new IllegalArgumentException("Illegal direction");
        }
    }

    public static Direction left(Direction direction) {
        switch (direction) {
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH: return EAST;
            case WEST: return SOUTH;
            default: throw new IllegalArgumentException("Illegal direction");
        }
    }
}
