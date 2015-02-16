package graph;

public class FloydWarshallInterpretation {
	
	double[][] allPairShortestDistances;
	int[][] predecessors;
	
	
	public double[][] FloydWarshall(Graph graph){
		int numberOfElementsInGraph = graph.nodes.length + graph.edges.length + graph.arcs.length;
		allPairShortestDistances = new double[numberOfElementsInGraph][numberOfElementsInGraph];
		predecessors = new int[numberOfElementsInGraph][numberOfElementsInGraph];
		
		//Initialize all distances as infinite
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				allPairShortestDistances[i][j] = Double.POSITIVE_INFINITY;
			}
			allPairShortestDistances[i][i] = 0;
		}
		
		/*For each edge, set the distance between the nodes at both end 
		 * as the traversal cost of the edge, it it's shorter than the 
		 * currently set shortest distance*/
		for (int i = 0; i < graph.edges.length; i++) {
			Edge currentEdge = graph.edges[i];
			int node1 = currentEdge.fromNodeId - 1;
			int node2 = currentEdge.toNodeId - 1;
			
			if(currentEdge.passThroughCost < allPairShortestDistances[node1][node2]){
				allPairShortestDistances[node1][node2] = currentEdge.passThroughCost; 
			}
			if(currentEdge.passThroughCost < allPairShortestDistances[node2][node1]){
				allPairShortestDistances[node2][node1] = currentEdge.passThroughCost; 
			}
		}
		
		//For each arc, set the distance {startnode,endnode} to the arc traversal cost
		for (int i = 0; i < graph.arcs.length; i++) {
			Arc currentArc = graph.arcs[i];
			int fromNode = currentArc.fromNodeId - 1;
			int toNode = currentArc.toNodeId - 1;
			
			if(currentArc.passThroughCost < allPairShortestDistances[fromNode][toNode]){
				allPairShortestDistances[fromNode][toNode] = currentArc.passThroughCost;
			}
		}
		
		
		//TODO:
		//For each node, set the distance between each connected edge and outbound arc as the traversal cost of the node
		
		//Do floyd-warshall
		
		
		
		
		
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				
			}
			
			
		}
		
		
		
		return allPairShortestDistances;
	}
}
