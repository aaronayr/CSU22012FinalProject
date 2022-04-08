package stopSearch;

public class Main {

	public static void main(String[] args) {
		String x = "FLAGSTOP example street";
		swap(x);
	}
	
	public static void swap(String busStop)
	{
		int space = busStop.indexOf(" ");
		
		System.out.println(busStop);
		if (busStop.indexOf("B ") == 1 || busStop.indexOf("FLAGSTOP") == 0) 
		{
			String prefix = busStop.substring(0, space);
			String other = busStop.substring(space+1, busStop.length());
			busStop = other + " " + prefix;
		}
		System.out.println(busStop);
	}

}
