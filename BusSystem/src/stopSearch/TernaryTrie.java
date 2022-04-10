package stopSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TernaryTrie {
	public Node root;
	public String[] headers;
	public ArrayList<String[]> allStops;
	public static ArrayList<String> validStops;
	
	public static class Node
	{
		public char letter;
		public int val;
		public Node less, equal, more;
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
	
	public void put(String key, int val)
	{
		root = put(key, val, root, 0);
	}
	
	public Node put(String key, int val, Node current, int level)
	{
		char nextLetter = key.charAt(level);
		if (current == null)
		{
			current = new Node();
			current.letter = nextLetter;
		}
		
		if (nextLetter < current.letter) current.less = put(key, val, current.less, level);
		else if (nextLetter > current.letter) current.more = put(key, val, current.more, level);
		else if (level < key.length() - 1) current.equal = put(key, val, current.equal, level + 1);
		else current.val = val;
		return current;
	}
	
	public int val(String key)
	{
		Node node = val(key, root, 0);
		if (node == null) return -1;
		return node.val;
	}
	
	public Node val(String key, Node current, int level)
	{
		if (current == null) return null;
		char nextLetter = key.charAt(level);
		
		if (nextLetter < current.letter) return val(key, current.less, level);
		else if (nextLetter > current.letter) return val(key, current.more, level);
		else if (level < key.length() - 1) return val(key, current.equal, level + 1);
		else return current;
	}
	
	public void allVal(String key)
	{
		if (key.isBlank())
		{
			for (String[] stop : this.allStops)
			{
				validStops.add(stop[2]);
			}
			return;
		}
		allVal(key, root, 0);
	}
	
	public void allVal(String key, Node current, int level)
	{
		if (current == null) return;
		char nextLetter = key.charAt(level);
		
		if (nextLetter < current.letter) allVal(key, current.less, level);
		else if (nextLetter > current.letter) allVal(key, current.more, level);
		else if (level < key.length() - 1) allVal(key, current.equal, level + 1);
		else 
		{
			addNode(current);
			allVal(current.equal, level + 1);
		}
	}
	
	public void allVal(Node current, int level)
	{
		if (current ==  null) return;
		addNode(current);
		if (current.less != null) allVal(current.less, level);
		if(current.more != null) allVal(current.more, level);
		if(current.equal != null) allVal(current.equal, level + 1);

		else return;
		
	}
	
	public void addNode(Node n)
	{
		if (n.val != 0) 
		{
			int ValidStopval = n.val - 1;
			String ValidStopName = this.allStops.get(ValidStopval)[2];
			validStops.add(ValidStopName);
		}
	}
	
	public void print()
	{
		ArrayList<String> stops = validStops;
		if (stops.size() < 100)
		{
			Collections.sort(stops);
		}
		
		if (!stops.isEmpty())
		{
			for (String stop : stops)
			{
				int stopNumber = this.val(stop) - 1;
				if (stopNumber == -2) stopNumber = 8756;
				for (int i = 0; i < 9; i++)
				{
					if (!this.allStops.get(stopNumber)[i].isBlank())
					{
						System.out.println(this.headers[i] + ": " + this.allStops.get(stopNumber)[i]);
					}
				}
				System.out.println("");
				
			}
			System.out.println(stops.size() + " stops were found, they are listed above");
		}
		else System.out.println("No valid such stops exist");
		System.out.println("");
	}
	
	public static void q2(Scanner input)
	{
		System.out.println("What stop are you looking for?");
		TernaryTrie bus = new TernaryTrie();
		String inputtedName = input.nextLine();
		inputtedName = inputtedName.toUpperCase();
		
		try 
		{
			BufferedReader stopsFile = new BufferedReader(new FileReader("stops.txt"));
			bus.headers = stopsFile.readLine().split(",");
			bus.headers[0] = bus.headers[0].substring(3);
			bus.allStops = new ArrayList<String[]>();
			validStops = new ArrayList<String>();
			
			
			String currentStop = "";
			while ((currentStop = stopsFile.readLine()) != null) 
			{
				String[] stopDetails = currentStop.split(",");
				stopDetails[2] = swap(stopDetails[2]);
				bus.allStops.add(stopDetails);
			}
			
			for (int i = 0; i < bus.allStops.size() -1; i++)
			{
				bus.put(bus.allStops.get(i)[2], i+1);
			}
			bus.allVal(inputtedName);
			bus.print();
			
			stopsFile.close();
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
	}

}
