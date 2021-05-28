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

    static {
        for (Direction a : Direction.values()) {
            lookup.put(a.getShortName(), a);
        }
    }

    /**
     * Static method to determine the direction obtained by turning right from a given direction.
     * @param direction The initial direction.
     * @return The direction obtained by turning right.
     */
    public static Direction right(Direction direction) {
        switch (direction) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: throw new IllegalArgumentException("Illegal cardinal direction");
        }
    }

    /**
     * Static method to determine the direction obtained by turning left from a given direction.
     * @param direction The initial direction.
     * @return The direction obtained by turning left.
     */
    public static Direction left(Direction direction) {
        switch (direction) {
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH: return EAST;
            case WEST: return SOUTH;
            default: throw new IllegalArgumentException("Illegal cardinal direction");
        }
    }

    /**
     * Method to get the enum from the corresponding short string representation
     * @param shortName Short name of the direction (N, E, S, W)
     * @return The corresponding it.zanifabio.rover.Direction enum
     *
     * @throws IllegalArgumentException When the given direction is not valid
     */
    public static Direction get(String shortName) throws IllegalArgumentException{
        if (lookup.get(shortName) == null) {
            throw new IllegalArgumentException("Illegal cardinal direction");
        }
        return lookup.get(shortName);
    }
}
