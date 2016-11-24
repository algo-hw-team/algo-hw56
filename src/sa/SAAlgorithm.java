package sa;

import core.Algorithm;
import core.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SAAlgorithm extends Algorithm {

    final private double COOLING_RATE = 0.9;
    final private double KT = 100;

    private ArrayList<Integer> bestIdList = null;
    private double bestTotalDistance;

    private double temperature = 10000.0;
    Random rand = new Random();

    public SAAlgorithm(NodeList _nodeList) {
        super(_nodeList);
    }

    @Override
    public void run() {
        initialize();

        while (temperature > 0.1) {

            // iterate for kt time
            for (int i = 0; i < KT; i++) {
                simulate();
            }

            // apply cooling rate
            temperature *= COOLING_RATE;
        }
    }

    @Override
    public ArrayList<Integer> getBestIdList() {
        return bestIdList;
    }

    @Override
    public double getBestTotalDistance() {
        return bestTotalDistance;
    }

    //// private methods

    public ArrayList<Integer> initialize() {
        bestIdList = new ArrayList<>();

        for (int i = 0; i < nodeList.size(); i++) {
            bestIdList.add(i + 1);
        }

        Collections.shuffle(bestIdList);

        bestTotalDistance = getTotalDistance(bestIdList);
        return bestIdList;
    }

    public void simulate() {
        ArrayList<Integer> nextSolution = new ArrayList<>(bestIdList);

        // find neighboring bestIdList: switch
        int bound = nodeList.size();
        int index1 = rand.nextInt(bound);
        int index2 = rand.nextInt(bound);
        int value1 = nextSolution.get(index1);
        int value2 = nextSolution.get(index2);

        nextSolution.set(index1, value2);
        nextSolution.set(index2, value1);

        // compare next and current bestIdList
        double nextTotalDistance = getTotalDistance(nextSolution);

        if (nextTotalDistance < bestTotalDistance) {
            bestIdList = nextSolution;
            bestTotalDistance = nextTotalDistance;

        } else {
            double prob = getProbabilityToChange(nextTotalDistance - bestTotalDistance);

            if (rand.nextFloat() < prob) {
                bestIdList = nextSolution;
                bestTotalDistance = nextTotalDistance;
            }
        }
    }

    private double getProbabilityToChange(double diff) {
        double power = -1 * (diff / temperature);

        return Math.exp(power);
    }

    private double getTotalDistance(ArrayList<Integer> idList) {
        double totalDistance = 0;
        int last = nodeList.size() - 1;

        for (int i = 0; i < last; i++) {
            totalDistance += nodeList.getDistance(idList.get(i), idList.get(i + 1));
        }

        totalDistance += nodeList.getDistance(idList.get(last), idList.get(0));

        return totalDistance;
    }
}
