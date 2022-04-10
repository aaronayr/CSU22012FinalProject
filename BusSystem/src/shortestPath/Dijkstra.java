package shortestPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dijkstra {
	public String[] headers = {"Stop", "Cost", "Total Cost"};
	public int source;
	public int destination;
	public DirectedGraph City;
	public DirectedEdge[] edgeTo;
	public ArrayList<stop> stopQueue;
	
	
	public Dijkstra(DirectedGraph City)
	{
		this.City = City;
		edgeTo = new DirectedEdge[DirectedGraph.stops.size()];
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
	}
	
	public static stop findStop(int currentStopNumber)
	{
		stop answer = new stop(-1, -1, "");
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
	
	public static int stopExists(String potentialStop)
	{
		int answer = -1;
		potentialStop = potentialStop.toUpperCase();
		for (stop s: DirectedGraph.stops) 
		{
			if (s.name.equals(potentialStop)) answer = s.stopNumber;
		}
		return answer;
	}
	
	public static void q1(Scanner input) {
		
		System.out.println("Where are you coming from?");
		String startingPoint = input.nextLine();
		System.out.println("And where are you going to?");
		DirectedGraph Vancouver = new DirectedGraph();
		Vancouver.generateGraph();
		String endPoint = input.nextLine();
		int sourceId = stopExists(startingPoint);
		int destinationId = stopExists(endPoint);
		System.out.println("");
		if (sourceId != -1 && destinationId != -1)
		{
			Dijkstra path = new Dijkstra(Vancouver);
			stop sourceStop = findStop(sourceId);
			path.stopQueue = new ArrayList<stop>();
			path.setSource(sourceStop);
			path.relaxVertices();
			
			stop destination = findStop(destinationId);
			stop previousStop = destination;
			
			if (!sourceStop.equals(previousStop))
			{
				System.out.println("Destination: " + previousStop.name);
				System.out.println("");
				
				int priorStopNumber = path.edgeTo[previousStop.index].stop;
				stop priorStop = findStop(priorStopNumber);
				while (!priorStop.equals(sourceStop))
				{
					System.out.println("--" + priorStop.name);
					System.out.println("");
					previousStop = priorStop;
					priorStopNumber = path.edgeTo[previousStop.index].stop;
					priorStop = findStop(priorStopNumber);
				}
				
				System.out.println("Starting Point: " + sourceStop.name);
				System.out.println(" ")
				;
				System.out.println("Cost of Trip: " + destination.cost);
			}
			else System.out.println("Sorry, the stops you have inputted are not valid");
		}
		else System.out.println("Sorry, the stops you have inputted are not valid");
		System.out.println("");
	}

}
