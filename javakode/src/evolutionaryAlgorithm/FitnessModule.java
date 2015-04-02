package evolutionaryAlgorithm;

import java.util.ArrayList;

import parameterFiles.EvolutionaryAlgorithmParams;

import graph.FloydWarshall;
import graph.Graph;

public class FitnessModule {
	
	
	/**Takes a trip as a sequence of tasks, and returns the cost of traversing the trip
	 * using one uncapacitated vehicle. Useful for evaluating costs of each vehicle in
	 * a multi-vehicle tour, and for evaluating a grand tour if there is only one single
	 * vehicle.*/
	public static double tripCost(int[] trip){
		double cost = 0;
		double sumOfDemandTimesTripNumber = 0;

		cost += FloydWarshall.distance(Graph.getDeoptNodeIndex(), trip[0])
				+ Graph.getElementByID(trip[0]).getServicingCost();
		sumOfDemandTimesTripNumber += 1 * Graph.getElementByID(trip[0]).getDemand();
		for (int i = 1; i < trip.length; i++) {
			cost += FloydWarshall.distance(trip[i - 1], trip[i])
					+ Graph.getElementByID(trip[i]).getServicingCost();
			sumOfDemandTimesTripNumber += (i+1)*Graph.getElementByID(trip[i]).getDemand(); //Add 1 because number of trips is 1 indexed due to math
		}
		cost += FloydWarshall.distance(trip[trip.length - 1], Graph.getDeoptNodeIndex());

		/**This is the slope of the demand throughout the trip.
		 * Used to determine if prioritized tasks are handeled
		 * early on or late in the trip*/
		double demandSlope = (sumOfDemandTimesTripNumber - Graph.averageNumberOfRequiredElements*Graph.averageDeamnd)/Graph.varianceOfRequiredElements;
		/*If the slope is positive we are handeling increasingly 
		 * more demanded tasks, which is bad, hence penalize.*/
		if(EvolutionaryAlgorithmParams.penalizeDemandOutOfOrder && demandSlope > 0){
			cost += demandSlope * Graph.averageDeamnd;	//This might be a sin against the admissibility of the heuristic, but then again we don't really have an absolute measure of demand in terms of cost anywat...
		}

		return cost;
	}
	
	
	public static ArrayList<int[]> tripsSplittedFromTour(double[] costs, int[] predecessors, Genotype genotype){
		ArrayList<int[]> ListOfTrips = new ArrayList<>();	//L in the papers
		int j = genotype.genome.length;	//The number of tasks in the problem
		int i;
		do {
			i = predecessors[j];
			int[] currentTrip = new int[j - (i + 1)];	//Trip T in the papers
			for (int k = i + 1; k < j; k++) {
				currentTrip[k-(i + 1)] = genotype.genome[k];
			}
			ListOfTrips.add(0, currentTrip);
			j = i;
		} while (i != 0);
		
		return ListOfTrips;
	}
	
	public static void split(Genotype genotype){
		/**Cost of going from depot to each task in the auxiliary graph.
		 * The +1 is added so that the depot node can be the 0th element
		 * used in this representation.*/
		double[] V = new double[Graph.numberOfRequiredElements + 1];
		/**The list of predecessors in the */
		int[] P = new int[V.length];
		V[0] = 0;	//The cost of going to the depot is 0
		P[0] = 0;	//The predecessor of the depot is the depot
		for (int i = 1; i < V.length; i++) {
			V[i] = Double.POSITIVE_INFINITY;	//Initialize the cost of all other elements as infinity
		}
		
		/**The index of the depot node*/
		int depot = Graph.getDeoptNodeIndex();
		/**Iterator used to iterate over the remaining tasks.*/
		int j;
		/**The demand collected in the current trip*/
		double load;
		/**The distance/cost covered in the current trip*/
		double cost;
		
		for (int i = 1; i < V.length; i++) {
			j = i;
			load = 0;
			cost = 0;
			
			do {
				//Update load to include the load of servicing the current element
				load += Graph.getElementByID(genotype.genome[j-1]).getDemand();
				//Update cost
				/*Remember to subtract 1 more than the general algorithm
				 * when accessing 0-indexed graph elements (i.e. not V and
				 * P), because they don't have the depot node
				 * as the 0th element*/
				if( i == j ){
					cost = FloydWarshall.distance(depot, genotype.genome[i-1])
							+ Graph.getElementByID(genotype.genome[i-1]).getServicingCost()
							+ FloydWarshall.distance(genotype.genome[i-1], depot);
				}else{
					cost = cost - FloydWarshall.distance(genotype.genome[j-2], depot)
							+ FloydWarshall.distance(genotype.genome[j-2], genotype.genome[j-1])
							+ Graph.getElementByID(genotype.genome[j-1]).getServicingCost()
							+ FloydWarshall.distance(genotype.genome[j-1], depot);
				}
				//If load and cost are manageable, update V and P
				if ( (load <= Graph.vehicleCapacity) && (V[i-1] + cost < V[j]) ) {
					V[j] = V[i-1] + cost;
					P[j] = i - 1;
				}
				j++;
			} while ( (j < V.length) && (load < Graph.vehicleCapacity) );
		}//End for
		
		/*The sum of costs of trips is the cost of the last element in 
		 * the auxiliary graph, can be interpreted as the cost of the 
		 * shortest path through the auxiliary graph.*/ 
		genotype.fitness = V[V.length - 1];
	}//End split
	
}
