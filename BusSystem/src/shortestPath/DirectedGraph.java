package shortestPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectedGraph {
	public static int numStops = 0;
	public static ArrayList<DirectedEdge>[] edgeList;
	public static ArrayList<stop> stops;
	
	@SuppressWarnings("unchecked")
	public void generateGraph()
	{
		stops = new ArrayList<stop>();
		
		try 
		{
			BufferedReader stopsFile = new BufferedReader(new FileReader("stops.txt"));
			
			stopsFile.readLine();
			String currentLine ="";
			int index = 0;
			while ((currentLine = stopsFile.readLine()) != null) 
			{
				String[] stopDetails = currentLine.split(",");
				stopDetails[2] = swap(stopDetails[2]);
				stop currentStop = new stop(index, Integer.parseInt(stopDetails[0]), stopDetails[2]);
				stops.add(currentStop);
				index++;
			}
			
			numStops = stops.size();
			edgeList = new ArrayList[numStops];
			for (int i = 0; i < numStops; i++)
			{
				edgeList[i] = new ArrayList<DirectedEdge>();
			}
			stopsFile.close();
			
			BufferedReader stopTimesFile = new BufferedReader(new FileReader("stop_times.txt"));
			
			stopTimesFile.readLine();
			int currentTrip = -1;
			int prevId = -1;
			while ((currentLine = stopTimesFile.readLine()) != null)
			{
				String[] edgeDetails = currentLine.split(",");
				if (Integer.parseInt(edgeDetails[0]) == currentTrip)
				{
					DirectedEdge currentEdge = new DirectedEdge(prevId, Integer.parseInt(edgeDetails[3]), 1);
//					int stop = stops.indexOf(prevId);
					stop currentStop = findStop(prevId);
					edgeList[currentStop.index].add(currentEdge);
				}
				currentTrip = Integer.parseInt(edgeDetails[0]);
				prevId = Integer.parseInt(edgeDetails[3]);
			}
			stopTimesFile.close();
			
			BufferedReader transfersFile = new BufferedReader(new FileReader("transfers.txt"));
			
			transfersFile.readLine();
			while ((currentLine = transfersFile.readLine()) != null)
			{
				String[] edgeDetails = currentLine.split(",");
				boolean standardTransfer = edgeDetails[2].equals("0");
				double cost = 2;
				if (!standardTransfer) cost = (double) Integer.parseInt(edgeDetails[3]) / 100;
				
				DirectedEdge currentEdge = new DirectedEdge(Integer.parseInt(edgeDetails[0]), Integer.parseInt(edgeDetails[1]), cost);
//				int stop = stops.indexOf(Integer.parseInt(edgeDetails[0]));
				stop currentStop = findStop(Integer.parseInt(edgeDetails[0]));
				edgeList[currentStop.index].add(currentEdge);
			}
			transfersFile.close();
			
//			for (DirectedEdge e : Network.edgeList[0])
//			{
//				System.out.println(e.stop);
//				System.out.println(e.destination);
//				System.out.println(e.cost);
//				System.out.println("");
//			}
			
		}
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
	}
	
	public static String swap(String busStop)
	{
		if (busStop.indexOf("B ") == 1 || busStop.indexOf("FLAGSTOP") == 0) 
		{
			int space = busStop.indexOf(" ");
			String prefix = busStop.substring(0, space);
			String other = busStop.substring(space+1, busStop.length());
			busStop = other + " " + prefix;
		}
		return busStop;
	}
	
	public static stop findStop(int currentstopNumber)
	{
		stop answer = new stop(-1, -1, "");
		for (stop c : stops)
		{
			if (c.stopNumber == currentstopNumber) 
			{
				answer = c;
				break;
			}
		}
		return answer;
	}
}
