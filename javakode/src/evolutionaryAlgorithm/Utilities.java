package evolutionaryAlgorithm;

import java.util.Random;

public class Utilities {
	
	static Random random;
	/** Code from method java.util.Collections.shuffle();
	 * as reproduced on http://stackoverflow.com/a/19333201*/
	public static void shuffle(int[] array){
		if(random == null){
			random = new Random();
		}
		int count = array.length;
		for (int i = 0; i < count; i++) {
			swap(array, i - 1, random.nextInt(i));
		}
	}
	private static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = array[temp];
	}

	public static Genotype[] crossover(Genotype firstParent, Genotype secondParent,
			int firstCrossoverPoint, int secondCrossoverPoint) {
		Genotype firstChild;
		Genotype secondChild;
		int genomeLength = firstParent.genome.length;
		int[] firstChildGenome = new int[genomeLength];
		int[] secondChildGenome = new int[genomeLength];
		
		int rangeToBeCopied;
		boolean rangeWraps;
		if (secondCrossoverPoint > firstCrossoverPoint) {
			rangeToBeCopied = secondCrossoverPoint - firstCrossoverPoint;
			rangeWraps = false;
		}else{
			rangeToBeCopied = genomeLength - (firstCrossoverPoint - secondCrossoverPoint);
			rangeWraps = true;
		}
		
		int indexToCopy;
		//Copy the part of the parent that should be copied entirely as-is
		for (int i = 0; i <= rangeToBeCopied; i++) {
			indexToCopy = firstCrossoverPoint + i;
			if (rangeWraps) {
				indexToCopy %= genomeLength;
			}
			firstChildGenome[indexToCopy] = firstParent.genome[indexToCopy];
			secondChildGenome[indexToCopy] = secondParent.genome[indexToCopy];
		}
		
		//Copy the rest
		int firstChildIndexToPlace = (secondCrossoverPoint + 1) % genomeLength;
		int secondChildIndexToPlace = (secondCrossoverPoint + 1) % genomeLength;
		for (int i = 0; i < genomeLength; i++) {
			indexToCopy = (secondCrossoverPoint + 1 + i) % genomeLength;
			if(!doesArrayContainInt(firstChildGenome, secondParent.genome[indexToCopy])){
				firstChildGenome[firstChildIndexToPlace] = secondParent.genome[indexToCopy];
				firstChildIndexToPlace = (firstChildIndexToPlace + 1) % genomeLength;
			}
			if(!doesArrayContainInt(secondChildGenome, firstParent.genome[indexToCopy])){
				secondChildGenome[secondChildIndexToPlace] = firstParent.genome[indexToCopy];
				secondChildIndexToPlace = (secondChildIndexToPlace + 1) % genomeLength;
			}
		}
		
		//TODO: update fitness here?

		firstChild = new Genotype(firstChildGenome);
		secondChild = new Genotype(secondChildGenome);
		Genotype[] generatedChildren = { firstChild, secondChild };
		return generatedChildren;
	}
	
	
	
	
	
	
	static boolean doesArrayContainInt(int[] arrayToBeSearhced, int numberToCheckFor){
		for (int i = 0; i < arrayToBeSearhced.length; i++) {
			if(arrayToBeSearhced[i] == numberToCheckFor){
				return true;
			}
		}
		return false;
		
	}
	
	
	
	
	
}























































