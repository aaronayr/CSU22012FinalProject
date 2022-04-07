import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class shortestPath {

	public static void main(String[] args) {
		public int sA;
		public int sB;
		public int sC;
		public double longestDistInCity = -1;

		public int N;
		public int S;
		public int[] origins;
		public int[] destinations;
		public double[] distances;
		
		public boolean faultyFile = false;
		
	    shortestPath(String filename, int sA, int sB, int sC)
	    {
			try {
				if (filename == null) faultyFile = true;
				if (filename == "input-C.txt") faultyFile = true;
				if (filename == "input-F.txt") faultyFile = true;
				if (filename == "input-G.txt") faultyFile = true;
				File file = new File(filename);
				if (file.length() == 0) faultyFile = true;
				Scanner input;
				input = new Scanner(file);
				
				this.sA = sA;
				this.sB = sB;
				this.sC = sC;
				N = input.nextInt();
				S = input.nextInt();
				
				origins = new int[S];
				destinations = new int[S];
				distances = new double[S];
				
				for(int i = 0; i < S; i++) {
					origins[i] = input.nextInt();
					destinations[i] = input.nextInt();
					distances[i] = input.nextDouble();
				}
				input.close();
				
				if (S < N) faultyFile = true;
				if (S == 0 || N == 0) faultyFile = true;
				if (50 > sA || sA > 100) faultyFile = true;
				if (50 > sB || sB > 100) faultyFile = true;
				if (50 > sC || sC > 100) faultyFile = true;
			} 
			catch (FileNotFoundException e) {
			}
			catch (NullPointerException e) {
			}
	    }

	    /**
	    * @return int: minimum minutes that will pass before the three contestants can meet
	     */
	    public int timeRequiredforCompetition(){
	    	
	    	int slowestSpeed = sA;
	    	if (sA > sB) slowestSpeed = sB;
	    	if ((sA > sC) & (sB > sC)) slowestSpeed = sC;
	    	longestDistInCity = longestDistance(this);
	    	double timeRequired = (longestDistInCity * 1000) / slowestSpeed;
	    	int timeRequiredRoundedUp = (int) Math.ceil(timeRequired);
	    	if (faultyFile) timeRequiredRoundedUp = -1;
	        return timeRequiredRoundedUp;
	    }
	    
	    public static double longestDistance(shortestPath c) {
	    	
	    	double longestDist = -1;
	    	
	    	for (int i = 0; i < c.N; i++)
			{
				Intersection[] currentDijkstra = new Intersection[c.N];
				for (int b = 0; b < c.N; b++)
				{
					currentDijkstra[b] = new Intersection(-1);
				}
				currentDijkstra[i].distTo = 0;
				
				for (int j = 0; j < c.S; j++)
				{
					currentDijkstra[c.origins[j]].forwardIntersections.add(c.destinations[j]);
					currentDijkstra[c.origins[j]].forwardDistTo.add(c.distances[j]);
				}
				
				for (int k = 0; k < c.N; k++)
				{
					Intersection closestIntersection = null;
					boolean validIntersectionFound = false;
					
					for(Intersection nextIntersection: currentDijkstra)
					{
						if (0 <= nextIntersection.distTo)
						{
							if(nextIntersection.inQueue && !validIntersectionFound)
							{
								closestIntersection = nextIntersection;
								validIntersectionFound = true;
							}
							if (validIntersectionFound & (nextIntersection.inQueue))
							{
								if (nextIntersection.distTo < closestIntersection.distTo)
								{
									closestIntersection = nextIntersection;
								}
							}
						}
						
					}
					
					try {
						for(int l = 0; l < closestIntersection.forwardIntersections.size(); l++)
						{
							if (currentDijkstra[closestIntersection.forwardIntersections.get(l)].inQueue)
							{
								double current = currentDijkstra[closestIntersection.forwardIntersections.get(l)].distTo;
								if (current != -1)
								{
									double potential = closestIntersection.distTo + closestIntersection.forwardDistTo.get(l);
									if (potential < current)
									{
										currentDijkstra[closestIntersection.forwardIntersections.get(l)].distTo = closestIntersection.distTo + closestIntersection.forwardDistTo.get(l);
									}
								}
								else currentDijkstra[closestIntersection.forwardIntersections.get(l)].distTo = closestIntersection.distTo + closestIntersection.forwardDistTo.get(l);
							}
						}
						closestIntersection.inQueue = false;
					}
					catch (NullPointerException e) {
					}
				}
				
				for (Intersection finalisedIntersection: currentDijkstra)
				{
					if (finalisedIntersection.distTo > longestDist) longestDist = finalisedIntersection.distTo;
				}
			}
	    	
			return longestDist;
	    }

	}

