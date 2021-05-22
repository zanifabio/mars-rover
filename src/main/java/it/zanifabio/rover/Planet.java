package it.zanifabio.rover;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class Planet {

    private final int width;
    private final int height;
    /** Set of coordinates representing the position of obstacles on the planet*/
    private final HashSet<Coordinate> obstacles;

    private Planet(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.obstacles = new HashSet<>();
    }

    /**
     * Static factory method to create a new instance of a planet.
     * @param width it.zanifabio.rover.Planet width (number of points on the x axis)
     * @param height it.zanifabio.rover.Planet height (number of points on the y axis)
     * @return A new instance of the planet with the given height and width, without any obstacle.
     */
    public static Planet newInstance(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("We are modelling Mars, not a black hole");
        }
        return new Planet(width, height);
    }

    public boolean isOutOfBounds(final int x, final int y) {
        boolean xOutOfBounds = x < 0 || x >= getWidth();
        boolean yOutOfBounds = y < 0 || y >= getHeight();
        return xOutOfBounds || yOutOfBounds;
    }

    /**
     * Method to place an obstacle on the planet.
     * @param x X coordinate of the obstacle. it must be between 0 and width - 1
     * @param y Y coordinate of the obstacle. it must be between 0 and height - 1
     */
    public void placeObstacle(int x, int y) {
        if (isOutOfBounds(x, y)) {
            throw new IllegalArgumentException("Sorry, cannot place obstacles outside this planet");
        }
        obstacles.add(Coordinate.of(x, y));
    }

    /**
     * Method to remove a specific obstacle form the planet.
     * @param x X coordinate of the obstacle. it must be between 0 and width - 1
     * @param y Y coordinate of the obstacle. it must be between 0 and height - 1
     */
    public void removeObstacle(int x, int y) {
        if (isOutOfBounds(x, y)) {
            throw new IllegalArgumentException("Sorry, cannot remove obstacles outside this planet");
        }
        obstacles.remove(Coordinate.of(x, y));
    }

    /**
     * Method to place multiple obstacles on the planet.
     * @param coordinates Set of coordinates representing the various obstacles. All coordinates must pertain to the planet.
     */
    public void placeObstacles(Set<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {
            placeObstacle(coordinate.getX(), coordinate.getY());
        }
    }

    /**
     * Method to remove multiple obstacles on the planet.
     * @param coordinates Set of coordinates representing the various obstacles. All coordinates must pertain to the planet.
     */
    public void removeObstacles(Set<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {
            removeObstacle(coordinate.getX(), coordinate.getY());
        }
    }

    /**
     * Method to remove all the obstacles form the planet.
     */
    public void removeObstacles() {
        this.obstacles.clear();
    }
}
