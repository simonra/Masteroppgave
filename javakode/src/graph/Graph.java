package graph;

import java.io.BufferedReader;
import java.io.FileReader;

import parameterFiles.GraphParams;
//import java.util.Arrays;

public class Graph {
	/**The problem name given in the input file*/
	public static String problemName;
	/**The optimal value given in the input file. 
	 * It is -1 if unknown.*/
	static double optimalValue;
	/**The number of available vehicles given in the input file. 
	 * Is -1 if unspecified/unconstrained.*/
	public static int numberOfVehicles;
	/**The capacity of each vehicle, given in the input file.*/
	public static double vehicleCapacity;
	/**The index/id of the depot node as given in the input file.
	 * Is -1 if */
	static int depotNodeIndex;
	/**The array holding all the nodes, including the required nodes.*/
	static Node[] nodes;
	/**The array containing only the nodes that the problem states
	 * must be traversed for the problem to be solved.*/
	static Node[] requiredNodes;
	static Edge[] edges;
	static Edge[] requiredEdges;
	static Arc[] arcs;
	static Arc[] requiredArcs;
	
	static int[] requiredElementIDs;
	
	public static double sumOfServicingCostsOfRequiredElements;
	public static double sumOfDemand;
	public static double averageDeamnd;
	public static double demandStandardDeviation;
	
	public static int numberOfRequiredElements;
	public static int numberOfElements;
	public static double averageNumberOfRequiredElements;
	public static double varianceOfRequiredElements;
	
	public static int[] getRequiredElementsIDs(){
		if(requiredElementIDs == null){
			requiredElementIDs = new int[numberOfRequiredElements];
			int iterator = 0;
			for (int i = 0; i < requiredNodes.length; i++) {
				requiredElementIDs[iterator] = requiredNodes[i].getID();
				iterator++;
			}
			for (int i = 0; i < requiredEdges.length; i++) {
				requiredElementIDs[iterator] = requiredEdges[i].getID();
				iterator++;
			}
			for (int i = 0; i < requiredArcs.length; i++) {
				requiredElementIDs[iterator] = requiredArcs[i].getID();
				iterator++;
			}
		}
		return requiredElementIDs;
	}
	
	private static void calculateDemandStandardDeviation(){
		if(numberOfRequiredElements <= 1){
			return;
		}
		demandStandardDeviation = 0;
		
		for (int i = 0; i < requiredNodes.length; i++) {
			demandStandardDeviation += Math.pow((requiredNodes[i].demand - averageDeamnd),2);
		}
		for (int i = 0; i < requiredEdges.length; i++) {
			demandStandardDeviation += Math.pow((requiredEdges[i].demand - averageDeamnd),2);
		}
		for (int i = 0; i < requiredArcs.length; i++) {
			demandStandardDeviation += Math.pow((requiredArcs[i].demand - averageDeamnd),2);
		}
		demandStandardDeviation = demandStandardDeviation / (numberOfRequiredElements);
		demandStandardDeviation = Math.sqrt(demandStandardDeviation);
	}
	
	public static int getDeoptNodeIndex(){
		if(depotNodeIndex != -1){
			return depotNodeIndex;
		} else{
			return 1;
		}
	}
	
	public static int getDepotNodeIndex(int IDOfFirstElementInTour){
		if(depotNodeIndex != -1){
			return depotNodeIndex;
		} else{
			return IDOfFirstElementInTour;
		}
	}
	
	public static ElementProperties getElementByID(int ID){
		//TODO: Test this code
		ElementProperties requestedElement;
		if(ID < nodes.length){
			requestedElement = nodes[ID];
		}else if(ID < nodes.length + edges.length){
			requestedElement = edges[ID - nodes.length];
		}else{
			requestedElement = arcs[ID - (edges.length + nodes.length)];
		}
		return requestedElement;
	}
	
