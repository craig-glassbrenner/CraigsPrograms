import java.util.Scanner;

public class Q1 {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number: ");
		
		double num = scan.nextDouble();
		scan.close();
		
		double total = 0;
		num = num - 1;
		while( num >= 0) {
			if ( num % 6 == 0) {
				total = total + num;
				
			} else if( num % 4 == 0 && num%6 != 0) {
				total = total + num;
				
			}
			
			num = num - 1;
		}
		
		System.out.println("Sum = " + total);

	}
}
