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
	
	
	int[] genome;
	double fitness = -1.0;
	
	public double getFitness(){
		if(fitness == -1.0){
			updateFitness();
		}
		return fitness;
	}
	
	public void updateFitness(){
		if(EvolutionaryAlgorithmParams.FINTESS_TYPE == EvolutionaryAlgorithmParams.fitnessType.GRAND_TOUR){
			this.fitness = FitnessModule.tripCost(genome);
		}else if(EvolutionaryAlgorithmParams.FINTESS_TYPE == EvolutionaryAlgorithmParams.fitnessType.SPLITTED){
			FitnessModule.split(this);
		}
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}
	
	public void mutate(){
		if(!EvolutionaryAlgorithmParams.RANDOM_MUTATION){
			//else swap two and two and evaluate each switch until better
			for (int i = 0; i < genome.length; i++) {
				/*j = i because no need to check swaps already checked*/
				for (int j = i; j < genome.length - i; j++) {
					Utilities.swap(genome, i, j);
					if(FitnessModule.tripCost(genome) > this.fitness){
						updateFitness();
						return;
					}
					Utilities.swap(genome, i, j);	//Undo the attempt before continuing
				}
			}
		}
		//No improvement was found or random was selected, flip two randomly
		int[] randomPoints = Utilities.getRandomCrossoverPoints();
		Utilities.swap(genome, randomPoints[0], randomPoints[1]);
		updateFitness();
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
