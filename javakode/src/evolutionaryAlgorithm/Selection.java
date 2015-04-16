package evolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

import parameterFiles.EvolutionaryAlgorithmParams;
import parameterFiles.EvolutionaryAlgorithmParams.AdultSelection;
import parameterFiles.EvolutionaryAlgorithmParams.ParentSelection;

public class Selection {
	
	/*TODO:
	 * Look into doing parent selection with replacement?
	 * */
	
	/**Depending on how the run is configured moves a combination
	 * of old adults and new children to the next generation and clears children*/
	public static void adultSelection(ArrayList<Genotype> adultPopulation, ArrayList<Genotype> children){

		if(EvolutionaryAlgorithmParams.ADULT_SELECTION == AdultSelection.FULL_REPLACEMENT){
			adultPopulation.clear();
			adultPopulation.addAll(children);
			children.clear();
		}

		else if(EvolutionaryAlgorithmParams.ADULT_SELECTION == AdultSelection.ELITIST_MIXING){
			children.addAll(adultPopulation);
			adultPopulation.clear();
			Collections.sort(children);
			adultPopulation.addAll(children.subList(children.size() - EvolutionaryAlgorithmParams.POPULATION_SIZE, children.size()));
			children.clear();
		}

		else if(EvolutionaryAlgorithmParams.ADULT_SELECTION == AdultSelection.OVERPRODUCTION){
			adultPopulation.clear();
			Collections.sort(children);
			adultPopulation.addAll(children.subList(children.size() - EvolutionaryAlgorithmParams.POPULATION_SIZE, children.size()));
			children.clear();
		}

	}

	public static ArrayList<Genotype> selectParents(ArrayList<Genotype> adultPopulation){
		ArrayList<Genotype> population = new ArrayList<>();
		population.addAll(adultPopulation);
		ArrayList<Genotype> selectedParents = new ArrayList<>();


		if(EvolutionaryAlgorithmParams.PARENT_SELECTION == ParentSelection.UNIFORM_SELECTION){
			while(selectedParents.size() < EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS * 2){
				selectedParents.add(population.remove(Utilities.getRandom().nextInt(population.size())));
			}
		}
		
		else if(EvolutionaryAlgorithmParams.PARENT_SELECTION == ParentSelection.TOURNAMENT_SELECTION){
			while(selectedParents.size() < parameterFiles.EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS * 2){
				ArrayList<Genotype> tournamentGroup = new ArrayList<>();
				ArrayList<Genotype> tournamentGroupCopy = new ArrayList<>();
				while(tournamentGroup.size() < EvolutionaryAlgorithmParams.TOURNAMENT_SIZE){
					tournamentGroup.add(population.remove(Utilities.getRandom().nextInt(population.size())));
				}
				Collections.sort(tournamentGroup);
				tournamentGroupCopy.addAll(tournamentGroup);
				for (int i = 0; i < EvolutionaryAlgorithmParams.TOURNAMENT_SIZE - 1; i++) {
					if(Utilities.getRandom().nextDouble() < EvolutionaryAlgorithmParams.TOURNAMEN_SELECTION_PROBABILITY){
						selectedParents.add(tournamentGroup.remove(tournamentGroup.size() - 1));
//						tournamentGroupCopy.remove(tournamentGroup.size());	//is the same as the index of the element just removed
						break;
					}
					tournamentGroup.remove(tournamentGroup.size() - 1);
					/*If everybody else failed the tournament, the worst participant is selected with a probability of 1:*/
					if(tournamentGroup.size() == 1){
						selectedParents.add(tournamentGroup.remove(0));
//					tournamentGroupCopy.remove(0);
					}
				}
				/*Reset the population before the next tournament so that the losers might get another chance*/
				population.addAll(tournamentGroupCopy);
			}
		}

		else if(EvolutionaryAlgorithmParams.PARENT_SELECTION== ParentSelection.FITNESS_PROPORTIONATE_SELECTION){
			/*Normalize fitnesses
			 * iterate upwards till you find the first genotype
			 * with a higher cdf than the random number. Remove it,
			 * normalize again, and pick the next one the same way
			 * untill you have enough parents*/
			double sumOfFitnesses = 0;
			for (Genotype genotype : population) {
				sumOfFitnesses += genotype.getFitness();
			}
			while(selectedParents.size() < EvolutionaryAlgorithmParams.NUMBER_OF_CROSSOVER_PAIRS * 2){
				double cumulativeDensityOfPrevious = 0;
				for (Genotype genotype : population) {
					genotype.calculateFitness();	//For when we have removed one of its brethren in a previous iteration
					genotype.setFitness((genotype.getFitness()/sumOfFitnesses) + cumulativeDensityOfPrevious);
					cumulativeDensityOfPrevious += genotype.getFitness();
				}
				Collections.sort(population);
				double random = Utilities.getRandom().nextDouble();
				for (int i = 0; i < population.size(); i++) {
					if(random < population.get(i).getFitness()){
						population.get(i).calculateFitness();
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
