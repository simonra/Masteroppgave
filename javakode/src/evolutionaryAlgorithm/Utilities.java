package evolutionaryAlgorithm;

public class Utilities {

	public Genotype[] crossover(Genotype firstParent, Genotype secondParent,
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
		for (int i = 0; i < rangeToBeCopied; i++) {
			indexToCopy = firstCrossoverPoint + i;
			if (rangeWraps) {
				indexToCopy %= genomeLength;
			}
			firstChildGenome[indexToCopy] = firstParent.genome[indexToCopy];
			secondChildGenome[indexToCopy] = secondParent.genome[indexToCopy];
		}
		
		//Copy the rest
		for (int i = 0; i < genomeLength; i++) {
			indexToCopy = (secondCrossoverPoint + i) % genomeLength;
			if(!doesArrayContainInt(firstChildGenome, secondParent.genome[indexToCopy])){
				
			}
		}
		

		firstChild = new Genotype(firstChildGenome);
		secondChild = new Genotype(secondChildGenome);
		Genotype[] generatedChildren = { firstChild, secondChild };
		return generatedChildren;
	}
	
	
	
	
	
	
	boolean doesArrayContainInt(int[] arrayToBeSearhced, int numberToCheckFor){
		for (int i = 0; i < arrayToBeSearhced.length; i++) {
			if(arrayToBeSearhced[i] == numberToCheckFor){
				return true;
			}
		}
		return false;
		
	}
	
	
}























































