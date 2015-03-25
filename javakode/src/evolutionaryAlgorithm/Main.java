package evolutionaryAlgorithm;

import java.util.Arrays;

public class Main {
	
	
	
	
	
	public static void main(String[] args) {
		int[] firstGenome = {1, 2, 3, 4, 5, 6};
		int[] secondGenome = {1, 4, 5, 6, 2, 3};
		Genotype parent1 = new Genotype(firstGenome);
		Genotype parent2 = new Genotype(secondGenome);
		
		System.out.println(Arrays.toString(firstGenome));
		System.out.println(Arrays.toString(secondGenome));
		
		System.out.println(Arrays.toString(parent1.genome));
		System.out.println(Arrays.toString(parent2.genome));
		
		
		Genotype[] children = Utilities.crossover(parent1, parent2, 1, 3);
		
		System.out.println(Arrays.toString(children[0].genome));
		System.out.println(Arrays.toString(children[1].genome));
	}
}
