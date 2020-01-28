
public class Tester {

	public static void main(String[] args) {
		int[] toSort = {8, 17, 4, 2, 3, 14, 6};
		InsertionSort s = new InsertionSort();
		
		int[] sorted = s.sortArray(toSort);
		for(int i=0; i < sorted.length; i++) {
			System.out.print(sorted[i] + " ");
		}
		System.out.println();
		

	}

}
