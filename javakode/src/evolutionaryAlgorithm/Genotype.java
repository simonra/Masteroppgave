package evolutionaryAlgorithm;

public class Genotype {
	
	public Genotype(){
		
	}
	
	public Genotype(int[] genome){
//		this.genome = new int[genome.length];
//		System.arraycopy(genome, 0, this.genome, 0, genome.length);
		this.genome = genome;
	}
	
	
	int[] genome;
	double finess;
	/**Phenotype: genome with trip delimeters?*/
	
	/*har lyst på: indeksen i requiredWhatever*/
	
	/*TODO:
	 * have representation
	 * store fitness
	 * have mating function?
	 * have phenotype (route)-generating function?
	 * have fitness evaluation function?
	 * implement comparator so that comparing genotypes becomes neater
	 * */
}
