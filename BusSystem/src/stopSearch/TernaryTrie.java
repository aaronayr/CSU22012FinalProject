package stopSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TernaryTrie<Value> {
	public Node<Value> root;
	
	public static class Node<Value>
	{
		public char letter;
		public Value val;
		public Node<Value> less, equal, more;
		
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
	
	public void put(String key, Value val)
	{
		root = put(key, val, root, 0);
	}
	
	public Node<Value> put(String key, Value val, Node<Value> current, int level)
	{
		char nextLetter = key.charAt(level);
		if (current == null)
		{
			current = new Node<Value>();
			current.letter = nextLetter;
		}
		
		if (nextLetter < current.letter) current.less = put(key, val, current.less, level);
		else if (nextLetter > current.letter) current.more = put(key, val, current.more, level);
		else if (level < key.length() - 1) current.equal = put(key, val, current.equal, level + 1);
		else current.val = val;
		return current;
//		else
//		{
//			if (nextLetter < current.letter) current.less = put(key, val, current.less, level);
//			else if (nextLetter > current.letter) current.more = put(key, val, current.more, level);
//			
//		}
//		
//		if (level < key.length() - 1) current.equal = put(key, val, current.equal, level + 1);
//		else current.val = val;
//		return current;
	}
	
	public Value get(String key)
	{
		Node<Value> node = get(key, root, 0);
		if (node == null) return null;
		return node.val;
	}
	
	public Node<Value> get(String key, Node<Value> current, int level)
	{
		if (current == null) return null;
		char nextLetter = key.charAt(level);
		
		if (nextLetter < current.letter) return get(key, current.less, level);
		else if (nextLetter > current.letter) return get(key, current.more, level);
		else if (level < key.length() - 1) return get(key, current.equal, level + 1);
		else return current;
	}
	
	public static void main(String[] args) 
	{
		
		char test = '.';
		if (test <- 'a') System.out.println("success");
		else System.out.println("ruhroh");
		TernaryTrie<Integer> vancouverSystem = new TernaryTrie<Integer>();
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("stops.txt"));
			ArrayList<String[]> stops = new ArrayList<String[]>();
			String currentStop = input.readLine(); // gets rid of header line
			while ((currentStop = input.readLine()) != null) 
			{
				String[] stopDetails = currentStop.split(",");
				stopDetails[2] = swap(stopDetails[2]);
				stops.add(stopDetails);
			}
			System.out.println(Arrays.toString(stops.get(0)));
			
			for (int i = 0; i < stops.size() -1; i++)
			{
				vancouverSystem.put(stops.get(i)[2], i);
			}
			
			//System.out.println(vancouverSystem.get("HWY 10 FS 175 ST WB"));
			//System.out.println(vancouverSystem.get("HASTINGS ST FS HOLDOM AVE- WB"));
			System.out.println(stops.get(1)[2]);
			System.out.println(vancouverSystem.get("SHAUGHNESSY ST FS MCALLISTER AVE NB"));
			
			input.close();
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
	}

}
