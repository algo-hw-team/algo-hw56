package BB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import core.Algorithm;
import core.NodeList;

public class BBAlgorithm extends Algorithm{
	ArrayList<Integer> bestNodeList;
	ArrayList<Integer> currentNodeList;
	double min;
	int count = 0;
	
	public BBAlgorithm(NodeList nodeList) {
		super(nodeList);
		bestNodeList= new ArrayList<>();
		currentNodeList= new ArrayList<>();
		min = Double.MAX_VALUE;
	}

	@Override
	public ArrayList<Integer> getBestIdList() {
		return bestNodeList;
	}

	@Override
	public double getBestTotalDistance() {
		double distance = 0;
		for (int i = 0; i < bestNodeList.size() - 1; i++) {
			distance += super.nodeList.getDistance(bestNodeList.get(i), bestNodeList.get(i + 1));
		}
		distance += super.nodeList.getDistance(bestNodeList.get(bestNodeList.size() - 1), 1);
		return distance;
	}
	
	@Override
	public void run() {
		currentNodeList.add(1);
		ArrayList<Integer> remain = new ArrayList<>();
		for (int i = 1; i < super.nodeList.list.size(); i++) {
			remain.add(super.nodeList.list.get(i).getId());
		}
		bfs(0, 1, remain);
		System.out.println(count);
	}
	
	private void bfs(double cost, int start, ArrayList<Integer> remain) {
		count++;
		if (remain.size() == 1) {
			if (cost + super.nodeList.getDistance(start, remain.get(0)) + super.nodeList.getDistance(remain.get(0), 1) < min) {
				min = cost + super.nodeList.getDistance(start, remain.get(0)) + super.nodeList.getDistance(remain.get(0), 1);
				bestNodeList = new ArrayList<>(currentNodeList);
				bestNodeList.add(remain.get(0));
				return;
			}
		}
		ArrayList<BoundPair> bounds = new ArrayList<>();
		for (int i = 0; i < remain.size(); i++) {
			ArrayList<Integer> nextRemain = new ArrayList<>(remain);
			nextRemain.remove(remain.get(i));
			double lowerBound = getlowerBound(remain.get(i), nextRemain);
			bounds.add(new BoundPair(remain.get(i), cost + super.nodeList.getDistance(start, remain.get(i)) + lowerBound));
		}
		Collections.sort(bounds);
		for (int i = 0; i < bounds.size(); i++) {
			if (bounds.get(i).bound > min) {
				break;
			} else {
				currentNodeList.add(bounds.get(i).id);
				ArrayList<Integer> nextRemain = new ArrayList<>(remain);
				nextRemain.remove(new Integer(bounds.get(i).id));
				bfs(cost + super.nodeList.getDistance(start, bounds.get(i).id), bounds.get(i).id, nextRemain);
				currentNodeList.remove(new Integer(bounds.get(i).id));
			}
		}
	}
	
	private double getlowerBound (int start, ArrayList<Integer> nodes) {
		ArrayList<Edge> edges = new ArrayList<>();
		HashMap<Integer, ArrayList<Integer>> containMap = new HashMap<>();
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (i != j) {
					Edge e = new Edge(nodes.get(i), nodes.get(j), super.nodeList.getDistance(nodes.get(i), nodes.get(j)));
					edges.add(e);
				}
			}
		}
		for (int i = 0; i < nodes.size(); i++) {
			ArrayList<Integer> list = new ArrayList<>();
			list.add(nodes.get(i));
			containMap.put(nodes.get(i), list);
		}
		Collections.sort(edges);
		ArrayList<Edge> mst = new ArrayList<>();
		while (!edges.isEmpty()) {
			Edge e = edges.remove(0);
			if (containMap.get(e.v1).equals(containMap.get(e.v2))) {
				continue;
			} else {
				mst.add(e);
				containMap.get(e.v1).addAll(containMap.get(e.v2));
				for (int i = 0; i < containMap.get(e.v1).size(); i++) {
					containMap.put(containMap.get(e.v1).get(i), containMap.get(e.v1));
				}
				if (containMap.get(e.v1).size() == nodes.size()) {
					break;
				}
			}
		}
		double start_nodes_min = Double.MAX_VALUE;
		for (int i = 0; i < nodes.size(); i++) {
			if (super.nodeList.getDistance(start, nodes.get(i)) < start_nodes_min) {
				start_nodes_min = super.nodeList.getDistance(start, nodes.get(i));
			}
		}
		double nodes_end_min = Double.MAX_VALUE;
		for (int i = 0; i < nodes.size(); i++) {
			if (super.nodeList.getDistance(nodes.get(i), 1) < nodes_end_min) {
				nodes_end_min = super.nodeList.getDistance(nodes.get(i), 1);
			}
		}
		double result = start_nodes_min + nodes_end_min;
		for (int i = 0; i < mst.size(); i++) {
			result += mst.get(i).distance;
		}
		return result;
	}
}
