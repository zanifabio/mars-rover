import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PlanetTest {
    Planet planet;

    @Test
    public void testNewInstance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Planet.newInstance(-1, 2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Planet.newInstance(5, -20));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Planet.newInstance(0, 0));

        Planet planet = Planet.newInstance(20,15);
        Assertions.assertEquals(20, planet.getWidth());
        Assertions.assertEquals(15, planet.getHeight());
        Assertions.assertEquals(new HashSet<Coordinate>(), planet.getObstacles());
    }

    @BeforeEach
    public void setPlanet() {
        this.planet = Planet.newInstance(20, 15);
    }

    @Test
    public void testOutOfBounds() {
        Assertions.assertTrue(planet.isOutOfBounds(-1, 12));
        Assertions.assertTrue(planet.isOutOfBounds(25, 12));
        Assertions.assertTrue(planet.isOutOfBounds(20, 12));
        Assertions.assertTrue(planet.isOutOfBounds(7, -1));
        Assertions.assertTrue(planet.isOutOfBounds(7, 20));
        Assertions.assertTrue(planet.isOutOfBounds(7, 15));
        Assertions.assertFalse(planet.isOutOfBounds(7, 12));
        Assertions.assertFalse(planet.isOutOfBounds(0, 12));
        Assertions.assertFalse(planet.isOutOfBounds(19, 0));
    }

    @Test
    public void testPlaceAndClearObstacle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> planet.placeObstacle(-1, 20));
        planet.placeObstacle(7,12);
        Assertions.assertTrue(planet.getObstacles().contains(Coordinate.of(7,12)));
        planet.clearObstacle(7,12);
        Assertions.assertFalse(planet.getObstacles().contains(Coordinate.of(7,12)));
    }

    @Test
    public void testPlaceAndClearMultipleObstacles() {
        Coordinate c1 = Coordinate.of(2,3);
        Coordinate c2 = Coordinate.of(4,7);
        Coordinate c3 = Coordinate.of(5,11);
        Coordinate c4 = Coordinate.of(18,25);

        Assertions.assertThrows(IllegalArgumentException.class, () -> planet.placeObstacles(Set.of(c1, c2, c3, c4)));

        planet.placeObstacles(Set.of(c1, c2, c3));
        Assertions.assertTrue(planet.getObstacles().contains(c1));
        Assertions.assertTrue(planet.getObstacles().contains(c2));
        Assertions.assertTrue(planet.getObstacles().contains(c3));

        planet.clearObstacles(Set.of(c1, c3));
        Assertions.assertFalse(planet.getObstacles().contains(c1));
        Assertions.assertTrue(planet.getObstacles().contains(c2));
        Assertions.assertFalse(planet.getObstacles().contains(c3));

        planet.clearObstacles();
        Assertions.assertFalse(planet.getObstacles().contains(c2));

    }



}
