package tests;

import evolutionaryAlgorithm.Genotype;
import generalUtilities.FileSaving;
import graph.FloydWarshall;
import graph.Graph;

public class EATests {
	
	
	public Genotype createSingleGenoe(){
		Genotype genotype = new Genotype();
		return genotype;
	}
	
	
	
	public static void main(String[] args) {
		EATests test = new EATests();
		Graph.initialize();
		FloydWarshall.doFloydWarshall();
		System.out.println("Number of elements in graph" + Graph.numberOfElements);
		System.out.println(FileSaving.getGson().toJson(Graph.getRequiredElementsIDs()));
		
		Genotype genotype = test.createSingleGenoe();
		System.out.println("Random genome:");
		System.out.println(FileSaving.getGson().toJson(genotype));
		System.out.println(FileSaving.getGson().toJson(Graph.getRequiredElementsIDs()));
	}
}
