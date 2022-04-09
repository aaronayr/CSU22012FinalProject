package tripSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TripLibrary {

	public String[] headers;
	public ArrayList<String[]> allTrips;
	public ArrayList<trip> [][][] allTimes;
	
//	public class trip
//	{
//		int hour;
//		int minute;
//		int second;
//	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		TripLibrary timetable = new TripLibrary();
		timetable.allTrips = new ArrayList<String[]>();
		timetable.allTimes = new ArrayList[24][60][60];
		for(int i = 0; i < 24; i++)
		{
			for (int j = 0; j < 60; j++)
			{
				for (int k = 0; k < 60; k++)
				{
					timetable.allTimes[i][j][k] = new ArrayList<trip>();
				}
			}
		}
		
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("stop_times.txt"));
			timetable.headers = input.readLine().split(",");
			
			String currentTime = "";
			while ((currentTime = input.readLine()) != null)
			{
				String[] timeDetails = currentTime.split(",");
				timeDetails[1] = timeDetails[1].substring(1, timeDetails[1].length());
				timeDetails[2] = timeDetails[2].substring(1, timeDetails[2].length());
				timetable.allTrips.add(timeDetails);
			}
			
			for (int i = 0; i < timetable.allTrips.size(); i++)
			{
				String[] time = timetable.allTrips.get(i);
				trip currentTrip = new trip(time[1], i);
				int hour = currentTrip.hours;
				int minute = currentTrip.minutes;
				int second = currentTrip.seconds;
				timetable.allTimes[hour][minute][second].add(currentTrip);
				
			}
			
			System.out.println(Arrays.toString(timetable.headers));
			System.out.println(Arrays.toString(timetable.allTrips.get(0)));
			//String[] requestedTime = new String[3];
			
			
			input.close();
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
		
		
	}

}
