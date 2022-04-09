package tripSearch;

public class trip {
	public int hours;
	public int minutes;
	public int seconds;
	public int index;
	
	public trip(String time, int index)
	{
		String[] temp = time.split(":");
		hours = Integer.parseInt(temp[0]);
		minutes = Integer.parseInt(temp[1]);
		seconds = Integer.parseInt(temp[2]);
		this.index = index;
	}
}
