package shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Dijkstra {
	public String[] headers = {"Stop", "Cost", "Total Cost"};
	public int source;
	public int destination;
	public DirectedGraph City;
//	public double[] costForStop;
	public DirectedEdge[] edgeTo;
	public ArrayList<stop> stopQueue;
	
	
	public Dijkstra(DirectedGraph City)
	{
		this.City = City;
//		costForStop = new double[DirectedGraph.stops.size()];
		edgeTo = new DirectedEdge[DirectedGraph.stops.size()];
		
//		for (int currentStop = 0; currentStop < costForStop.length; currentStop++)
//		{
//			costForStop[currentStop] = -1;
//		}
	}
	
	public void setSource(stop source)
	{
		source.cost = 0;
		stopQueue.add(source);
	}
	
	public void relaxVertices ()
	{
		while (!stopQueue.isEmpty())
		{
			sortQueue();
			stop currentStop = returnClosest();
			for (DirectedEdge segment: DirectedGraph.edgeList[currentStop.index])
			{
				stop destination = findStop(segment.destination);
				if (destination.cost > currentStop.cost + segment.cost)
				{
					destination.cost = currentStop.cost + segment.cost;
					edgeTo[destination.index] = segment;
					if (!stopQueue.contains(destination)) stopQueue.add(destination);
				}
//				int destination = DirectedGraph.findStop(segment.destination).index;
//				int stop = DirectedGraph.findStop(segment.stop).index;
//				if (costForStop[destination] > costForStop[stop] + segment.cost)
//				{
//					costForStop[destination] = costForStop[stop] + segment.cost;
//					edgeTo[destination] = segment;
//					if (!stopQueue.contains(DirectedGraph.findStop(segment.destination))) stopQueue.add(DirectedGraph.findStop(destination));
//				}
			}
		}
	}
	
	public stop returnClosest()
	{
		stop closest = stopQueue.get(0);
		stopQueue.remove(0);
		return closest;
	}
	
	public void sortQueue()
	{
		ArrayList<stop> temp = stopQueue;
		ArrayList<Double> tempCost = new ArrayList<Double>();
		stopQueue = new ArrayList<stop>();
		
		for (int i = 0; i < temp.size(); i++)
		{
			tempCost.add(temp.get(i).cost);
		}
		Collections.sort(tempCost);
		
		while (!temp.isEmpty())
		{
			for (int i = 0; i < temp.size(); i++)
			{
				if (temp.get(i).cost == tempCost.get(0))
				{
					stopQueue.add(temp.get(i));
					temp.remove(i);
					tempCost.remove(0);
					break;
				}
			}
		}
		
//		double[] costForStop = new double[queue.size()];
//		for (int i = 0; i < queue.size(); i++)
//		{
//			costForStop[i] = DirectedGraph.stops.get(queue.get(i));
//		}
//		Arrays.sort(costForStop);
//		for (int i = 0; i < queue.size(); i++)
//		{
//			queue.set(i, queue.indexOf(DirectedGraph.stops.indexOf(costForStop[i])));
//		}
	}
	
	public static stop findStop(int currentStopNumber)
	{
		stop answer = new stop(-1, -1);
		for (stop c : DirectedGraph.stops)
		{
			if (c.stopNumber == currentStopNumber) 
			{
				answer = c;
				break;
			}
		}
		return answer;
	}
	
	public static void main(String[] args) {
		DirectedGraph Vancouver = new DirectedGraph();
		Vancouver.generateGraph();
//		int source = 646;
//		int indexOfSource = Vancouver.stops.indexOf(source);
//		int destination = 378;
//		int indexOfDestination= Vancouver.stops.indexOf(destination);
		Dijkstra path = new Dijkstra(Vancouver);
		int source = 646;
		int destination = 378;
		path.stopQueue = new ArrayList<stop>();
		path.setSource(findStop(source));
		path.relaxVertices();
		
		stop d = findStop(destination);
		System.out.println(d.index);
		System.out.println(d.stopNumber);
		System.out.println(d.cost);
		System.out.println("");
		
		System.out.println("success");
	}

}
