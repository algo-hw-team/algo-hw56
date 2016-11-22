package core;

import java.util.ArrayList;

abstract public class Algorithm {

    protected ArrayList<Node> nodeList;

    public Algorithm(ArrayList<Node> _nodeList) {
        nodeList = _nodeList;
    }
    abstract public void run();
    abstract public ArrayList<Node> getBestNodeList();
    abstract public int getBestTotalDistance();
}