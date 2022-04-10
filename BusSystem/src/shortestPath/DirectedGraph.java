package shortestPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectedGraph {
	public int numStops;
	public int numSegments;
	public ArrayList<DirectedEdge> edgeList;
	
	public static void main(String[] args)
	{
		DirectedGraph Network = new DirectedGraph();
		Network.edgeList = new ArrayList<DirectedEdge>();
		
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("stop_times.txt"));
			
			input.readLine();
			String currentLine ="";
			int currentTrip = -1;
			int prevId = -1;
			while ((currentLine = input.readLine()) != null)
			{
				String[] edgeDetails = currentLine.split(",");
				if (Integer.parseInt(edgeDetails[0]) == currentTrip)
				{
					DirectedEdge currentEdge = new DirectedEdge(prevId, Integer.parseInt(edgeDetails[3]), 1);
					Network.edgeList.add(currentEdge);
				}
				currentTrip = Integer.parseInt(edgeDetails[0]);
				prevId = Integer.parseInt(edgeDetails[3]);
			}
			input.close();
			
			BufferedReader input2 = new BufferedReader(new FileReader("transfers.txt"));
			
			input2.readLine();
			while ((currentLine = input2.readLine()) != null)
			{
				String[] edgeDetails = currentLine.split(",");
				boolean standardTransfer = edgeDetails[2].equals("0");
				double cost = 2;
				if (!standardTransfer) cost = (double) Integer.parseInt(edgeDetails[3]) / 100;
				DirectedEdge currentEdge = new DirectedEdge(Integer.parseInt(edgeDetails[0]), Integer.parseInt(edgeDetails[1]), cost);
				Network.edgeList.add(currentEdge);
			}
			input2.close();
			
			for (int i = 0; i < 100; i++)
			{
				DirectedEdge e = Network.edgeList.get(i);
				{
					System.out.println("stop: " + e.stop);
					System.out.println("destination: " + e.destination);
					System.out.println("cost: " + e.cost);
					System.out.println("");
				}
			}
			
			
		}
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
		System.out.println("success"); 
	}
}
