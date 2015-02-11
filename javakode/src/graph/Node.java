package graph;

public class Node implements ElementProperties, Cost{

	String ID;
	double servicingCost;
	double passThroughCost;
	double demand;
	boolean isRequired;

	public Node(String elementId, double demand, double servicingCost, boolean isRequired) {
		this.ID = elementId;
		this.demand = demand;
		this.servicingCost = servicingCost;
		this.passThroughCost = 0;
		this.isRequired = isRequired;
	}

	@Override
	public double getServicingCost() {
		return servicingCost;
	}

	@Override
	public double getPassThroughCost() {
		return passThroughCost;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public boolean getIsRequired() {
		return isRequired;
	}

	@Override
	public double getDemand() {
		return demand;
	}
	
	
	public String toString(){
		String output = "\n";
		output += "Node ID: " + ID + "\t";
		output += "Servicing Cost: " + servicingCost + "\t";
		output += "Pass Through Cost: " + passThroughCost + "\t";
		output += "Demand: " + demand + "\t";
		output += "Is required: " + isRequired;
		return output;
	}
}
