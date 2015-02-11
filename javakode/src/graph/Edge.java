package graph;

public class Edge implements ElementProperties, Cost{

	String ID;
	double servicingCost;
	double passThroughCost;
	double demand;
	boolean isRequired;

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
}
