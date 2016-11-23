package core;

import java.util.ArrayList;

abstract public class Algorithm {

    protected NodeList nodeList;

    public Algorithm(NodeList _nodeList) {
        nodeList = _nodeList;
    }
    abstract public void run();
    abstract public ArrayList<Node> getBestNodeList();
    abstract public int getBestTotalDistance();
}