package shortestPath;

public class stop {
	public int index;
	public int stopNumber;
	public double cost;
	public String name;
	
	public stop(int index, int stopNumber, String name)
	{
		this.index = index;
		this.stopNumber = stopNumber;
		cost = Double.MAX_VALUE;
		this.name = name;
	}
}
