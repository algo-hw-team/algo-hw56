package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;
import java.util.ArrayList;

import BB.BBAlgorithm;
import BT.BTAlgorithm;
import DP.DPAlgorithm;
import core.Node;
import core.NodeList;
import core.Algorithm;

public class MainApp {

    // choose one algorithm from (1, 2, 3, 4, 5) in project specification
    final private static int PROBLEM = 1;

    final private static String basePath = "./";
    final private static String inputPath = basePath + "38tsp.log";
    final private static String outputPath = basePath + "2013147544.txt";

    final private static StringBuilder builder = new StringBuilder();

    public static void main(String[] args) {
        try {
            //모든 인풋 텍스트를 라인단위로 리스트에 저장한다.
            BufferedReader br = new BufferedReader(new FileReader(inputPath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
            String sCurrentLine;
            ArrayList<String> inputList = new ArrayList<>();
            while ((sCurrentLine = br.readLine()) != null) {
                inputList.add(sCurrentLine);
            }

            br.close();

            int numOfNodes = Integer.parseInt(inputList.get(0));
            ArrayList<Node> nodeList = new ArrayList<>();

            for (int i = 0; i < numOfNodes; i++) {
                String[] tokens = inputList.get(i + 1).split(" ");
                int id = Integer.parseInt(tokens[0]);
                double x = Double.parseDouble(tokens[1]);
                double y = Double.parseDouble(tokens[2]);
                nodeList.add(new Node(id, x, y));
            }

            // run algorithm
            //Algorithm algorithm = getAlgorithmByProblem(new NodeList(nodeList));
            
            NodeList list = new NodeList(nodeList);
            BBAlgorithm algorithm = new BBAlgorithm(list);
            long startTime = System.currentTimeMillis();
            System.out.println("시작시간: " + startTime);
            algorithm.run();
            long endTime = System.currentTimeMillis();
            System.out.println("종료시간: " + endTime);
            System.out.println("수행시간: " + (endTime - startTime));
            ArrayList<Integer> bestNodeList = algorithm.getBestIdList();
            double bestTotalDistance = algorithm.getBestTotalDistance();

            for (Integer nodeId: bestNodeList) {
                builder.append(nodeId).append(" ");
            }
            builder.append(1);
            builder
                    .append(System.getProperty("line.separator"))
                    .append(bestTotalDistance);

            // output
            String output = builder
                    .toString()
                    .trim();

            bw.write(output);
            bw.flush();
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private static Algorithm getAlgorithmByProblem(NodeList nodeList) {
        return new GeneticAlgorithm(nodeList);
    }
    */
}