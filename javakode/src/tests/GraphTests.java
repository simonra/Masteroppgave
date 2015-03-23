package tests;

import graph.FloydWarshallInterpretation;
import graph.Graph;

public class GraphTests {
	
	
	public void printProblemSize(Graph graph){
		System.out.println("Number of elements (|N|+|E|+|A|):\t" + graph.numberOfElements);
		System.out.println("Number of required elements:\t" + graph.numberOfRequiredElements);
	}
	
	public void printMinimumServicingCost(Graph graph){
		System.out.println("Cost of servicing all the required elements exactly once: " + graph.sumOfServicingCostsOfRequiredElements);
	}
	
	public void printAllGraphElementsOneByOne(Graph graph){
		for (int i = 0; i < graph.numberOfElements; i++) {
			System.out.print(graph.getElementByID(i).toString());
		}
	}
	
	public void printFloydWarshallComputationTime(FloydWarshallInterpretation flw){
		System.out.println("Floyd Warshall was completed in:\t" + flw.timeTakenToComputeFloydWarshall/1000.0 + " seconds");
	}
	
	public void printFloydWarshallShortestPathMatrix(FloydWarshallInterpretation flw){
		System.out.println("The Calculated floyd-warshall shortest path matrix:");
		System.out.println(flw.allPairsToString());
	}
	
	public void printFloydWarshallSuccessorsMatrix(FloydWarshallInterpretation flw){
		System.out.println("The floyd-warshall sucessors matrix:");
		System.out.println(flw.sucessorsToString());
	}
	
	
	public static void main(String[] args) {
		GraphTests testingModule = new GraphTests();
		Graph g = new Graph();
		FloydWarshallInterpretation flw = new FloydWarshallInterpretation();
		flw.FloydWarshall(g);
		
		
		testingModule.printAllGraphElementsOneByOne(g);
		
		testingModule.printProblemSize(g);
		
		testingModule.printMinimumServicingCost(g);
		
		testingModule.printFloydWarshallComputationTime(flw);
//		
		testingModule.printFloydWarshallShortestPathMatrix(flw);
		
	}

}
