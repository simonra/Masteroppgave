package evolutionaryAlgorithm;

import graph.Graph;

public class Genotype implements Comparable<Genotype>{
	
	public Genotype(Graph graph){
		initializeRandomly(graph);
	}
	
	public Genotype(int[] genome){
//		this.genome = new int[genome.length];
//		System.arraycopy(genome, 0, this.genome, 0, genome.length);
		this.genome = genome;
	}
	
	
	int[] genome;
	double finess;
	/**Phenotype: genome with trip delimiters?*/
	

	void initializeRandomly(Graph graph){
		genome = graph.getRequiredElementsIDs();
		Utilities.shuffle(genome);
	}

	@Override
	public int compareTo(Genotype otherGenotype) {
		if(this.finess < otherGenotype.finess){
			return -1;
		}else if(this.finess > otherGenotype.finess){
			return 1;
		}else{
			return 0;			
		}
	}
}
