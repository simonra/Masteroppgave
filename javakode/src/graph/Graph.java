package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Graph {
	String problemName;
	double optimalValue;
	public int numberOfVehicles;
	double vehicleCapacity;
	int depotNodeIndex;
	Node[] nodes;
	Node[] requiredNodes;
	Edge[] edges;
	Edge[] requiredEdges;
	Arc[] arcs;
	Arc[] requiredArcs;
	
	public int numberOfRequiredElements;
	public int numberOfElements;
	
	public int getDeoptNodeIndex(){
		if(depotNodeIndex != -1){
			return depotNodeIndex;
		} else{
			return 1;
		}
	}
	
	public ElementProperties getElementByID(int ID){
		//TODO: return the correct element
		return null;
	}
	
	/*TODO:
	 * -getters for individual graph elements?
	 * -fields for best known stuff for this graph that the algorithm can use?*/
	
	public Graph(){
		//Read the file:
		int numberOfNodes, numberOfEdges, numberOfArcs, numberOfRequiredNodes, numberOfRequiredEdges, numberOfRequiredArcs;
		String[] lineWithMultipleContent;
		String elementName;
		int currentElementID;
		int globalElementID = 0;
		int fromNode, toNode;
		double traversalCost, demand, servicingCost;
		boolean isRequired;
		Node createdNode;
		Edge createdEdge;
		Arc createdArc;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(parameterFiles.GraphParams.graphFilePath));
			//Read parameters of graph:
			String line = bufferedReader.readLine();
			problemName = line.replaceAll("Name:\t\t", "");
			
			line = bufferedReader.readLine();
			optimalValue = Double.parseDouble(line.replaceAll("Optimal value:\t", ""));
			
			line = bufferedReader.readLine();
			numberOfVehicles = Integer.parseInt(line.replaceAll("#Vehicles:\t", ""));
			
			line = bufferedReader.readLine();
			vehicleCapacity = Double.parseDouble(line.replaceAll("Capacity:\t", ""));
			
			line = bufferedReader.readLine();
			depotNodeIndex = Integer.parseInt(line.replaceAll("Depot Node:\t", ""));
			
			line = bufferedReader.readLine();
			numberOfNodes = Integer.parseInt(line.replaceAll("#Nodes:\t\t", ""));
			nodes = new Node[numberOfNodes];
			
			line = bufferedReader.readLine();
			numberOfEdges = Integer.parseInt(line.replaceAll("#Edges:\t\t", ""));
			edges = new Edge[numberOfEdges];
			
			line = bufferedReader.readLine();
			numberOfArcs = Integer.parseInt(line.replaceAll("#Arcs:\t\t", ""));
			arcs = new Arc[numberOfArcs];
			
			line = bufferedReader.readLine();
			numberOfRequiredNodes = Integer.parseInt(line.replaceAll("#Required N:\t", ""));
			requiredNodes = new Node[numberOfRequiredNodes];
			
			line = bufferedReader.readLine();
			numberOfRequiredEdges = Integer.parseInt(line.replaceAll("#Required E:\t", ""));
			requiredEdges = new Edge[numberOfRequiredEdges];
			
			line = bufferedReader.readLine();
			numberOfRequiredArcs = Integer.parseInt(line.replaceAll("#Required A:\t", ""));
			requiredArcs = new Arc[numberOfRequiredArcs];
			
			//Set the required nodes:
			bufferedReader.readLine();
			bufferedReader.readLine();
			isRequired = true;
			for (int i = 0; i < numberOfRequiredNodes; i++) {
				line = bufferedReader.readLine();
				lineWithMultipleContent = line.split("\t");
				elementName = lineWithMultipleContent[0];
				currentElementID = Integer.parseInt(elementName.replaceAll("N", ""));
				demand = Double.parseDouble(lineWithMultipleContent[1]);
				servicingCost = Double.parseDouble(lineWithMultipleContent[2]);
				createdNode = new Node(globalElementID, elementName, demand, servicingCost, isRequired);
				requiredNodes[i] = createdNode;
				nodes[currentElementID - 1] = createdNode;
				
				globalElementID++;
			}
			
			//Fill in the rest of the nodes
			isRequired = false;
			for (int i = 0; i < numberOfNodes; i++) {
				if(nodes[i] == null){
					elementName = "NrN" + (i + 1);
					createdNode = new Node(globalElementID, elementName, 0, 0, isRequired);
					nodes[i] = createdNode;
					
					globalElementID++;
				}
			}
			
			//Set the required edges:
			bufferedReader.readLine();
			bufferedReader.readLine();
			isRequired = true;
			for (int i = 0; i < numberOfRequiredEdges; i++) {
				line = bufferedReader.readLine();
				lineWithMultipleContent = line.split("\t");
				elementName = lineWithMultipleContent[0];
				currentElementID = Integer.parseInt(elementName.replaceAll("E", ""));
				fromNode = Integer.parseInt(lineWithMultipleContent[1]);
				toNode = Integer.parseInt(lineWithMultipleContent[2]);
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				demand = Double.parseDouble(lineWithMultipleContent[4]);
				servicingCost = Double.parseDouble(lineWithMultipleContent[5]);
				createdEdge = new Edge(globalElementID, elementName, fromNode, toNode, traversalCost, servicingCost, demand, isRequired);
				requiredEdges[i] = createdEdge;
				edges[i] = createdEdge;
				nodes[fromNode - 1].addConnection(true, false, i);
				nodes[toNode - 1].addConnection(true, true, i);
				
				globalElementID++;
			}
			
			
			//Set the rest of the edges
			bufferedReader.readLine();
			bufferedReader.readLine();
			isRequired = false;
			for (int i = 0; i < numberOfEdges - numberOfRequiredEdges; i++) {
				line = bufferedReader.readLine();
				lineWithMultipleContent = line.split("\t");
				elementName = lineWithMultipleContent[0];
				currentElementID = Integer.parseInt(elementName.replaceAll("NrE", ""));
				fromNode = Integer.parseInt(lineWithMultipleContent[1]);
				toNode = Integer.parseInt(lineWithMultipleContent[2]);
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				createdEdge = new Edge(globalElementID, elementName, fromNode, toNode, traversalCost, 0, 0, isRequired);
				edges[numberOfRequiredEdges + i] = createdEdge;
				nodes[fromNode - 1].addConnection(true, false, numberOfRequiredEdges + i);
				nodes[toNode - 1].addConnection(true, true, numberOfRequiredEdges + i);
				
				globalElementID++;
			}
			
			//Set the required arcs
			bufferedReader.readLine();
			bufferedReader.readLine();
			isRequired = true;
			for (int i = 0; i < numberOfRequiredArcs; i++) {
				line = bufferedReader.readLine();
				lineWithMultipleContent = line.split("\t");
				elementName = lineWithMultipleContent[0];
				currentElementID = Integer.parseInt(elementName.replaceAll("A", ""));
				fromNode = Integer.parseInt(lineWithMultipleContent[1]);
				toNode = Integer.parseInt(lineWithMultipleContent[2]);
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				demand = Double.parseDouble(lineWithMultipleContent[4]);
				servicingCost = Double.parseDouble(lineWithMultipleContent[5]);
				createdArc = new Arc(globalElementID, elementName, fromNode, toNode, traversalCost, servicingCost, demand, isRequired);
				requiredArcs[i] = createdArc;
				arcs[i] = createdArc;
				nodes[fromNode - 1].addConnection(false, false, i);
				nodes[toNode - 1].addConnection(false, true, i);
				
				globalElementID++;
			}
			
			//Set the rest of the arcs
			bufferedReader.readLine();
			bufferedReader.readLine();
			isRequired = false;
			for (int i = 0; i < numberOfArcs - numberOfRequiredArcs; i++) {
				line = bufferedReader.readLine();
				lineWithMultipleContent = line.split("\t");
				elementName = lineWithMultipleContent[0];
				currentElementID = Integer.parseInt(elementName.replaceAll("NrA", ""));
				fromNode = Integer.parseInt(lineWithMultipleContent[1]);
				toNode = Integer.parseInt(lineWithMultipleContent[2]);
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				createdArc = new Arc(globalElementID, elementName, fromNode, toNode, traversalCost, 0, 0, isRequired);
				arcs[numberOfRequiredArcs + i] = createdArc;
				nodes[fromNode - 1].addConnection(false, false, numberOfRequiredArcs + i);
				nodes[toNode - 1].addConnection(false, true, numberOfRequiredArcs + i);
				
				globalElementID++;
			}
			
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		numberOfRequiredElements = requiredNodes.length + requiredEdges.length + requiredArcs.length;
		numberOfElements = globalElementID;
		System.out.println(Arrays.toString(nodes));
	}
	
	
	
	
	public static void main(String[] args) {
		Graph g = new Graph();
		System.out.println("─────────────────────");
		FloydWarshallInterpretation flw = new FloydWarshallInterpretation();
		double[][] allPairsShortest;
		allPairsShortest = flw.FloydWarshall(g);
		System.out.println("All pairs shortest complete in " + flw.timeTakenToComputeFloydWarshall/1000.0 + " seconds");
		System.out.println("Problem size (|N|+|E|+|A|):" + allPairsShortest.length);
	}

}
