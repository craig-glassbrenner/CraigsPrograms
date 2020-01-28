//Craig Glassbrenner, Probabilites: .33633, .3329, .33309
// In general, it seems that the probability is 1/3, this is the case because
// if you just think about it on the interval (0,1), the biggest number that x can
// be for the interval (x,b) to be double (a,x) is 1/3. 

public class Prob3 {

	public static void main(String[] args) {
		double first = interval(0, 1);
		double second = interval(0, 10);
		double third = interval(0, 100);
		
		System.out.println(first);
		System.out.println(second);
		System.out.println(third);

	}
	
	public static double interval(double a, double b) {
		int numTrials = 100000;
		int success = 0;
		
		for(int i=1; i <= numTrials; i++) {
			double x = Math.random()*b + a;
			
			double length1 = x - a;
			double length2 = b - x;
			
			if((2*length1) < length2) {
				success++;
			}
		}
		
		return ((double)success / (double) numTrials);
	}
}
