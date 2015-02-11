package graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Graph {
	String problemName;
	double optimalValue;
	int numberOfVehicles;
	double vehicleCapacity;
	int depotNodeIndex;
	Node[] nodes;
	Node[] requiredNodes;
	Edge[] edges;
	Edge[] requiredEdges;
	Arc[] arcs;
	Arc[] requiredArcs;
	
	/*TODO:
	 * -graph constructor that reads from file
	 * -the grea dijkstra-table
	 * -getters for individual graph elements?
	 * -fields for best known stuff for this graph that the algorithm can use?*/
	
	public Graph(){
		//Read the file:
		String inputText = "";
		int numberOfNodes, numberOfEdges, numberOfArcs, numberOfRequiredNodes, numberOfRequiredEdges, numberOfRequiredArcs;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(parameterFiles.GraphParams.graphFilePath));
			StringBuilder stringBuilder = new StringBuilder();
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
			
			
			while(line != null){
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
			}
			inputText = stringBuilder.toString();
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(inputText);
		System.out.println("Problem name: " + problemName);
		System.out.println("Optimal value: " + optimalValue);
		System.out.println("Number of vehicles: " + numberOfVehicles);
		System.out.println("Capacity: " + vehicleCapacity);
		System.out.println("Deopt node index: " + depotNodeIndex);
		System.out.println("Number of nodes: " + nodes.length);
		System.out.println("Number of edges: " + edges.length);
		System.out.println("Number of arcs: " + arcs.length);
	}
	
	
	
	
	public static void main(String[] args) {
		Graph g = new Graph();
	}

}
