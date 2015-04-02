package parameterFiles;

public class EvolutionaryAlgorithmParams {
	public enum ParentSelection{
		FitnessProportionateSelection, UniformSelection, TournamentSelection;
	}
	
	public static final int POPULATION_SIZE = 200;
	public static final int NUMBER_OF_CROSSOVER_PAIRS = 70;
	public static final boolean NO_SPLIT_ONLY_TOUR = true; 
	//mut-chance: always there?
	public static final boolean PENALIZE_DEMAND_OUT_OF_ORDER = true;
	public static final boolean RANDOM_MUTATION = false;
	public static ParentSelection PARENT_SELECTION = ParentSelection.UniformSelection;
	/**Number of genomes in a tournament if the parent selection is done tournament style*/
	public static final int TOURNAMENT_SIZE = 5;
	/**Likelyhood of best best individual being chosen in a tournament selection*/
	public static final double TOURNAMEN_SELECTION_PROBABILITY = 0.8;
}
