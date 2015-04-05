package parameterFiles;

public class EvolutionaryAlgorithmParams {
	public enum ParentSelection{
		FITNESS_PROPORTIONATE_SELECTION, UNIFORM_SELECTION, TOURNAMENT_SELECTION;
	}
	public enum fitnessType{
		GRAND_TOUR, SPLITTED;
	}
	public enum AdultSelection{
		FULL_REPLACEMENT, ELITIST_MIXING, OVERPRODUCTION;
	}
	
	/**Set to Long.MAX_VALUE for in practice infinite runtime*/
	public static final long MAX_GENERATIONS = Long.MAX_VALUE;
//	public static final long MAX_GENERATIONS = 1000;
	public static final long MAX_GENERATIONS_WITHOUT_CHANGE = 50000;
	
	public static final int POPULATION_SIZE = 200;
	public static final int NUMBER_OF_CROSSOVER_PAIRS = 100;
	public static fitnessType FINTESS_TYPE = fitnessType.GRAND_TOUR;
	//mut-chance: always there?
	public static final boolean PENALIZE_DEMAND_OUT_OF_ORDER = true;
	/**Whether mutation should be random, or we should use the memetic approach*/
	public static final boolean RANDOM_MUTATION = false;
	/**What kind of parent selection should be performed when selecting who gets to mate*/
	public static ParentSelection PARENT_SELECTION = ParentSelection.UNIFORM_SELECTION;
	/**What kind of adult selection should be performed when one decides what individuals should survive till the next generation.*/
	public static AdultSelection ADULT_SELECTION = AdultSelection.FULL_REPLACEMENT;
	/**Number of genomes in a tournament if the parent selection is done tournament style*/
	public static final int TOURNAMENT_SIZE = 5;
	/**Likelyhood of best best individual being chosen in a tournament selection*/
	public static final double TOURNAMEN_SELECTION_PROBABILITY = 0.8;
	
	/**Given in number of generations between each time to log*/
	public static final int GENERATION_LOGGING_FREQUENCY = 100;
}
