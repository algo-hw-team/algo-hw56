package DP;

import java.util.ArrayList;

import core.Algorithm;
import core.Node;
import core.NodeList;

public class DPAlgorithm extends Algorithm{
	public DPAlgorithm(NodeList _nodeList) {
		super(_nodeList);
	}
	public ArrayList<Integer> getBestIdList() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getBestTotalDistance() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void run() {
		ArrayList<Node> input = super.nodeList.list;
		
		
	}
	
	static double dp (Node start, ArrayList<Node> remain, double[][] distance) {
		
		if (remain.isEmpty()) {
			return distance[start.getId() - 1][0];
		}

		return 1;
	}

}
