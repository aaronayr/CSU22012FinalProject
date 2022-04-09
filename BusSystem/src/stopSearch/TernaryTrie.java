package stopSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TernaryTrie {
	public Node root;
	String[] headers;
	ArrayList<String[]> stops;
	
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
	
	public int get(String key)
	{
		Node node = get(key, root, 0);
		if (node == null) return -1;
		return node.val;
	}
	
	public Node get(String key, Node current, int level)
	{
		if (current == null) return null;
		char nextLetter = key.charAt(level);
		
		if (nextLetter < current.letter) return get(key, current.less, level);
		else if (nextLetter > current.letter) return get(key, current.more, level);
		else if (level < key.length() - 1) return get(key, current.equal, level + 1);
		else return current;
	}
	
	public void print(String stop)
	{
		int stopNumber = this.get(stop);
		for (int i = 0; i < 9; i++)
		{
			if (this.stops.get(stopNumber)[i] != null)
			{
				System.out.println(this.headers[i] + ": " + this.stops.get(stopNumber)[i]);
			}
		}
		
		
	}
	
	public static void main(String[] args) 
	{
		TernaryTrie Bus = new TernaryTrie();
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("stops.txt"));
			Bus.stops = new ArrayList<String[]>();
			Bus.headers = input.readLine().split(",");
			Bus.headers[0] = Bus.headers[0].substring(3);
			
			String currentStop = "";
			while ((currentStop = input.readLine()) != null) 
			{
				String[] stopDetails = currentStop.split(",");
				stopDetails[2] = swap(stopDetails[2]);
				Bus.stops.add(stopDetails);
			}
			
			for (int i = 0; i < Bus.stops.size() -1; i++)
			{
				Bus.put(Bus.stops.get(i)[2], i);
			}
			
			Bus.print("SHAUGHNESSY ST FS MCALLISTER AVE NB");
			
			input.close();
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
	}

}
