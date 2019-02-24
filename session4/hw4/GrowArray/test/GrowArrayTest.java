import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrowArrayTest {

    private static GrowArray arr;

    @BeforeEach
    public void emptyArr() {
        arr = new GrowArray();
    }

    @Test
    public void testAddStart() {
        arr.addStart(new Point(0,1));
        assert(arr.toString().equals("[(0,1)]"));
        assert(arr.size() == 1);
    }

    @Test
    public void testAddEnd() {
        arr.addEnd(new Point(1, 2));
        arr.addEnd(new Point(2, 3));
        assert(arr.toString().equals("[(1,2), (2,3)]"));
        assert(arr.size() == 2);
    }

    @Test
    public void testRemoveMiddle() {
        arr.addEnd(new Point(3, 4));
        arr.addEnd(new Point(4, 5));
        arr.addEnd(new Point(5, 6));
        Point p = arr.remove(1);
        assert(arr.size() == 2);
        assert(arr.toString().equals("[(3,4), (5,6)]"));
        assert(p.toString().equals("(4,5)"));
    }

    @Test
    public void testInsert() {
        arr.insert(0, new Point(6, 7));
        arr.insert(1, new Point(7, 8));
        arr.insert(1, new Point(8, 9));
        assert(arr.toString().equals("[(6,7), (8,9), (7,8)]"));
    }

    @Test
    public void testGetAndSet() {
        arr.addStart(new Point(9, 10));
        assert arr.get(0).toString().equals("(9,10)");
        arr.set(0, new Point(10, 11));
        assert arr.get(0).toString().equals("(10,11)");
    }

    @Test
    public void testGetStartAndEnd() {
        arr.addStart(new Point(11, 12));
        arr.addEnd(new Point(12, 13));
        assert arr.getStart().toString().equals(("(11,12)"));
        assert arr.getEnd().toString().equals("(12,13)");
    }
}
