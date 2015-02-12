package graph;

import java.util.LinkedList;

public class AllPairsShortestPath {

	double[][] allPairShortestDistances;
	int[][] predecessors;
	
	
	public double[][] allPairsShortestPath(Graph graph){
		int numberOfElementsInGraph = graph.nodes.length + graph.edges.length + graph.arcs.length;
		allPairShortestDistances = new double[numberOfElementsInGraph][numberOfElementsInGraph];
		predecessors = new int[numberOfElementsInGraph][numberOfElementsInGraph];
		boolean[] fixed = new boolean[numberOfElementsInGraph];
		
		
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				allPairShortestDistances[i][j] = Double.POSITIVE_INFINITY;
				fixed[j] = false;
			}
			
			
		}
		
		
		
		return allPairShortestDistances;
	}
	
}
