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
			setFitness();
		}
		return fitness;
	}
	
	public void setFitness(){
		if(EvolutionaryAlgorithmParams.noSplitOnlyTour){
			this.fitness = FitnessModule.tripCost(genome);
		}
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
