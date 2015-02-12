package graph;

import java.util.LinkedList;

public class AllPairsShortestPath {

	double[][] allPairShortestDistances;
	double[][] predecessors;
	
	
	public double[][] allPairsShortestPath(Graph graph){
		int numberOfElementsInGraph = graph.nodes.length + graph.edges.length + graph.arcs.length;
		allPairShortestDistances = new double[numberOfElementsInGraph][numberOfElementsInGraph];
		predecessors = new double[numberOfElementsInGraph][numberOfElementsInGraph];
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
