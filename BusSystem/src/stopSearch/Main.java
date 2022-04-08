package stopSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static final int R = 256;
	
	public static class Node<Value>
	{
		public char c;
		public Node<Value> less, equal, more;
		public Value val;
	}
	
	public static void swap(String busStop)
	{
		if (busStop.indexOf("B ") == 1 || busStop.indexOf("FLAGSTOP") == 0) 
		{
			int space = busStop.indexOf(" ");
			String prefix = busStop.substring(0, space);
			String other = busStop.substring(space+1, busStop.length());
			busStop = other + " " + prefix;
		}
	}
	
	public static void main(String[] args) 
	{
		String x = "FLAGSTOP example street";
		swap(x);
		
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("stops.txt"));
			ArrayList<String[]> stops = new ArrayList<String[]>();
			String currentStop = input.readLine(); // gets rid of header line
			while ((currentStop = input.readLine()) != null) 
			{
				String[] stopDetails = currentStop.split(",");
				swap(stopDetails[2]);
				stops.add(stopDetails);
			}
			System.out.println(Arrays.toString(stops.get(0)));
						
			input.close();
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
	}

}
