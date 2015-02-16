package graph;

public class FloydWarshallInterpretation {

	double[][] allPairShortestDistances;
	int[][] predecessors;

	public double[][] FloydWarshall(Graph graph) {
		int numberOfElementsInGraph = graph.nodes.length + graph.edges.length
				+ graph.arcs.length;
		allPairShortestDistances = new double[numberOfElementsInGraph][numberOfElementsInGraph];
		predecessors = new int[numberOfElementsInGraph][numberOfElementsInGraph];

		// Initialize all distances as infinite
		for (int i = 0; i < numberOfElementsInGraph; i++) {
			for (int j = 0; j < numberOfElementsInGraph; j++) {
				allPairShortestDistances[i][j] = Double.POSITIVE_INFINITY;
			}
			allPairShortestDistances[i][i] = 0;
		}

		/*
		 * For each edge, set the distance between the nodes at both end as the
		 * traversal cost of the edge, it it's shorter than the currently set
		 * shortest distance
		 */
		for (int i = 0; i < graph.edges.length; i++) {
			Edge currentEdge = graph.edges[i];
			int node1 = currentEdge.fromNodeId - 1;
			int node2 = currentEdge.toNodeId - 1;

			if (currentEdge.passThroughCost < allPairShortestDistances[node1][node2]) {
				allPairShortestDistances[node1][node2] = currentEdge.passThroughCost;
			}
			if (currentEdge.passThroughCost < allPairShortestDistances[node2][node1]) {
				allPairShortestDistances[node2][node1] = currentEdge.passThroughCost;
			}
		}

		// For each arc, set the distance {startnode,endnode} to the arc
		// traversal cost
		for (int i = 0; i < graph.arcs.length; i++) {
			Arc currentArc = graph.arcs[i];
			int fromNode = currentArc.fromNodeId - 1;
			int toNode = currentArc.toNodeId - 1;

			if (currentArc.passThroughCost < allPairShortestDistances[fromNode][toNode]) {
				allPairShortestDistances[fromNode][toNode] = currentArc.passThroughCost;
			}
		}

		/*
		 * For each node, set the distance between each connected edge and
		 * outbound arc as the traversal cost of the node
		 */
		// The -1 is to compensate for 1-indexing of edges and arc.
		int edgeOffset = graph.nodes.length - 1;
		int arcOffset = graph.nodes.length + graph.edges.length - 1;
		for (int i = 0; i < graph.nodes.length; i++) {
			Node currentNode = graph.nodes[i];
			// for each inbound edge
			// Set distance to each other edge and outbound arc to the nodes
			// passthrough cost
			// for each outbound edge
			// Set distance to each other edge and outbound arc to the nodes
			// passthrough cost
			// for each inbound arc
			// Set distance to each other edge and outbound arc to the nodes
			// passthrough cost
			for (int j = 0; j < currentNode.inboundEdges.length; j++) {
				int sourceEdge = currentNode.inboundEdges[j];
				for (int k = 0; k < currentNode.inboundEdges.length; k++) {
					int destinationEdge = currentNode.inboundEdges[k];
					if (sourceEdge == destinationEdge) {
						continue;
					}
					if (currentNode.passThroughCost < allPairShortestDistances[sourceEdge
							+ edgeOffset][destinationEdge + edgeOffset]) {
						allPairShortestDistances[sourceEdge + edgeOffset][destinationEdge
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
				for (int k = 0; k < currentNode.outboundEdges.length; k++) {
					int destinationEdge = currentNode.outboundEdges[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceEdge
							+ edgeOffset][destinationEdge + edgeOffset]) {
						allPairShortestDistances[sourceEdge + edgeOffset][destinationEdge
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
				for (int k = 0; k < currentNode.outboundArcs.length; k++) {
					int destinationArc = currentNode.outboundArcs[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceEdge
							+ edgeOffset][destinationArc + arcOffset]) {
						allPairShortestDistances[sourceEdge + edgeOffset][destinationArc
								+ arcOffset] = currentNode.passThroughCost;
					}
				}
			}
			for (int j = 0; j < currentNode.outboundEdges.length; j++) {
				int sourceEdge = currentNode.outboundEdges[j];
				for (int k = 0; k < currentNode.inboundEdges.length; k++) {
					int destinationEdge = currentNode.inboundEdges[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceEdge
							+ edgeOffset][destinationEdge + edgeOffset]) {
						allPairShortestDistances[sourceEdge + edgeOffset][destinationEdge
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
				for (int k = 0; k < currentNode.outboundEdges.length; k++) {
					int destinationEdge = currentNode.outboundEdges[k];
					if (sourceEdge == destinationEdge) {
						continue;
					}
					if (currentNode.passThroughCost < allPairShortestDistances[sourceEdge
							+ edgeOffset][destinationEdge + edgeOffset]) {
						allPairShortestDistances[sourceEdge + edgeOffset][destinationEdge
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
				for (int k = 0; k < currentNode.outboundArcs.length; k++) {
					int destinationArc = currentNode.outboundArcs[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceEdge
							+ edgeOffset][destinationArc + arcOffset]) {
						allPairShortestDistances[sourceEdge + edgeOffset][destinationArc
								+ arcOffset] = currentNode.passThroughCost;
					}
				}
			}
			for (int j = 0; j < currentNode.inboundArcs.length; j++) {
				int sourceArc = currentNode.inboundArcs[j];
				for (int k = 0; k < currentNode.inboundEdges.length; k++) {
					int destinationEdge = currentNode.inboundEdges[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceArc
							+ arcOffset][destinationEdge + edgeOffset]) {
						allPairShortestDistances[sourceArc + arcOffset][destinationEdge
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
				for (int k = 0; k < currentNode.outboundEdges.length; k++) {
					int destinationEdge = currentNode.outboundEdges[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceArc
							+ arcOffset][destinationEdge + edgeOffset]) {
						allPairShortestDistances[sourceArc + arcOffset][destinationEdge
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
				for (int k = 0; k < currentNode.outboundArcs.length; k++) {
					int destinationArc = currentNode.outboundArcs[k];
					if (currentNode.passThroughCost < allPairShortestDistances[sourceArc
							+ arcOffset][destinationArc + edgeOffset]) {
						allPairShortestDistances[sourceArc + arcOffset][destinationArc
								+ edgeOffset] = currentNode.passThroughCost;
					}
				}
			}
		}

		String outputAllPairsString = "";
		for (int i = 0; i < allPairShortestDistances.length; i++) {
			for (int j = 0; j < allPairShortestDistances.length; j++) {
				outputAllPairsString += allPairShortestDistances[i][j] + "\t";
			}
			outputAllPairsString += "\n";
		}
		outputAllPairsString += "─────────────────────";
		System.out.println(outputAllPairsString);
		
		// Do floyd-warshall
		for (int k = 0; k < numberOfElementsInGraph; k++) {
			for (int i = 0; i < numberOfElementsInGraph; i++) {
				for (int j = 0; j < numberOfElementsInGraph; j++) {
					if (allPairShortestDistances[i][j] > allPairShortestDistances[i][k]
							+ allPairShortestDistances[k][j]) {
						allPairShortestDistances[i][j] = allPairShortestDistances[i][k]
								+ allPairShortestDistances[k][j];
					}

				}
			}

		}

		return allPairShortestDistances;
	}
}
