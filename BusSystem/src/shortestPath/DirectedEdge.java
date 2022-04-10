package shortestPath;

public class DirectedEdge {
	public int stop;
	public int destination;
	public double cost;
	
	public DirectedEdge(int stop, int destination, double cost)
	{
		this.stop = stop;
		this.destination = destination;
		this.cost = cost;
	}
}
