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
		firstParent.calculateFitness();
		secondParent.calculateFitness();
		children[0].calculateFitness();
		children[1].calculateFitness();
		
		validateCrossover(firstParent, secondParent, children[0], children[1], firstCrossoverPoint, secondCrossoverPoint);
		
		return new Genotype[] {firstParent, secondParent, children[0], children[1]};
	}
	
	
	public void validateCrossover(Genotype firstParent, Genotype secondParent,
			Genotype firstChild, Genotype secondChild,
			int firstCrossoverPoint, int secondCrossoverPoint){
		boolean firstChildFails = false;
		boolean secondChildFails = false;
		
		validateGenome(firstParent);
		validateGenome(secondParent);
		validateGenome(firstChild);
		validateGenome(secondChild);
		
		for (int i = firstCrossoverPoint; i != secondCrossoverPoint; i=(i+1)%Graph.numberOfRequiredElements) {
			if(firstChild.getGenome()[i] != firstParent.getGenome()[i]){
				firstChildFails = true;
			}
			if(secondChild.getGenome()[i] != secondParent.getGenome()[i]){
				secondChildFails = true;
			}
		}
		if(firstChildFails){
			System.out.println("Something was wrong with the first child");
		}
		if(secondChildFails){
			System.out.println("Something was wrong with the second child");			
		}
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
