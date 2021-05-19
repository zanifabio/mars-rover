import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class Planet {

    private final int width;
    private final int height;
    private final HashSet<Coordinate> obstacles;

    private Planet(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.obstacles = new HashSet<>();
    }

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

    public void placeObstacle(int x, int y) {
        if (isOutOfBounds(x, y)) {
            throw new IllegalArgumentException("Sorry, cannot place obstacles outside this planet");
        }
        obstacles.add(Coordinate.of(x, y));
    }

    public void clearObstacle(int x, int y) {
        if (isOutOfBounds(x, y)) {
            throw new IllegalArgumentException("Sorry, cannot remove obstacles outside this planet");
        }
        obstacles.remove(Coordinate.of(x, y));
    }

    public void placeObstacles(Set<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {
            placeObstacle(coordinate.getX(), coordinate.getY());
        }
    }

    public void clearObstacles(Set<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {
            clearObstacle(coordinate.getX(), coordinate.getY());
        }
    }

    public void clearObstacles() {
        this.obstacles.clear();
    }
}