	public static void initialize(){
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
		
		sumOfDemand = 0;
		sumOfServicingCostsOfRequiredElements = 0;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(GraphParams.GRAPH_FILE_PATH));
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
				globalElementID = currentElementID - 1;
				demand = Double.parseDouble(lineWithMultipleContent[1]);
				sumOfDemand += demand;
				servicingCost = Double.parseDouble(lineWithMultipleContent[2]);
				sumOfServicingCostsOfRequiredElements += servicingCost;
				createdNode = new Node(globalElementID, elementName, demand, servicingCost, isRequired);
				requiredNodes[i] = createdNode;
				nodes[globalElementID] = createdNode;
			}
			
			//Fill in the rest of the nodes
			isRequired = false;
			for (int i = 0; i < numberOfNodes; i++) {
				if(nodes[i] == null){
					globalElementID = i;
					elementName = "NrN" + (i + 1);
					createdNode = new Node(globalElementID, elementName, 0, 0, isRequired);
					nodes[i] = createdNode;
				}
			}
			globalElementID = numberOfNodes;
			
			//Set the required edges:
			bufferedReader.readLine();
			bufferedReader.readLine();
			isRequired = true;
			for (int i = 0; i < numberOfRequiredEdges; i++) {
				line = bufferedReader.readLine();
				lineWithMultipleContent = line.split("\t");
				elementName = lineWithMultipleContent[0];
				currentElementID = Integer.parseInt(elementName.replaceAll("E", ""));
				fromNode = Integer.parseInt(lineWithMultipleContent[1]) - 1;
				toNode = Integer.parseInt(lineWithMultipleContent[2]) - 1;
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				demand = Double.parseDouble(lineWithMultipleContent[4]);
				sumOfDemand += demand;
				servicingCost = Double.parseDouble(lineWithMultipleContent[5]);
				sumOfServicingCostsOfRequiredElements += servicingCost;
				createdEdge = new Edge(globalElementID, elementName, fromNode, toNode, traversalCost, servicingCost, demand, isRequired);
				requiredEdges[i] = createdEdge;
				edges[i] = createdEdge;
				nodes[fromNode].addConnection(true, false, i);
				nodes[toNode].addConnection(true, true, i);
				
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
				fromNode = Integer.parseInt(lineWithMultipleContent[1]) - 1;
				toNode = Integer.parseInt(lineWithMultipleContent[2]) - 1;
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				createdEdge = new Edge(globalElementID, elementName, fromNode, toNode, traversalCost, 0, 0, isRequired);
				edges[numberOfRequiredEdges + i] = createdEdge;
				nodes[fromNode].addConnection(true, false, numberOfRequiredEdges + i);
				nodes[toNode].addConnection(true, true, numberOfRequiredEdges + i);
				
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
				fromNode = Integer.parseInt(lineWithMultipleContent[1]) - 1;
				toNode = Integer.parseInt(lineWithMultipleContent[2]) - 1;
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				demand = Double.parseDouble(lineWithMultipleContent[4]);
				sumOfDemand += demand;
				servicingCost = Double.parseDouble(lineWithMultipleContent[5]);
				sumOfServicingCostsOfRequiredElements += servicingCost;
				createdArc = new Arc(globalElementID, elementName, fromNode, toNode, traversalCost, servicingCost, demand, isRequired);
				requiredArcs[i] = createdArc;
				arcs[i] = createdArc;
				nodes[fromNode].addConnection(false, false, i);
				nodes[toNode].addConnection(false, true, i);
				
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
				fromNode = Integer.parseInt(lineWithMultipleContent[1]) - 1;
				toNode = Integer.parseInt(lineWithMultipleContent[2]) - 1;
				traversalCost = Double.parseDouble(lineWithMultipleContent[3]);
				createdArc = new Arc(globalElementID, elementName, fromNode, toNode, traversalCost, 0, 0, isRequired);
				arcs[numberOfRequiredArcs + i] = createdArc;
				nodes[fromNode].addConnection(false, false, numberOfRequiredArcs + i);
				nodes[toNode].addConnection(false, true, numberOfRequiredArcs + i);
				
				globalElementID++;
			}
			
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		numberOfRequiredElements = requiredNodes.length + requiredEdges.length + requiredArcs.length;
		numberOfElements = globalElementID;
		averageDeamnd = sumOfDemand / numberOfRequiredElements;
		averageNumberOfRequiredElements = (numberOfRequiredElements + 1)/2.0;
		varianceOfRequiredElements = (numberOfRequiredElements*numberOfRequiredElements - 1)/12.0;
		calculateDemandStandardDeviation();
	}
	
	

}
