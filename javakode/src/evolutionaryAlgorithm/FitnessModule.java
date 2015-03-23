package evolutionaryAlgorithm;

import graph.FloydWarshallInterpretation;
import graph.Graph;

public class FitnessModule {
	
	/**Takes a trip as a sequence of tasks, and returns the cost of traversing the trip
	 * using one uncapacitated vehicle. Useful for evaluating costs of each vehicle in
	 * a multi-vehicle tour, and for evaluating a grand tour if there is only one single
	 * vehicle.*/
	public double tripCost(int[] trip, FloydWarshallInterpretation floydWarshall, Graph graph){
		double cost = 0;
		cost += floydWarshall.distance(graph.getDeoptNodeIndex(), trip[0]);
		for (int i = 1; i < trip.length; i++) {
			cost += floydWarshall.distance(i - 1, i);
		}
		cost += floydWarshall.distance(trip[trip.length - 1], graph.getDeoptNodeIndex());
		return cost;
	}
	
	public void split(Genotype genotype, Graph graph, FloydWarshallInterpretation floydWarshall){
		/**Cost of going from depot to each task in the auxiliary graph.
		 * The +1 is added so that the depot node can be the 0th element
		 * used in this representation.*/
		double[] V = new double[graph.numberOfRequiredElements + 1];
		/**The list of predecessors in the */
		int[] P = new int[V.length];
		V[0] = 0;	//The cost of going to the depot is 0
		P[0] = 0;	//The predecessor of the depot is the depot
		for (int i = 1; i < V.length; i++) {
			V[i] = Double.POSITIVE_INFINITY;	//Initialize the cost of all other elements as infinity
		}
		
		/**The index of the depot node*/
		int depot = graph.getDeoptNodeIndex();
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
				load += graph.getElementByID(genotype.genome[j-1]).getDemand();
				//Update cost
				if( i == j ){
					cost = floydWarshall.distance(depot, genotype.genome[i-1])
							+ graph.getElementByID(genotype.genome[i-1]).getServicingCost()
							+ floydWarshall.distance(genotype.genome[i-1], depot);
				}else{
					cost = cost - floydWarshall.distance(genotype.genome[j-2], depot)
							+ floydWarshall.distance(genotype.genome[j-2], genotype.genome[j-1])
							+ graph.getElementByID(genotype.genome[j-1]).getServicingCost()
							+ floydWarshall.distance(genotype.genome[j-1], depot);
				}
				//If load and cost are manageable, update V and P
				if ( (load <= graph.vehicleCapacity) && (V[i-1] + cost < V[j]) ) {
					V[j] = V[i-1] + cost;
					P[j] = i - 1;
				}
				j++;
			} while ( (j < V.length) && (load < graph.vehicleCapacity) );
		}//End for
		
		//TODO: Calculate fitness(total cost) and feed it into the genotype
	}//End split
	
}
