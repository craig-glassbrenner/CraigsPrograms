
public class BinaryHeap {
	private int contents[];
	private int size;
	
	public BinaryHeap(int s) {
		contents = new int[s+1];
		size = 0;
	}
	
	public boolean empty() {
		return size == 0;
	}
	
	public int getMin() {
		return contents[1];
	}
	
	public boolean full() {
		return size == contents.length-1;
	}
	
	public int getSize() {
		return size;
	}
	
	public void insert(int k) {
		
		if(full()) {
			return;
			
		} else {
			int parent;
			int child;
			size ++;
			child = size;
			parent = child / 2;
			contents[0] = k;
			
			while(contents[parent] > k) {
				contents[child] = contents[parent];
				child = parent;
				parent = child / 2;
			}
			contents[child] = k;
		}
	}
	
	public int removeMin() {
		if(empty()) {
			return 0;
			
		} else {
			int parent;
			int child;
			size --;
			int d = contents[1];
			int x = contents[size];
			
			child = 2;
			while(child < size) {
				parent = child / 2;
				if(child < size && contents[child+1] < contents[child]) {
					child++;
					
				} if(x < contents[child]) {
					break;
					
				}
				
				contents[parent] = contents[child];
				child = 2*child;
			}
			
			contents[child/2] = x;
			return d;
		}
	}
}








