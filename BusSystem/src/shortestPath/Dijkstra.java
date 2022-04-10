package shortestPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dijkstra {
	public String[] headers = {"Stop", "Cost", "Total Cost"};
	
	public static void main(String[] args) {
		Dijkstra path = new Dijkstra();
		
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("stop_times.txt"));
			
			input.close();
		}
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
		System.out.println("success");
	}

}
