package tests;

import java.util.Arrays;

import evolutionaryAlgorithm.FitnessModule;
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
		Utilities.init();
		System.out.println("Number of required elements in graph " + Graph.numberOfRequiredElements);
		System.out.println("Required element IDs:");
		System.out.println(FileSaving.getGson().toJson(Graph.getRequiredElementsIDs()));
		
		Genotype genotype = test.createSingleGenome();
		System.out.println("Random genome:");
		System.out.println(FileSaving.getGson().toJson(genotype));
		genotype.calculateFitness();
		System.out.println("Genotype fitness: " + genotype.getFitness());
		
//		Genotype[] labrats = test.createTwoParentsAndChildren();
//		for (Genotype rat : labrats) {
//			System.out.println(FileSaving.getGson().toJson(rat));
//			test.validateGenome(rat);
//		}
		
//		System.out.println();
//		int[] testGenome = new int[60];
//		for (int i = 0; i < testGenome.length; i++) {
//			testGenome[i] = 60 + i;
//		}
////		Genotype testGenotype = new Genotype(testGenome);
//		System.out.println("The test genome has a fitness of: " + FitnessModule.tripCost(testGenome));
		
		String[] genomeFromData = {"E146137741","A146135111","E146137742","E146137743","E146137744","E146136061","E146136062","E107","E108","E146136771","E146136770","E245778140","E146151149","E146151148","E146137613","E146137612","E146132701","E146132702","E146132703","E212379613","E146135454","E146136748","E146137240","E245604093","A146135455","A146135456","E146134503","E245609100","E245609099","E245609101","E245609102","E323096222","E323096223","E146138046","E146135150","E146135457","E146135458","E146135459","E146135460","E245609097","E245609098","A146133117","A146133116","A146133115","A146133114","E146133537","E245626970","E245626969","E200425266","E200425265","E200425263","E200425264","E146136049","E200425255","E300008","E200425258","E200425259","E200425262","A146138004","A146138003","A146138002","E146138703","E146138702","A146138047","A146132704","A146138048","E146138049","E146138472","E146138473","E146138050","E146134938","E146134937","E245609089","E245609090","E200425261","E146138051","E200425253","E30007","E146138052","E146134153","E146132876","E146132875","E146136892","E146132874","E146139271","E146149233","E146148844","E146138155","E146133538","E146133604","E146134528","E146133603","E146137860","E146134227","E146134228","E146136917","E146136916","E146134494","E146134493","E146133554","E146133553","E146133552","E146135759","E146134492","A146132779","E146138342","E146138343","E146132989","E146138660","E146133876","E240551660"};
		int[] requiredIDs = Graph.getRequiredElementsIDs();
		int[] testGenome = new int[requiredIDs.length];
		int id = -1;
		
		for (int j = 0; j < genomeFromData.length; j++) {
			for (int i = 0; i < requiredIDs.length; i++) {
				if(Graph.getElementByID(requiredIDs[i]).getName().equals(genomeFromData[j])){
					id = requiredIDs[i];
					break;
				}
			}
			testGenome[j] = id;
		}
//		Genotype testGenotype = new Genotype(testGenome);
		System.out.println("The test genome has a fitness of: " + FitnessModule.tripCost(testGenome));
		
		
	}
}


































