package evolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

public class Selection {
	
	/*TODO:
	 * Adult selection
	 * 	deciding who goes on to the next round
	 * */
	
	public ArrayList<Genotype> selectParents(ArrayList<Genotype> adultPopulation){
		ArrayList<Genotype> population = new ArrayList<>();
		population.addAll(adultPopulation);
		ArrayList<Genotype> selectedParents = new ArrayList<>();
		
		
		if(parameterFiles.EvolutionaryAlgorithmParams.PARENT_SELECTION == parameterFiles.EvolutionaryAlgorithmParams.ParentSelection.UniformSelection){
			while(selectedParents.size() < parameterFiles.EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS * 2){
				selectedParents.add(population.remove(Utilities.getRandom().nextInt(population.size())));
			}
		}
		
		else if(parameterFiles.EvolutionaryAlgorithmParams.PARENT_SELECTION == parameterFiles.EvolutionaryAlgorithmParams.ParentSelection.TournamentSelection){
			while(selectedParents.size() < parameterFiles.EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS * 2){
				ArrayList<Genotype> tournamentGroup = new ArrayList<>();
				ArrayList<Genotype> tournamentGroupCopy = new ArrayList<>();
				while(tournamentGroup.size() < parameterFiles.EvolutionaryAlgorithmParams.TOURNAMENT_SIZE){
					tournamentGroup.add(population.remove(Utilities.getRandom().nextInt(population.size())));
				}
				Collections.sort(tournamentGroup);
				tournamentGroupCopy.addAll(tournamentGroup);
				for (int i = 0; i < parameterFiles.EvolutionaryAlgorithmParams.TOURNAMENT_SIZE - 1; i++) {
					if(Utilities.getRandom().nextDouble() < parameterFiles.EvolutionaryAlgorithmParams.TOURNAMEN_SELECTION_PROBABILITY){
						selectedParents.add(tournamentGroup.remove(tournamentGroup.size() - 1));
						tournamentGroupCopy.remove(tournamentGroup.size());	//is the same as the index of the element just removed
						break;
					}
					tournamentGroup.remove(tournamentGroup.size() - 1);
				}
				/*If everybody else failed the tournament, the worst participant is selected with a probability of 1:*/
				if(tournamentGroup.size() == 1){
					selectedParents.add(tournamentGroup.remove(0));
					tournamentGroupCopy.remove(0);
				}
				/*Reset the population before the next tournament so that the losers might get another chance*/
				population.addAll(tournamentGroupCopy);
			}
		}
		
		else if(parameterFiles.EvolutionaryAlgorithmParams.PARENT_SELECTION== parameterFiles.EvolutionaryAlgorithmParams.ParentSelection.FitnessProportionateSelection){
			/*Normalize fitnesses
			 * iterate upwards till you find the first genotype
			 * with a higher cdf than the random number. Remove it,
			 * normalize again, and pick the next one the same way
			 * untill you have enough parents*/
			double sumOfFitnesses = 0;
			for (Genotype genotype : population) {
				sumOfFitnesses += genotype.getFitness();
			}
			while(selectedParents.size() < parameterFiles.EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS * 2){
				double cumulativeDensityOfPrevious = 0;
				for (Genotype genotype : population) {
					genotype.resetFitness();	//For when we have removed one of its brethren in a previous iteration
					genotype.setFitness((genotype.getFitness()/sumOfFitnesses) + cumulativeDensityOfPrevious);
					cumulativeDensityOfPrevious += genotype.getFitness();
				}
				Collections.sort(population);
				double random = Utilities.getRandom().nextDouble();
				for (int i = 0; i < population.size(); i++) {
					if(random < population.get(i).getFitness()){
						population.get(i).resetFitness();
						sumOfFitnesses -= population.get(i).getFitness();
						selectedParents.add(population.remove(i));
						break;
					}
				}
				
			}
		}
		
		return selectedParents;
	}
}
