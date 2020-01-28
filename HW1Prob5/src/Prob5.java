//Craig Glassbrenner, p(.51) = .0416, p(.6) = .3386, p(.7) = .5708
// p(.8) = .7521, p(.9) = .8917, p(.95) = .9443
import java.util.Random;

public class Prob5 {

	protected static Random rand = new Random();
	
	public static void main(String[] args) {
		double fiftyone = votes(51, 10000);
		double sixty = votes(60, 10000);
		double seventy = votes(70, 10000);
		double eighty = votes(80, 10000);
		double ninety = votes(90, 10000);
		double ninetyfive = votes(95, 10000);
		
		System.out.println(fiftyone);
		System.out.println(sixty);
		System.out.println(seventy);
		System.out.println(eighty);
		System.out.println(ninety);
		System.out.println(ninetyfive);
		
	}

	
	public static double votes(int percentVotes, int numTrials) {
		int numFail = 0;
		int totalVotes = 100*percentVotes;
		
		for(int i=0; i < numTrials; i++) {
			int[] perm = generateArray(totalVotes);
			int count = 0;
			
			for(int j=0; j<perm.length; j++) {
				if(perm[j] == 0) {
					count--;	
				} else {
					count++;
				}
				
				if( count < 0 ) {
					numFail++;
					break;
				}
			}
		}
		
		double prob = (double) (numTrials - numFail) / (double) numTrials;
		return prob;
	}
	
	public static int[] generateArray(int n) {
		int numVotes = 10000;
		int[] voteCount = new int[numVotes];
		
		for(int i=0; i < numVotes; i++) {
			if(i < n) {
				voteCount[i] = 1;
			} else {
				voteCount[i] = 0;
			}
		}
		
		for(int j=0; j < numVotes; j++) {
			int index = j + rand.nextInt(numVotes-j);
			int temp = voteCount[j];
			voteCount[j] = voteCount[index];
			voteCount[index] = temp;
		}
		
		return voteCount;
	}
}









