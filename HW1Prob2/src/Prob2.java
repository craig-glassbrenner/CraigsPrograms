//Craig Glassbrenner, Answer = 1.33355366

public class Prob2 {

	public static void main(String[] args) {
		int numTrials = 100000;
		int numInside = 0;
		int a = 0;
		double b = Math.PI;
		double ymax = 0.76980036;
		
		for(int i = 1; i <= numTrials; i++) {
			double randx = Math.random()*b + a;
			double randy = Math.random()*ymax;
			
			if(randy <= f(randx)) {
				numInside++;
			}
		}
		
		double area = (numInside / (double) numTrials)*(b-a)*ymax;
		System.out.println(area);

	}
	
	private static double f(double x) {
		double toReturn = (Math.sin(2*x))*Math.cos(x);
		
		return toReturn;
	}

}
