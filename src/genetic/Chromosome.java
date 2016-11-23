package genetic;

import core.Node;

import java.util.ArrayList;

public class Chromosome {

    private ArrayList<Node> nodeList;
    private double totalDistance;

    public Chromosome(ArrayList<Node> _nodeList) {
        nodeList = _nodeList;
        totalDistance = 0;
        int length = nodeList.size();

        for (int i = 0; i < length - 1; i++) {
            totalDistance += Node.getDistance(nodeList.get(i), nodeList.get(i + 1));
        }

        totalDistance += Node.getDistance(nodeList.get(length - 1), nodeList.get(0));
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }
    
}
