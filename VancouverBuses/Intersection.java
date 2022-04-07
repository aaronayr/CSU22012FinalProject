import java.util.ArrayList;

public class Intersection {
	
	double distTo = -1;
	int edgeTo = -1;
	boolean inQueue = true;
	ArrayList<Integer> forwardIntersections = new ArrayList<Integer>();
	ArrayList<Double> forwardDistTo = new ArrayList<Double>();
	
	public Intersection(int edgeTo)
	{
		this.edgeTo = edgeTo;
	}
	public void setDistTo(double distTo)
	{
		this.distTo = distTo;
	}
	
	public void setEdgeTo(int edgeTo)
	{
		this.edgeTo = edgeTo;
	}
	
	public void dequeue()
	{
		inQueue = false;
	}
}
