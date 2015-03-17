package graph;

public class FloydWarshallInterpretation {

	double[][] allPairShortestDistances;
	int[][] successors;
	double timeTakenToComputeFloydWarshall;
	
	public double getShortestDistanceFromAToB(int graphElementAID, int graphElementBID){
		return allPairShortestDistances[graphElementAID][graphElementBID];
	}

	public double[][] FloydWarshall(Graph graph) {
		double startTime = System.currentTimeMillis();
		int numberOfElementsInGraph = graph.nodes.length + graph.edges.length
				+ graph.arcs.length;
		allPairShortestDistances = new double[numberOfElementsInGraph][numberOfElementsInGraph];
		successors = new int[numberOfElementsInGraph][numberOfElementsInGraph];

		// Initialize all distances as infinite
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				allPairShortestDistances[i][j] = Double.POSITIVE_INFINITY;
				successors[i][j] = -1;
			}
			allPairShortestDistances[i][i] = 0;
		}

		System.out.println("Distances initialized to infin and 0");
		
		int edgeOffset = graph.nodes.length;
		int arcOffset = graph.nodes.length + graph.edges.length;
		
		/*
		 * For each edge, set the distance between the nodes at both end as the
		 * traversal cost of the edge, it it's shorter than the currently set
		 * shortest distance
		 */
		for (int i = 0; i < graph.edges.length; i++) {
			Edge currentEdge = graph.edges[i];
			int node1 = currentEdge.fromNodeId - 1;
			int node2 = currentEdge.toNodeId - 1;

			allPairShortestDistances[edgeOffset + i][node1] = graph.nodes[node1].passThroughCost;
			allPairShortestDistances[node1][edgeOffset + i] = graph.edges[i].passThroughCost;
			allPairShortestDistances[edgeOffset + i][node2] = graph.nodes[node2].passThroughCost;
			allPairShortestDistances[node2][edgeOffset + i] = graph.edges[i].passThroughCost;
			
			successors[edgeOffset + i][node1] = node1;
			successors[node1][edgeOffset + i] = edgeOffset + i;
			successors[edgeOffset + i][node2] = node2;
			successors[node2][edgeOffset + i] = edgeOffset + i;
		}
		
		System.out.println("Edge-distances initialized");

		// For each arc, set the distance {startnode,endnode} to the arc
		// traversal cost
		for (int i = 0; i < graph.arcs.length; i++) {
			Arc currentArc = graph.arcs[i];
			int fromNode = currentArc.fromNodeId - 1;
			int toNode = currentArc.toNodeId - 1;

			allPairShortestDistances[arcOffset + i][toNode] = graph.nodes[toNode].passThroughCost;
			allPairShortestDistances[fromNode][arcOffset + i] = graph.arcs[i].passThroughCost;
			
			successors[arcOffset + i][toNode] = toNode;
			successors[fromNode][arcOffset + i] = arcOffset + i;
		}

		System.out.println("Arc-distances initialized");
		
		// Do floyd-warshall
		for (int k = 0; k < numberOfElementsInGraph; k++) {
//			if(k%10 == 0) System.out.println("Now on the " + k + "th of " +numberOfElementsInGraph +"th iteration");
			for (int i = 0; i < numberOfElementsInGraph; i++) {
				for (int j = 0; j < numberOfElementsInGraph; j++) {
					if (allPairShortestDistances[i][j] > allPairShortestDistances[i][k]
							+ allPairShortestDistances[k][j]) {
						allPairShortestDistances[i][j] = allPairShortestDistances[i][k]
								+ allPairShortestDistances[k][j];
						successors[i][j] = successors[i][k];
					}

				}
			}

		}
		
		System.out.println("Floyd-Warshall main itteration complete");
		
		//Subtract destination cost from destinations
		double amountToSubtract;
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			if(i < edgeOffset){
				amountToSubtract = graph.nodes[i].passThroughCost;
			}
			else if(i < arcOffset){
				amountToSubtract = graph.edges[i - edgeOffset].passThroughCost;
			}else {
				amountToSubtract = graph.arcs[i - arcOffset].passThroughCost;
			}
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				if(i == j){
					continue;
				}
				allPairShortestDistances[j][i] -= amountToSubtract;
			}
		}
		
		System.out.println("Floyd-Warshall weights fixed");
		timeTakenToComputeFloydWarshall = 0.0 + System.currentTimeMillis() - startTime;

		return allPairShortestDistances;
	}
	
	
	public String allPairsToString(){
		String outputString = "";
		for (int i = 0; i < allPairShortestDistances.length; i++) {
			for (int j = 0; j < allPairShortestDistances.length; j++) {
				outputString += allPairShortestDistances[i][j] + "\t";
			}
			outputString += "\n";
		}
		return outputString;
	}
	
	public String sucessorsToString(){
		String outputString = "";
		for (int i = 0; i < successors.length; i++) {
			for (int j = 0; j < successors.length; j++) {
				outputString += successors[i][j] + "\t";
			}
			outputString += "\n";
		}
		return outputString;
	}
	
}
