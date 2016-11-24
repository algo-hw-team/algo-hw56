package DP;

import java.util.ArrayList;
import java.util.HashMap;

import core.Algorithm;
import core.Node;
import core.NodeList;
import jdk.internal.org.objectweb.asm.commons.RemappingMethodAdapter;

public class DPAlgorithm extends Algorithm {
	ArrayList<Integer> bestNodeList;
	HashMap<Integer, HashMap<ArrayList<Integer>, ArrayList<Integer>>> map;
	long count;
	
	public DPAlgorithm(NodeList _nodeList) {
		super(_nodeList);
		map = new HashMap<>();
		bestNodeList = new ArrayList<>();
		count = 0;
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
		System.out.println("start DP");
		bestNodeList.add(1);
		ArrayList<Integer> remain = new ArrayList<>();
		for (int i = 1; i < super.nodeList.list.size(); i++) {
			remain.add(super.nodeList.list.get(i).getId());
		}
		System.out.println("recursive DP");
		ArrayList<Integer> result = dp(1, remain);
		bestNodeList.addAll(result);
		System.out.println("end DP: " + count);
	}

	private ArrayList<Integer> dp(int start, ArrayList<Integer> remain) {
		count++;
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (map.containsKey(start)) {
			if (map.get(start).containsKey(remain)) {
				for (int i = 0; i < map.get(start).get(remain).size(); i++) {
					result.add(map.get(start).get(remain).get(i));
				}
				return result;
			}
		}
		double min = Double.MAX_VALUE;
		for (int i = 0; i < remain.size(); i++) {
			int next = remain.get(i);
			ArrayList<Integer> nextRemain = new ArrayList<Integer>();
			for (int j = 0; j < remain.size(); j++) {
				if (j != i) {
					nextRemain.add(remain.get(j));
				}
			}
			ArrayList<Integer> partial = dp(next, nextRemain);
			partial.add(0, next);
			double pDistance = super.nodeList.getDistance(start, next);
			for (int j = 0; j < partial.size() - 1; j++) {
				pDistance += super.nodeList.getDistance(partial.get(j), partial.get(j + 1));
			}
			pDistance += super.nodeList.getDistance(partial.get(partial.size() - 1), 1);
			if (min > pDistance) {
				while(!result.isEmpty()) {
					result.remove(0);
				}
				for (int j = 0; j < partial.size(); j++) {
					result.add(partial.get(j));
				}
				min = pDistance;
			}
		}
		if (map.containsKey(start)) {
			map.get(start).put(remain, result);
		} else {
			map.put(start, new HashMap<>());
			map.get(start).put(remain, result);
		}
		return new ArrayList<>(result);
	}
}