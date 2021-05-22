package it.zanifabio.rover;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, Direction> lookup = new HashMap<>();

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

    /**
     * Method to get the enum from the corresponding short string representation
     * @param shortName Short name of the action (F,B,L,R)
     * @return The corresponding it.zanifabio.rover.Action enum
     */
    public static Direction get(String shortName) {
        if (lookup.get(shortName) == null) {
            throw new IllegalArgumentException("Command not found");
        }
        return lookup.get(shortName);
    }
}
