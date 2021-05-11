import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Planet {

    private Point[][] planetMap;

    private Planet(Point[][] planetMap) {
        this.planetMap = planetMap;
    }

    public static Planet newInstance(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("We are modelling Mars, not a black hole");
        }
        Point[][] planetMap = new Point[height][width];
        return new Planet(planetMap);
    }

    public int getHeight() {
        return planetMap.length;
    }

    public int getWidth() {
        return planetMap[0].length;
    }

    public void placeObstacle(int x, int y) {
        if (isOutOfBounds(x, y)) {
            throw new IllegalArgumentException("Sorry, cannot place obstacles outside this planet");
        }
        planetMap[y][x].setHasObstacle(true);
    }

    public void clearObstacle(int x, int y) {
        if (isOutOfBounds(x, y)) {
            throw new IllegalArgumentException("Sorry, cannot remove obstacles outside this planet");
        }
        planetMap[y][x].setHasObstacle(false);
    }

    private boolean isOutOfBounds(final int x, final int y) {
        boolean xOutOfBounds = x < 0 || x >= getWidth();
        boolean yOutOfBounds = y < 0 || y >= getHeight();
        return xOutOfBounds || yOutOfBounds;
    }

    public void placeObstacles(List<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {
            placeObstacle(coordinate.getX(), coordinate.getY());
        }
    }

    public void clearObstacles(List<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {
            clearObstacle(coordinate.getX(), coordinate.getY());
        }
    }

    public void clearObstacles() {
        planetMap = new Point[getHeight()][getWidth()];
    }
}
