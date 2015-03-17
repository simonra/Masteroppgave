package evolutionaryAlgorithm;

import graph.FloydWarshallInterpretation;
import graph.Graph;

public class FitnessModule {

	/*TODO:
	 * Have a function that takes in a genotype and returns it's fitness
	 * If not preprocessed, calculate a fitness for each element of the graph,
	 * taking some of the following into considerations:
	 * 	length
	 * 	speed limit
	 * 	width
	 * 	curvature?
	 * 	slope?
	 * 	classification?
	 * 		eg municipal, regional, national, freeway, etc*/
	
	public void splitTourAmongVehiclesAndCalculateCost(Genotype genotype, Graph graph, FloydWarshallInterpretation floydWarshall){
		int numberOfVehiclesAvailable;
		int numberOfVehiclesUsed; //Is the same as number of trips
		int numberOfRequiredGraphElements = graph.numberOfRequiredElements;
		double demandCollectedInCurretnTrip; //A competitive refers to this as load
		double distanceOfCurrentTrip; // A competitive refers to this as cost
		double[] costOfGoingToTasks = new double[numberOfRequiredGraphElements];
		
		//TODO: Determine whether the following statement should be a ternary
		if(graph.numberOfVehicles == -1){
			//If the number of vehicles is unbounded use 1 vehicle for now
			numberOfVehiclesAvailable = 1;
		}else{
			//Otherwise use the number supplied in the gaph
			numberOfVehiclesAvailable = graph.numberOfVehicles;
		}
		
		for (int i = 0; i < costOfGoingToTasks.length; i++) {
			costOfGoingToTasks[i] = Double.POSITIVE_INFINITY;
		}
		
		for (int i = 0; i < numberOfRequiredGraphElements; i++) {
			demandCollectedInCurretnTrip = 0;
			distanceOfCurrentTrip = 0;
			
		}
		
		
	}
}
