//Craig Glassbrenner, Answer = .49428

public class Prob4 {

	public static void main(String[] args) {
		int numTrials = 100000;
		int win = 0;
		int lose = 0;
		
		for(int i=1; i<=numTrials; i++) {
			int die1 = (int) (Math.random()*6 + 1);
			int die2 = (int) (Math.random()*6 + 1);
			int sum = die1 + die2;
			
			if(sum == 7 || sum == 11) {
				win++;
				
			} else if(sum == 2 || sum == 3 || sum == 12) {
				lose++;
				
			} else {
				int point = sum;
				int nextSum = 0;
				
				while(nextSum != point && nextSum != 7) {
					int dieA = (int) (Math.random()*6 + 1);
					int dieB = (int) (Math.random()*6 + 1);
					
					nextSum = dieA + dieB;
					if(nextSum == point) {
						win++;
						
					} else if(nextSum == 7) {
						lose++;
					}
				}
			}
		}
		double chance = (double) win / (double) numTrials;
		System.out.println(chance);

	}

}
