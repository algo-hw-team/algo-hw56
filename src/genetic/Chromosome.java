package genetic;

import core.NodeList;

import java.util.ArrayList;

class Chromosome {

    private ArrayList<Integer> idList;
    private double totalDistance;
    private NodeList nodeList;

    public Chromosome(NodeList _nodeList, ArrayList<Integer> _idList) {
        idList = _idList;
        nodeList = _nodeList;

        calculateTotalDistance();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public ArrayList<Integer> getIdList() {
        return idList;
    }

    private void calculateTotalDistance() {
        int length = idList.size();
        totalDistance = 0;

        for (int i = 0; i < length - 1; i++) {
            int id1 = idList.get(i);
            int id2 = idList.get(i + 1);

            totalDistance += nodeList.distance[id1][id2];
        }
    }
    
}
