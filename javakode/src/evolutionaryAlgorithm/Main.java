package evolutionaryAlgorithm;

import graph.FloydWarshall;
import graph.Graph;

public class Main {
	
	
	
	
	
	public static void main(String[] args) {
		Graph.initialize();
		FloydWarshall.doFloydWarshall();
		Utilities.init();
		EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm();
		ea.run();
	}
}
