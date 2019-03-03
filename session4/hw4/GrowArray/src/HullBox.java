public class HullBox {

    private Integer bigX; //box this one to check for initialization
    private int littleX, bigY, littleY;
    private GrowArray arr;

    public HullBox() {
        arr = new GrowArray();
    }

    public void add(Point p) {
        arr.addEnd(p);
        if(bigX == null) { //if it's the first point, set them all to that point
            bigX = littleX = p.x;
            bigY = littleY = p.y;
        }
        else {
            bigX = Math.max(bigX, p.x);
            littleX = Math.min(littleX, p.x);
            bigY = Math.max(bigY, p.y);
            littleY = Math.min(littleY, p.y);
        }
    }

    public int size() {
        return arr.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(50); //assume around 50 size for the string
        sb.append("BigX: ").append(bigX).append("\n");
        sb.append("LittleX: ").append(littleX).append("\n");
        sb.append("BigY: ").append(bigY).append("\n");
        sb.append("LittleY: ").append(littleY);
        return sb.toString();
    }
}
