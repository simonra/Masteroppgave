package evolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

import parameterFiles.EvolutionaryAlgorithmParams;
import parameterFiles.EvolutionaryAlgorithmParams.AdultSelection;

public class EvolutionaryAlgorithm {

	/*TODO:
	 * initialize population
	 * 
	 * while some condition(s):
	 * 	make pairs
	 * 	do crossovers (two point, as described)
	 * 	calculate fitnesses
	 * 	do mutations with fitness checking
	 * 	update population
	 * 		find best inidvidual
	 * 		kill some at random
	 * 		make new population and all that stuff
	 * 	output logging data*/

	public void run(){
		if(EvolutionaryAlgorithmParams.ADULT_SELECTION == AdultSelection.FULL_REPLACEMENT && (EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS + 0.0) != (EvolutionaryAlgorithmParams.POPULATION_SIZE + 0.0) / 2.0){
			System.out.println("Full generational replacement requires that there are exactly as many new children as there are spots in the population. " +
					"Aborting because number of crossoverpair is not equal to half the population size.");
			return;
		}
		
		long generationNumber = 0;
		double startTime = System.currentTimeMillis();
		Genotype bestIndividual;
		Genotype currentGenerationsCandidate;
		
		ArrayList<Genotype> adults = new ArrayList<>();
		ArrayList<Genotype> selectedParents = new ArrayList<>();
		ArrayList<Genotype> children = new ArrayList<>();
		
		while(adults.size() < EvolutionaryAlgorithmParams.POPULATION_SIZE){
			adults.add(new Genotype());
		}
		for (Genotype individual : adults) {
			individual.calculateFitness();
		}
		bestIndividual = Collections.max(adults);
		
		while(true){
			selectedParents.clear();
			selectedParents = Selection.selectParents(adults);
			children.clear();
			for (int i = 0; i < selectedParents.size(); i += 2) {
				int[] crossoverPoints = Utilities.getRandomCrossoverPoints();
				Genotype[] generatedChildren = Utilities.crossover(selectedParents.get(i), selectedParents.get(i + 1), crossoverPoints[0], crossoverPoints[1]); 
				children.add(generatedChildren[0]);
				children.add(generatedChildren[1]);
			}
			
			for (Genotype child : children) {
				child.calculateFitness();
			}
			/*Resets the fitness of the parents before adult selection
			 * in case we did fitness proportionate scaling or something like that.*/
			for (Genotype adult : adults) {
				adult.resetFitness();
			}
			
			Selection.adultSelection(adults, children);
			
			
			
			currentGenerationsCandidate = Collections.max(adults); 
			if (currentGenerationsCandidate.compareTo(bestIndividual) >  0) {
				bestIndividual = currentGenerationsCandidate;
			}
			/*Make sure the previous best is always in the set before we update it so that we don't casually discard a good solution*/
			adults.add(bestIndividual);
			generationNumber++;
			if(generationNumber > EvolutionaryAlgorithmParams.MAX_GENERATIONS){
				break;
			}
		}
		
		double timeTaken = 0.0 + System.currentTimeMillis() - startTime;
	}

}
