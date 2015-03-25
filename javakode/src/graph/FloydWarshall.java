package graph;

public class FloydWarshall {

	static double[][] allPairShortestDistances;
	static int[][] successors;
	static public double timeTakenToComputeFloydWarshall;
	
	/**Returns the distance between and origin and destination in the graph.
	 * Is used to make accessing the all pairs shortest path matrix more neat.*/
	public static double distance(int originElementID, int destinationElementID){
		return allPairShortestDistances[originElementID][destinationElementID];
	}

	public static double[][] doFloydWarshall() {
		double startTime = System.currentTimeMillis();
		int numberOfElementsInGraph = Graph.nodes.length + Graph.edges.length
				+ Graph.arcs.length;
		allPairShortestDistances = new double[numberOfElementsInGraph][numberOfElementsInGraph];
		successors = new int[numberOfElementsInGraph][numberOfElementsInGraph];

		// Initialize all distances as infinite
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				allPairShortestDistances[i][j] = Double.POSITIVE_INFINITY;
				successors[i][j] = -1;
			}
			allPairShortestDistances[i][i] = 0;
			successors[i][i] = i;
		}
		
		int edgeOffset = Graph.nodes.length;
		int arcOffset = Graph.nodes.length + Graph.edges.length;
		
		/*
		 * For each edge, set the distance between the nodes at both end as the
		 * traversal cost of the edge, it it's shorter than the currently set
		 * shortest distance
		 */
		for (int i = 0; i < Graph.edges.length; i++) {
			Edge currentEdge = Graph.edges[i];
			int node1 = currentEdge.fromNodeId;
			int node2 = currentEdge.toNodeId;

			allPairShortestDistances[edgeOffset + i][node1] = Graph.nodes[node1].passThroughCost;
			allPairShortestDistances[node1][edgeOffset + i] = Graph.edges[i].passThroughCost;
			allPairShortestDistances[edgeOffset + i][node2] = Graph.nodes[node2].passThroughCost;
			allPairShortestDistances[node2][edgeOffset + i] = Graph.edges[i].passThroughCost;
			
			successors[edgeOffset + i][node1] = node1;
			successors[node1][edgeOffset + i] = edgeOffset + i;
			successors[edgeOffset + i][node2] = node2;
			successors[node2][edgeOffset + i] = edgeOffset + i;
		}

		// For each arc, set the distance {startnode,endnode} to the arc
		// traversal cost
		for (int i = 0; i < Graph.arcs.length; i++) {
			Arc currentArc = Graph.arcs[i];
			int fromNode = currentArc.fromNodeId;
			int toNode = currentArc.toNodeId;

			allPairShortestDistances[arcOffset + i][toNode] = Graph.nodes[toNode].passThroughCost;
			allPairShortestDistances[fromNode][arcOffset + i] = Graph.arcs[i].passThroughCost;
			
			successors[arcOffset + i][toNode] = toNode;
			successors[fromNode][arcOffset + i] = arcOffset + i;
		}
		
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
		
		//Subtract destination cost from destinations
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				if(i == j){
					continue;
				}
				allPairShortestDistances[j][i] -= Graph.getElementByID(i).getPassThroughCost();
			}
		}
		
		timeTakenToComputeFloydWarshall = 0.0 + System.currentTimeMillis() - startTime;

		return allPairShortestDistances;
	}
	
	
	public static String allPairsToString(){
		String outputString = "";
		for (int i = 0; i < allPairShortestDistances.length; i++) {
			for (int j = 0; j < allPairShortestDistances.length; j++) {
				outputString += allPairShortestDistances[i][j] + "\t";
			}
			outputString += "\n";
		}
		return outputString;
	}
	
	public static String sucessorsToString(){
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
