package tests;

import graph.FloydWarshall;
import graph.Graph;

public class GraphTests {
	
	
	public void printProblemSize(){
		System.out.println("Number of elements (|N|+|E|+|A|):\t" + Graph.numberOfElements);
		System.out.println("Number of required elements:\t" + Graph.numberOfRequiredElements);
	}
	
	public void printMinimumServicingCost(){
		System.out.println("Cost of servicing all the required elements exactly once: " + Graph.sumOfServicingCostsOfRequiredElements);
	}
	
	public void printAllGraphElementsOneByOne(){
		for (int i = 0; i < Graph.numberOfElements; i++) {
			System.out.print(Graph.getElementByID(i).toString());
		}
	}
	
	public void printFloydWarshallComputationTime(){
		System.out.println("Floyd Warshall was completed in:\t" + FloydWarshall.timeTakenToComputeFloydWarshall/1000.0 + " seconds");
	}
	
	public void printFloydWarshallShortestPathMatrix(){
		System.out.println("The Calculated floyd-warshall shortest path matrix:");
		System.out.println(FloydWarshall.allPairsToString());
	}
	
	public void printFloydWarshallSuccessorsMatrix(){
		System.out.println("The floyd-warshall sucessors matrix:");
		System.out.println(FloydWarshall.sucessorsToString());
	}
	
	
	public static void main(String[] args) {
		GraphTests testingModule = new GraphTests();
		Graph.initialize();
		FloydWarshall.doFloydWarshall();
		
		
		testingModule.printAllGraphElementsOneByOne();
		
		testingModule.printProblemSize();
		
		testingModule.printMinimumServicingCost();
		
		testingModule.printFloydWarshallComputationTime();

		testingModule.printFloydWarshallShortestPathMatrix();
		
	}

}
