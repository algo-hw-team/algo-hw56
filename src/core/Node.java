package core;

public class Node {

    public int id, x, y;

    public Node(int _id, int _x, int _y) {
        id = _id;
        x = _x;
        y = _y;
    }

    public int getId() {
        return id;
    }

    public static double getDistance(Node n1, Node n2) {
        int diffX = n1.x - n2.x;
        int diffY = n1.y - n2.y;

        return Math.sqrt((diffX * diffX) + (diffY * diffY));
    }
}
