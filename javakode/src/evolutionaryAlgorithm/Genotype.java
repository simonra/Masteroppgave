package evolutionaryAlgorithm;

import parameterFiles.EvolutionaryAlgorithmParams;
import graph.Graph;

public class Genotype implements Comparable<Genotype>{
	
	public Genotype(){
		initializeRandomly();
	}
	
	public Genotype(int[] genome){
//		this.genome = new int[genome.length];
//		System.arraycopy(genome, 0, this.genome, 0, genome.length);
		this.genome = genome.clone();
	}
	
	/**The indexes of the (required) graph-elements of the trip this genome encodes in the order they are in the tour.*/
	int[] genome;
	double fitness = -1.0;
	/**If the fitness has been forcibly set to a number,
	 * for an instance due to normalization, this can be
	 * used to reset the fitness to it's original value
	 * without having to recalculate it.*/
	double previousFitness = -1.0;
	
	/**Returns the fitness of this genome. If it hasn't
	 * been calculated already it calculates and sets it
	 * before it returns it.*/
	public double getFitness(){
		if(fitness == -1.0){
			calculateFitness();
		}
		return fitness;
	}
	
	/**Calculates the fitness of this genome. Chooses how
	 * to calculate it based on the FITNESS_TYPE variable
	 * in EA-params*/
	public void calculateFitness(){
		if(EvolutionaryAlgorithmParams.FINTESS_TYPE == EvolutionaryAlgorithmParams.fitnessType.GRAND_TOUR){
			this.fitness = FitnessModule.tripCost(genome);
		}else if(EvolutionaryAlgorithmParams.FINTESS_TYPE == EvolutionaryAlgorithmParams.fitnessType.SPLITTED){
			FitnessModule.split(this);
		}
	}
	
	/**Sets this genomes fitness to the given value.
	 * Useful for normalizing fitnesses*/
	public void setFitness(double fitness){
		this.previousFitness = this.fitness;
		this.fitness = fitness;
	}
	
	/**If the fitness has been forcibly set to a number,
	 * for an instance due to normalization, this resets
	 * the fitness to it's original value without recalculating it.*/
	public void resetFitness(){
		if(this.previousFitness != -1){
			this.fitness = this.previousFitness;
		}
	}
	
	public void mutate(){
		if(!EvolutionaryAlgorithmParams.RANDOM_MUTATION){
			//else swap two and two and evaluate each switch until better
			for (int i = 0; i < genome.length; i++) {
				/*j = i because no need to check swaps already checked*/
				for (int j = i; j < genome.length - i; j++) {
					Utilities.swap(genome, i, j);
					if(FitnessModule.tripCost(genome) > this.fitness){
						calculateFitness();
						return;
					}
					Utilities.swap(genome, i, j);	//Undo the attempt before continuing
				}
			}
		}
		//No improvement was found or random was selected, flip two randomly
		int[] randomPoints = Utilities.getRandomCrossoverPoints();
		Utilities.swap(genome, randomPoints[0], randomPoints[1]);
		calculateFitness();
	}
	
	public int[] getGenome(){
		return this.genome.clone();
	}
	

	void initializeRandomly(){
		genome = Graph.getRequiredElementsIDs().clone();
		Utilities.shuffle(genome);
	}

	@Override
	public int compareTo(Genotype otherGenotype) {
		if(this.fitness < otherGenotype.fitness){
			return -1;
		}else if(this.fitness > otherGenotype.fitness){
			return 1;
		}else{
			return 0;			
		}
	}
}
