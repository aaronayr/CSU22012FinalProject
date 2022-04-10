package tripSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TripLibrary {

	public String[] headers;
	public ArrayList<String[]> allTrips;
	public ArrayList<trip>[][][] allTimes;

	@SuppressWarnings("unchecked")
	public static void q3(Scanner input) {
		System.out.println("Input an arrival time 'hh:mm:ss:' - ");
		String inputtedTime = input.nextLine();
		boolean validTimeFormat = true;
		
		String[] rqTemp = inputtedTime.split(":");
		
		if (rqTemp.length != 3) validTimeFormat = false;
		
		int rqHours = -1;
		int rqMinutes = -1;
		int rqSeconds = -1;
		
		if (validTimeFormat)
		{
			try
			{
				rqHours = Integer.parseInt(rqTemp[0]);
				rqMinutes = Integer.parseInt(rqTemp[1]);
				rqSeconds = Integer.parseInt(rqTemp[2]);
			}
			catch (NumberFormatException e)
			{
				validTimeFormat = false;
			}
		}
		
		if (!(rqHours < 24 && rqMinutes < 60 && rqSeconds < 60
				&& rqHours >  -1 && rqMinutes > -1 && rqMinutes > -1
				&& ((rqSeconds == 0 ? (rqHours >  0 || rqMinutes > 0) : (true))))) // handles edge case of "00:00:00"
		{
			validTimeFormat = false;
		}
		
		if (validTimeFormat)
		{
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
				BufferedReader stopTimesFile = new BufferedReader(new FileReader("stop_times.txt"));
				timetable.headers = stopTimesFile.readLine().split(",");
				int columns = timetable.headers.length;
				
				String currentTime = "";
				while ((currentTime = stopTimesFile.readLine()) != null)
				{
					String[] timeDetails = currentTime.split(",");
					if (timeDetails.length == 8)
					{
						String[] temp = new String[9];
						for (int i = 0; i < 8; i++)
						{
							temp[i] = timeDetails[i];
						}
						temp[8] = " ";
						timeDetails = temp;
					}
					if (timeDetails[1].substring(0,1).equals(" ")) timeDetails[1] = timeDetails[1].substring(1, timeDetails[1].length());
					if (timeDetails[2].substring(0,1).equals(" ")) timeDetails[2] = timeDetails[2].substring(1, timeDetails[2].length());
						
					String[] potentialTime = timeDetails[1].split(":");
					if (Integer.parseInt(potentialTime[0]) < 24)
					{
						timetable.allTrips.add(timeDetails);
					}
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
				
				ArrayList<trip> rqTrips = timetable.allTimes[rqHours][rqMinutes][rqSeconds];
				if (!rqTrips.isEmpty())
				{
					int[] rqIndices = new int[rqTrips.size()];
					int[] rqTripIds = new int[rqTrips.size()];
					String[][] sortedRqTrips = new String[rqTrips.size()][columns];

					for (int i = 0; i < rqTrips.size(); i++) 
					{
						rqIndices[i] = rqTrips.get(i).index;
						rqTripIds[i] = Integer.parseInt(timetable.allTrips.get(rqIndices[i])[0]);
					}
					
					Arrays.sort(rqTripIds);
					
					for (int i = 0; i < rqTrips.size(); i++)
					{
						int currentId = rqTripIds[i];
						for (int j = 0; j < rqTrips.size(); j++)
						{
							trip potentialTrip = rqTrips.get(j);
							int ptlIndex =potentialTrip.index;
							int ptlId = Integer.parseInt(timetable.allTrips.get(ptlIndex)[0]);
							if (ptlId == currentId) 
							{
								sortedRqTrips[i] = timetable.allTrips.get(ptlIndex);
							}
						}
					}
					
					for (String[] validTrip : sortedRqTrips)
					{
						for (int i = 0; i < columns; i++)
						{
							if (!validTrip[i].isBlank())
							{
								System.out.println(timetable.headers[i] + ": " + validTrip[i]);
							}
						}
						System.out.println("");
					}
				}
				
				else System.out.println("Sorry, no trips arrive at this time\n");
				stopTimesFile.close();
			} 
			catch (IOException e) 
			{
				System.out.println("File not found");
			}
		} 
		else System.out.println("Invalid time format");
		System.out.println("");
	}

}
