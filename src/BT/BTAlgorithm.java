package BT;

import java.util.ArrayList;

import core.Algorithm;
import core.NodeList;

public class BTAlgorithm extends Algorithm{

	ArrayList<Integer> bestNodeList;
	ArrayList<Integer> currentNodeList;
	double min;
	int count = 0;
	
	public BTAlgorithm(NodeList nodeList) {
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
		dfs(0, 1, remain);
		System.out.println(count);
	}
	
	private void dfs(double cost, int start, ArrayList<Integer> remain) {
		count++;
		if (remain.isEmpty()) {
			if (cost + super.nodeList.getDistance(start, 1) < min) {
				min = cost + super.nodeList.getDistance(start, 1);
				bestNodeList = new ArrayList<>(currentNodeList);
				return;
			}
		}
		for (int i = 0; i < remain.size(); i++) {
			if (cost + super.nodeList.getDistance(start, remain.get(i)) + super.nodeList.getDistance(remain.get(i), 1) > min) {
				continue;
			} else {
				currentNodeList.add(remain.get(i));
				ArrayList<Integer> nextRemain = new ArrayList<>(remain);
				nextRemain.remove(remain.get(i));
				dfs(cost + super.nodeList.getDistance(start, remain.get(i)), remain.get(i), nextRemain);
				currentNodeList.remove(remain.get(i));
			}
		}
	}
	

}
