package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;
import java.util.ArrayList;

import core.Node;
import core.Algorithm;

public class MainApp {

    // choose one algorithm from (1, 2, 3, 4, 5) in project specification
    final private static int PROBLEM = 1;

    final private static String basePath = "/Users/Join/dev/homeworks-0302/algo/hw56/";
    final private static String inputPath = basePath + "13tsp.log";
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
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                nodeList.add(new Node(id, x, y));
            }

            // run algorithm
            Algorithm algorithm = getAlgorithmByProblem(nodeList);

            algorithm.run();
            ArrayList<Node> bestNodeList = algorithm.getBestNodeList();
            int bestTotalDistance = algorithm.getBestTotalDistance();

            for (Node node : bestNodeList) {
                builder.append(node.getId()).append(" ");
            }
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

    private static Algorithm getAlgorithmByProblem(ArrayList<Node> nodeList) {
        return null;
    }
}