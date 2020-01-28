//Craig Glassbrenner, Estimated Probabilites: D1 = .99211 D2 = .73899
// D3 = .33771 D4 = .18098 D5 = .11648

public class Prob6 {

	public static void main(String[] args) {
		double first = D1();
		System.out.println(first);
		
		double second = D2();
		System.out.println(second);
		
		double third = D3();
		System.out.println(third);
		
		double fourth = D4();
		System.out.println(fourth);
		
		double fifth = D5();
		System.out.println(fifth);
	}
	
	public static double D1() {
		int maxSteps = 10000;
		int numTrials = 100000;
		int bad = 0;
		
		for(int i=1; i <= numTrials; i++) {
			int right = 0;
			int stepCounter = 0;
			
			while(stepCounter != maxSteps) {
				double rand = Math.random();
				
				if(rand > .5) {
					right++;
					
				} else {
					right--;
				}
				
				if(right == 0) {
					bad++;
					break;
				}
				stepCounter++;
			}
		}
		
		double probability = (double) bad / (double) numTrials;
		return probability;
	}
	
	public static double D2() {
		int maxSteps = 10000;
		int numTrials = 100000;
		int bad = 0;
		
		for(int i=1; i <= numTrials; i++) {
			int right = 0;
			int up = 0;
			int stepCounter = 0;
			
			while(stepCounter != maxSteps) {
				double rand = Math.random();
				
				if( rand < .25) {
					right ++;
				} else if(rand >= .25 && rand < .5) {
					right--;
				} else if(rand >= .5 && rand < .75) {
					up++;
				} else {
					up--;
				}
				
				if(right == 0 && up == 0) {
					bad++;
					break;
				}
				
				stepCounter++;
			}
		}
		
		double prob = (double) bad / (double) numTrials;
		return prob;
	}
	
	public static double D3() {
		int maxSteps = 10000;
		int numTrials = 100000;
		int bad = 0;
		
		for(int i=1; i <= numTrials; i++) {
			int right = 0;
			int up = 0;
			int z = 0;
			int stepCounter = 0;
			
			while(stepCounter != maxSteps) {
				double rand = Math.random();
				
				if( rand < (1/(double)6)) {
					right ++;
				} else if(rand >= (1/(double)6) && rand < (2/(double)6)) {
					right--;
				} else if(rand >= (2/(double)6) && rand < (3/(double)6)) {
					up++;
				} else if(rand >= (3/(double)6) && rand < (4/(double)6)) {
					up--;
				} else if(rand >= (4/(double)6) && rand < (5/(double)6)) {
					z++;
				} else {
					z--;
				}
				
				if(right == 0 && up == 0 && z == 0) {
					bad++;
					break;
				}
				
				stepCounter++;
			}
		}
		
		double prob = (double) bad / (double) numTrials;
		return prob;
	}
	
	public static double D4() {
		int maxSteps = 10000;
		int numTrials = 100000;
		int bad = 0;
		
		for(int i=1; i <= numTrials; i++) {
			int right = 0;
			int up = 0;
			int z = 0;
			int time = 0;
			int stepCounter = 0;
			
			while(stepCounter != maxSteps) {
				double rand = Math.random();
				
				if( rand < (1/(double)6)) {
					right ++;
				} else if(rand >= (1/(double)8) && rand < (2/(double)8)) {
					right--;
				} else if(rand >= (2/(double)8) && rand < (3/(double)8)) {
					up++;
				} else if(rand >= (3/(double)8) && rand < (4/(double)8)) {
					up--;
				} else if(rand >= (4/(double)8) && rand < (5/(double)8)) {
					z++;
				} else if(rand >= (5/(double)8) && rand < (6/(double)8)) {
					z--;
				} else if(rand >= (6/(double)8) && rand < (7/(double)8)) {
					time++;
				} else {
					time--;
				}
				
				if(right == 0 && up == 0 && z == 0 && time == 0) {
					bad++;
					break;
				}
				
				stepCounter++;
			}
		}
		
		double prob = (double) bad / (double) numTrials;
		return prob;
	}
	
	public static double D5() {
		int maxSteps = 10000;
		int numTrials = 100000;
		int bad = 0;
		
		for(int i=1; i <= numTrials; i++) {
			int right = 0;
			int up = 0;
			int z = 0;
			int time = 0;
			int spatial = 0;
			int stepCounter = 0;
			
			while(stepCounter != maxSteps) {
				double rand = Math.random();
				
				if( rand < (1/(double)6)) {
					right ++;
				} else if(rand >= (1/(double)10) && rand < (2/(double)10)) {
					right--;
				} else if(rand >= (2/(double)10) && rand < (3/(double)10)) {
					up++;
				} else if(rand >= (3/(double)10) && rand < (4/(double)10)) {
					up--;
				} else if(rand >= (4/(double)10) && rand < (5/(double)10)) {
					z++;
				} else if(rand >= (5/(double)10) && rand < (6/(double)10)) {
					z--;
				} else if(rand >= (6/(double)10) && rand < (7/(double)10)) {
					time++;
				} else if(rand >= (7/(double)10) && rand < (8/(double)10)) {
					time--;
				} else if(rand >= (8/(double)10) && rand < (9/(double)10)) {
					spatial++;
				} else {
					spatial --;
				}
				
				if(right == 0 && up == 0 && z == 0 && time == 0 && spatial == 0) {
					bad++;
					break;
				}
				
				stepCounter++;
			}
		}
		
		double prob = (double) bad / (double) numTrials;
		return prob;
	}
}
