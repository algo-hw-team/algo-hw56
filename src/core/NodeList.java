package core;

import java.util.ArrayList;

public class NodeList {
    public ArrayList<Node> list;
    public double[][] distance;

    public NodeList(ArrayList<Node> list) {
        this.list = list;
        distance = new double[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                int node1 = list.get(i).getId();
                int node2 = list.get(j).getId();
                if (node1 == node2) {
                    distance[node1 - 1][node2 - 1] = 0;
                } else {
                    distance[node1 - 1][node2 - 1] = Math.sqrt(Math.pow(list.get(i).x - list.get(j).x, 2) + Math.pow(list.get(i).y - list.get(j).y, 2));
                }
            }
        }
    }

    public int size() {
        return list.size();
    }

}
