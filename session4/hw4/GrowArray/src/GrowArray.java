public class GrowArray {

    private int numUsed;
    private Point[] items;

    public GrowArray() {
        numUsed = 0;
        items = new Point[1];
    }

    public GrowArray(int initialSize) throws IllegalArgumentException {
        if(initialSize < 1) {
            throw new IllegalArgumentException("Cannot initialize an array with less than 1 size");
        }
        numUsed = 0;
        items = new Point[initialSize];
    }

    private boolean isFilled() {
        return numUsed == items.length;
    }

    private void grow() {
        Point[] newArr = new Point[2 * items.length];
        for(int i = 0; i < items.length; i++) //do it manually for right now
            newArr[i] = items[i];
        items = newArr;
    }

    public void addEnd(Point p) {
        if(isFilled())
            grow();
        items[numUsed++] = p;
    }

    private void shiftedGrow(int numToShift) {
        Point[] newArr = new Point[2 * items.length];
        for(int i = 0; i < items.length; i++) //shiftRight it down one while you do it
            newArr[i + numToShift] = items[i]; //note you cannot shiftRight by more than the length of items
        items = newArr;
    }

    private void insertedGrow(int shiftIndex, Point p) { //insert a new element while growing the array
        Point[] newArr = new Point[2 * items.length];
        for(int i = 0; i < shiftIndex; i++) { //up to the shift index, simply copy
            newArr[i] = items[i];
        }
        newArr[shiftIndex] = p;
        for(int i = shiftIndex; i < items.length; i++) {
            newArr[i + 1] = items[i];
        }
        items = newArr;
    }

    private void shiftRight(int startingIndex, int numToShift) throws IndexOutOfBoundsException {
        if(numToShift + numUsed > items.length)
            throw new IndexOutOfBoundsException("Cannot shiftRight the array past the ending index");
        for(int i = numUsed - 1; i >= startingIndex; i--)
            items[i + numToShift] = items[i];
        //we will have numToShift places at the start of the array that still exist, null them out
        for(int i = 0; i < numToShift; i++)
            items[i] = null;
    }

    private void shiftLeft(int startingIndex, int numToShift) throws IndexOutOfBoundsException {
        if(numToShift > startingIndex)
            throw new IndexOutOfBoundsException("Cannot shiftRight past the beginning of the array");
        for(int i = startingIndex; i <= startingIndex; i++)
            items[i - numToShift] = items[i];
        for(int i = numUsed; i > numToShift; i--)
            items[i] = null;
    }

    //we shouldn't use the traditional grow algorithm because it will force us to shiftRight
    //everything twice
    public void addStart(Point p) {
        if (isFilled())
            shiftedGrow(1);
        else
            shiftRight(0, 1); //don't need to grow, only do a partial shiftRight
        items[0] = p;
        numUsed++;
    }

    public void insert(int i, Point p) throws IndexOutOfBoundsException {
        //shiftRight everything down one from i onward
        if(i > numUsed)
            throw new IndexOutOfBoundsException("Cannot insert an element beyond the end of the array");
        if(isFilled()) {
            insertedGrow(i, p);
            numUsed++;
        }
        else {
            shiftRight(i, 1);
            items[i] = p;
            numUsed++;
        }
    }

    private Point copy(Point p) {
        return new Point(p.x, p.y);
    }

    public Point removeEnd() throws IndexOutOfBoundsException {
        if(numUsed == 0)
            throw new IndexOutOfBoundsException("Cannot remove from empty array");
        Point p = items[numUsed - 1];
        Point copy = copy(p);
        items[--numUsed] = null;
        return copy;
    }

    public Point removeStart() throws IndexOutOfBoundsException {
        if(numUsed == 0)
            throw new IndexOutOfBoundsException("Cannot remove from empty array");
        Point copy = copy(items[0]);
        shiftLeft(1, 1);
        numUsed--;
        return copy;
    }

    public Point remove(int i) throws IndexOutOfBoundsException{
        if(i + 1 > numUsed)
            throw new IndexOutOfBoundsException("Cannot remove beyond the end of the array");
        Point copy = copy(items[i]);
        shiftLeft(i + 1, 1);
        numUsed--;
        return copy;
    }

    public Point get(int i) {
        return items[i];
    }

    public void set(int i, Point p) {
        items[i] = p;
    }

    public Point getStart() {
        return items[0];
    }

    public Point getEnd() {
        return items[numUsed - 1];
    }

    public int size() {
        return numUsed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < numUsed - 1; i++)
            sb.append(items[i]).append(", ");
        if(numUsed != 0)
            sb.append(items[numUsed - 1]);
        sb.append("]");
        return sb.toString();
    }

    public void empty() {
        numUsed = 0;
    }
}
