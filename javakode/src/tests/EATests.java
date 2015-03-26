package tests;

import java.util.Arrays;

import evolutionaryAlgorithm.Genotype;
import evolutionaryAlgorithm.Utilities;
import generalUtilities.FileSaving;
import graph.FloydWarshall;
import graph.Graph;

public class EATests {
	
	
	public Genotype createSingleGenome(){
		Genotype genotype = new Genotype();
		return genotype;
	}
	
	public Genotype[] createTwoParentsAndChildren(){
		Genotype firstParent = new Genotype();
		Genotype secondParent = new Genotype();
		int firstCrossoverPoint = (int) Math.random() * Graph.numberOfRequiredElements;
		int secondCrossoverPoint = (int) Math.random() * Graph.numberOfRequiredElements;
		Genotype[] children = Utilities.crossover(firstParent, secondParent, firstCrossoverPoint, secondCrossoverPoint);
		firstParent.setFitness();
		secondParent.setFitness();
		children[0].setFitness();
		children[1].setFitness();
		
		Genotype[] result = {firstParent, secondParent, children[0], children[1]};
		return result;
	}
	
	
	public void validateCrossover(Genotype firstParent, Genotype secondParent,
			Genotype fistChild, Genotype secondChile,
			int firstCrossoverPoint, int secondCrossoverPoint){
		
	}
	
	
	
	
	public void validateGenome(Genotype genotype){
//		boolean noDuplicateTasks = true;
		boolean correctNumberOfTasks = true;
		boolean allRequiredTasks = true;
		int[] genome = genotype.getGenome();
		
		if(genome.length != Graph.numberOfRequiredElements){
			correctNumberOfTasks = false;
		}
		Arrays.sort(genome);
		if(!Arrays.equals(genome, Graph.getRequiredElementsIDs())){
			allRequiredTasks = false;
		}
		
		if(!correctNumberOfTasks){
			System.out.println("Number of tasks has failed");
		}
		if(!allRequiredTasks){
			System.out.println("Sorted genome is different from graphs required IDs");
			System.out.println(FileSaving.getGson().toJson(genome));
			System.out.println(FileSaving.getGson().toJson(Graph.getRequiredElementsIDs()));
		}
				
	}
	
	
	
	public static void main(String[] args) {
		EATests test = new EATests();
		Graph.initialize();
		FloydWarshall.doFloydWarshall();
		System.out.println("Number of required elements in graph " + Graph.numberOfRequiredElements);
		System.out.println("Required element IDs:");
		System.out.println(FileSaving.getGson().toJson(Graph.getRequiredElementsIDs()));
		
//		Genotype genotype = test.createSingleGenome();
//		System.out.println("Random genome:");
//		System.out.println(FileSaving.getGson().toJson(genotype));
//		genotype.setFitness();
//		System.out.println("Genotype fitness: " + genotype.getFitness());
		
		Genotype[] labrats = test.createTwoParentsAndChildren();
		for (Genotype rat : labrats) {
			System.out.println(FileSaving.getGson().toJson(rat));
			test.validateGenome(rat);
		}
		
		
		
	}
}
