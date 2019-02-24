public class Main {

    public static void main(String[] args) {
        GrowArray arr = new GrowArray();
        arr.addStart(new Point(1, 2));
        arr.addEnd(new Point(3, 4));
        arr.addEnd(new Point(5, 6));
        System.out.println(arr);
        arr.remove(1);
        System.out.println(arr);
        arr.removeEnd();
        System.out.println(arr);
        arr.removeStart();
        System.out.println(arr);
    }
}
