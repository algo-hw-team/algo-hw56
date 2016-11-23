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
        int last = idList.size() - 1;
        totalDistance = 0;

        for (int i = 0; i < last; i++) {
            int index1 = idList.get(i) - 1;
            int index2 = idList.get(i + 1) - 1;

            totalDistance += nodeList.distance[index1][index2];
        }

        totalDistance += nodeList.distance[idList.get(last) - 1][0];
    }
    
}
