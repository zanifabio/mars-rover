import org.junit.jupiter.api.Test;

public class DirectionTest {
    @Test
    public void testDirectionRight() {
        assert Direction.right(Direction.NORTH).equals(Direction.EAST);
        assert Direction.right(Direction.EAST).equals(Direction.SOUTH);
        assert Direction.right(Direction.SOUTH).equals(Direction.WEST);
        assert Direction.right(Direction.WEST).equals(Direction.NORTH);
    }

    @Test
    public void testDirectionLeft() {
        assert Direction.left(Direction.NORTH).equals(Direction.WEST);
        assert Direction.left(Direction.WEST).equals(Direction.SOUTH);
        assert Direction.left(Direction.SOUTH).equals(Direction.EAST);
        assert Direction.left(Direction.EAST).equals(Direction.NORTH);
    }
}
