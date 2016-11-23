package genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.Algorithm;
import core.Node;
import core.NodeList;

public class GeneticAlgorithm extends Algorithm {

    final private int POPULATION_SIZE = 30;
    final private double MUTATION_RATE = 0.1;
    final private int INF = 999999999;
    final private int TOTAL_GENERATIONS = 100;

    private Random rand = new Random();
    private ArrayList<Node> bestNodeList = null;
    private int bestTotalDistance = INF;
    private ArrayList<Chromosome> population;

    public GeneticAlgorithm(NodeList nodeList) {
        super(nodeList);
    }

    public void run() {
        initializePopulation();

        for (int i = 0; i < TOTAL_GENERATIONS; i++) {
            nextGeneration();
        }

        pickBestChromosome();
    }

    public ArrayList<Node> getBestNodeList() {
        return bestNodeList;
    }

    public int getBestTotalDistance() {
        return bestTotalDistance;
    }

    //// private methods

    private void pickBestChromosome() {
        for (Chromosome chromosome : population) {
            int distance = (int) chromosome.getTotalDistance();

            if (distance < bestTotalDistance) {
                bestTotalDistance = distance;
                bestNodeList = chromosome.getNodeList();
            }
        }
    }

    private void initializePopulation() {
        population = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 1; i < nodeList.size(); i++) {
            indexList.add(i);
        }

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(createChromosome(indexList));
            Collections.shuffle(indexList);
        }
    }

    private Chromosome createChromosome(ArrayList<Integer> indexList) {
        ArrayList<Node> localNodeList = new ArrayList<>();

        for (Integer i : indexList) {
            localNodeList.add(nodeList.get(i));
        }

        // since first node should be fixed
        localNodeList.add(0, nodeList.get(0));

        return new Chromosome(localNodeList);
    }

    private void nextGeneration() {
        ArrayList<Chromosome> nextPopulation = new ArrayList<>();

        // crossover
        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            Chromosome c1 = tournamentSelection();
            Chromosome c2 = tournamentSelection();
            ArrayList<Node> nList1 = new ArrayList<>(c1.getNodeList());
            ArrayList<Node> nList2 = new ArrayList<>(c2.getNodeList());

            crossover(nList1, nList2);
            nextPopulation.add(new Chromosome(nList1));
            nextPopulation.add(new Chromosome(nList2));
        }

        // mutation
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (rand.nextFloat() >= MUTATION_RATE) {
                continue;
            }

            ArrayList<Node> nList = new ArrayList<>(nextPopulation.get(i).getNodeList());
            mutate(nList);

            nextPopulation.set(i, new Chromosome(nList));
        }

        // swap population
        population = nextPopulation;
    }

    private void mutate(ArrayList<Node> nList) {
        int bound = nList.size() - 1;
        int i = rand.nextInt(bound) + 1;
        int j = rand.nextInt(bound) + 1;

        Node n1 = nList.get(i);
        Node n2 = nList.get(j);

        nList.set(i, n2);
        nList.set(j, n1);
    }

    private void crossover(ArrayList<Node> nList1, ArrayList<Node> nList2) {
        int cycleIndex = 4;
        int size = nList1.size();
        boolean doSwap = true;

        while (doSwap) {
            Node n1 = nList1.get(cycleIndex);
            Node n2 = nList2.get(cycleIndex);

            // swap
            nList1.set(cycleIndex, n2);
            nList2.set(cycleIndex, n1);

            doSwap = false;
            for (int i = 0; i < size; i++) {
                if ((nList1.get(i).getId() == n2.getId()) && (i != cycleIndex)) {
                    cycleIndex = i;
                    doSwap = true;
                    break;
                }
            }
        }
    }

    private Chromosome tournamentSelection() {
        int index1 = rand.nextInt(POPULATION_SIZE);
        int index2 = rand.nextInt(POPULATION_SIZE);

        Chromosome c1 = population.get(index1);
        Chromosome c2 = population.get(index2);

        // returns winner
        return (c1.getTotalDistance() < c2.getTotalDistance()) ?
                c1 :
                c2;
    }
}
