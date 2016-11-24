package core;

public class Node {

    public int id;
    public double x, y;

    public Node(int _id, double _x, double _y) {
        id = _id;
        x = _x;
        y = _y;
    }

    public int getId() {
        return id;
    }
    
    public double getX() {
    	return x;
    }
    
    public double getY() {
    	return y;
    }
}
