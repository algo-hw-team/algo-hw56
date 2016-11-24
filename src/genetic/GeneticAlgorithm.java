package genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.Algorithm;
import core.NodeList;

public class GeneticAlgorithm extends Algorithm {

    final private int POPULATION_SIZE = 30;
    final private double MUTATION_RATE = 0.1;
    final private int INF = 999999999;
    final private int TOTAL_GENERATIONS = 100;

    private Random rand = new Random();
    private ArrayList<Integer> bestIdList = null;
    private double bestTotalDistance = INF;
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

    public ArrayList<Integer> getBestIdList() {
        return bestIdList;
    }

    public double getBestTotalDistance() {
        return bestTotalDistance;
    }

    //// private methods

    private void pickBestChromosome() {
        for (Chromosome chromosome : population) {
            double distance = chromosome.getTotalDistance();

            if (distance < bestTotalDistance) {
                bestTotalDistance = distance;
                bestIdList = chromosome.getIdList();
            }
        }
    }

    private void initializePopulation() {
        population = new ArrayList<>();
        ArrayList<Integer> idList = new ArrayList<>();

        for (int i = 1; i < nodeList.size(); i++) {
            idList.add(i + 1);
        }

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(createChromosome(idList));
            Collections.shuffle(idList);
        }
    }

    private Chromosome createChromosome(ArrayList<Integer> idList) {
        ArrayList<Integer> localIdList = new ArrayList<>(idList);

        // since first node should be fixed
        localIdList.add(0, 1);

        return new Chromosome(nodeList, localIdList);
    }

    private void nextGeneration() {
        ArrayList<Chromosome> nextPopulation = new ArrayList<>();

        // crossover
        for (int i = 0; i < POPULATION_SIZE;) {
            Chromosome c1 = tournamentSelection();
            Chromosome c2 = tournamentSelection();
            ArrayList<Integer> idList1 = new ArrayList<>(c1.getIdList());
            ArrayList<Integer> idList2 = new ArrayList<>(c2.getIdList());

            crossover(idList1, idList2);
            nextPopulation.add(new Chromosome(nodeList, idList1));
            if (++i >= POPULATION_SIZE) {
                break;
            }

            nextPopulation.add(new Chromosome(nodeList, idList2));
            if (++i >= POPULATION_SIZE) {
                break;
            }
        }

        // mutation
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (rand.nextFloat() >= MUTATION_RATE) {
                continue;
            }

            ArrayList<Integer> idList = new ArrayList<>(nextPopulation.get(i).getIdList());
            mutate(idList);

            nextPopulation.set(i, new Chromosome(nodeList, idList));
        }

        // swap population
        population = nextPopulation;
    }

    private void mutate(ArrayList<Integer> nList) {
        int bound = nList.size() - 1;
        int i = rand.nextInt(bound) + 1;
        int j = rand.nextInt(bound) + 1;

        int n1 = nList.get(i);
        int n2 = nList.get(j);

        nList.set(i, n2);
        nList.set(j, n1);
    }

    private void crossover(ArrayList<Integer> nList1, ArrayList<Integer> nList2) {
        int cycleIndex = 4;
        int size = nList1.size();
        boolean doSwap = true;

        while (doSwap) {
            int id1 = nList1.get(cycleIndex);
            int id2 = nList2.get(cycleIndex);

            // swap
            nList1.set(cycleIndex, id2);
            nList2.set(cycleIndex, id1);

            doSwap = false;
            for (int i = 0; i < size; i++) {
                if ((nList1.get(i) == id2) && (i != cycleIndex)) {
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
