
public class Q2 {

	public static void main(String[] args) {
		boolean palindrome = false;
		
		while(!palindrome) {
			long max = -1;
			long num = 9999999;
			
			for(long i= num; i >= 1000000; i--) {
				for(long j=i; j >= 1000000; j--) {
					long product = i*j;
					String numWord = Long.toString(product);
							
					if(product <= max) {
						break;
					} else if ( isPalindrome(numWord)) {
						palindrome = true;
						max = product;
					}
				}
			}
			System.out.println(max);
		}
	}
	
	private static boolean isPalindrome(String s) {
		return s.equals(new StringBuilder(s).reverse().toString());
	}
}
