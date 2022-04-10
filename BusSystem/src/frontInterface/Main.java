package frontInterface;

import java.util.Scanner;

import shortestPath.*;
import stopSearch.*;
import tripSearch.*;

public class Main {

	public static void rules()
	{
		System.out.println("");
		System.out.println("We offer 3 services: \n"
				+ "1 - Finding the shortest route between two stops\n"
				+ "2 - Searching for a bus stop by its name\n"
				+ "3 - Search for all trips at an arrival time\n");
		System.out.println("When you want to quit, just say that you're done,\n"
				+ "If you ever want to hear these rules again, just ask for them");
		System.out.println("\n\n");
		System.out.println("Which option would you like to choose?");
	}
	public static void main(String[] args) {
		System.out.println("Hi, welcome to the Vancouver Public Transportation System Database.");
		Scanner input = new Scanner(System.in);
		rules();
		String s = input.nextLine().toUpperCase();
		
		while (!(s.contains("DONE") || s.contains("FINISHED") || s.contains("EXIT") || s.contains("LEAVE")))
		{
			System.out.println("");
			if ((s.contains("FIND") || s.contains("SHORTEST") || s.contains("ROUTE") || s.contains("STOPS")))
			{
				Dijkstra.q1(input);
			}
			else if ((s.contains("SEARCH") || s.contains("STOP") || s.contains("NAME")))
			{
				TernaryTrie.q2(input);
			}
			else if ((s.contains("ALL") || s.contains("TRIPS") || s.contains("ARRIVAL") || s.contains("TIME")))
			{
				TripLibrary.q3(input);
			}
			else if ((s.contains("RULES") || s.contains("RULE") || s.contains("HELP")))
			{
				rules();
			}
			else System.out.println("Sorry, I didn't quite get that");
			System.out.println("Which option would you like to choose?");
			s = input.nextLine().toUpperCase();
			
		}
		System.out.println("\n\nGoodbye!");
		
		
		
		input.close();
	}

}
