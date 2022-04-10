package shortestPath;

public class stop {
	public int index;
	public int stopNumber;
	public double cost;
	
	public stop(int index, int stopNumber)
	{
		this.index = index;
		this.stopNumber = stopNumber;
		cost = Double.MAX_VALUE;
	}
}
