//Craig Glassbrenner, CS340
import java.io.File;
import java.util.Stack;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class BTree {
	private RandomAccessFile f;
	private int order;
	private int blockSize;
	private long root;
	private long free;
	
	private class BTreeNode {
		private int count;
		private int keys[];
		private long children[];
		private long address;
		
		private BTreeNode(int c, int key[], long child[], long addr) {
			count = c;
			keys = key;
			children = child;
			address = addr;
		}
		
		private BTreeNode(long addr) throws IOException {
			f.seek(addr);
			count = f.readInt();
			
			keys = new int[order-1];
			for(int i=0; i < keys.length; i++) {
				keys[i] = f.readInt();
			}
			
			children = new long[order];
			for(int j=0; j < children.length; j++) {
				children[j] = f.readLong();
			}
			address = f.readLong();
		}
		
		private void writeNode(long addr) throws IOException {
			f.seek(addr);
			f.writeInt(count);
			
			for(int i=0; i < keys.length; i++) {
				f.writeInt(keys[i]);
			}
			for(int j=0; j < children.length; j++) {
				f.writeLong(children[j]);
			}
			f.writeLong(address);
		}
	}
	
	public BTree(String filename, int bsize) throws IOException {
		//bsize is the block size. This value is used to calc the order
		// of the B+ Tree
		// All B+Tree nodes will use bsize bytes
		//makes a new B+tree
		File path = new File(filename);
		f = new RandomAccessFile(path, "rw");
		f.seek(0);
		root = 0;
		f.writeLong(root);
		free = 0;
		f.writeLong(free);
		
		blockSize = bsize;
		f.writeInt(blockSize);
		
		int toDivide = bsize - 12;
		order = toDivide / 12;
	}
	
	public BTree(String filename) throws IOException {
		//open an existing B+Tree
		File path = new File(filename);
		f = new RandomAccessFile(path, "rw");
		f.seek(0);
		root = f.readLong();
		free = f.readLong();
		blockSize = f.readInt();
		
		int toDivide = blockSize - 12;
		order = toDivide / 12;
		
	}
	
	//Determines if node was split or not
	private boolean split;
	
	public boolean insert(int key, long addr) throws IOException {
		/*
		If key is not a duplicate add, key to the B+tree
		addr (in DBTable) is the address of the row that contains the key return true if the key is added
		return false if the key is a duplicate
		*/
		
		if(root == 0) {
			long newAddr = getFree();
			int[] keys = new int[order-1];
			keys[0] = key;
			long[] child = new long[order];
			child[0] = addr;
			
			BTreeNode n = new BTreeNode(-1, keys, child, newAddr);
			n.writeNode(newAddr);
			root = newAddr;
			return true;
		}
		
		Stack<BTreeNode> path = searchPath(key);
		BTreeNode cur = path.pop();
		int val=0;
		long loc=0;
		
		if(Math.abs(cur.count) < (order-1)) {
			boolean toDo = insertKey(cur, key, addr);
			if(toDo == false) {
				return false;
			} else {
				cur.count--;
				cur.writeNode(cur.address);
				split = false;
			}
		} else {
			int medianIndex = (order-1) / 2;
			val = cur.keys[medianIndex];
			
			int[] keys = new int[order-1];
			long[] child = new long[order];
			
			BTreeNode left = new BTreeNode(0, keys, child, cur.address);
			for(int i=0; i <= medianIndex; i++) {
				left.keys[i] = cur.keys[i];
				left.children[i] = cur.children[i];
				left.count--;
			}
			
			long nextAddr = getFree();
			int[] k = new int[order-1];
			long[] c = new long[order];
			
			BTreeNode right = new BTreeNode(0, k, c, nextAddr);
			for(int i= medianIndex+1; i < (order-1); i++) {
				right.keys[i-(medianIndex+1)] = cur.keys[i];
				right.children[i - (medianIndex+1)] = cur.children[i];
				right.count--;
			}
			
			boolean toDo2;
			if(key < val) {
				toDo2 = insertKey(left, key, addr);
				left.count--;
			} else {
				toDo2 = insertKey(right, key, addr);
				right.count--;
			}
			
			if(toDo2 == false) {
				return false;
			}
			
			loc = right.address;
			left.writeNode(cur.address);
			right.writeNode(right.address);
			
			val = right.keys[0];
			cur = left;
			split = true;
		}
		
		while(!path.empty() && split) {
			cur = path.pop();
			if(Math.abs(cur.count) < (order-1)) {
				boolean toDo = insertKey(cur, val, loc);
				if(toDo == false) {
					return false;
				} else {
					cur.count++;
					cur.writeNode(cur.address);
					split = false;
				}
			} else {
				int medianIndex = (order-1) / 2;
				int newVal = cur.keys[medianIndex];
				
				int[] keys = new int[order-1];
				long[] child = new long[order];
				
				BTreeNode left = new BTreeNode(0, keys, child, cur.address);
				for(int i=0; i < medianIndex; i++) {
					left.keys[i] = cur.keys[i];
					left.children[i] = cur.children[i];
					left.count--;
				}
				left.children[medianIndex] = cur.children[medianIndex];
				
				long nextAddr = getFree();
				int[] k = new int[order-1];
				long[] c = new long[order];
				
				BTreeNode right = new BTreeNode(0, k, c, nextAddr);
				for(int i= medianIndex + 1; i < (order-1); i++) {
					right.keys[i-(medianIndex+1)] = cur.keys[i];
					right.children[i - (medianIndex+1)] = cur.children[i];
					right.count--;
				}
				if((order-1)%2 == 0) {
					right.children[medianIndex-1] = cur.children[order-1];
				} else {
					right.children[medianIndex] = cur.children[order-1];
				}
				
				boolean toDo2;
				if(val < newVal) {
					toDo2 = insertKey(left, val, loc);
					left.count--;
				} else {
					toDo2 = insertKey(right, val, loc);
					right.count--;
				}
				
				if(toDo2 == false) {
					return false;
				}
				
				left.count = Math.abs(left.count);
				right.count = Math.abs(right.count);
				
				loc = right.address;
				left.writeNode(cur.address);
				right.writeNode(right.address);
				
				val = newVal;
				cur = left;
				split = true;
			}
		}
		
		if(split) {
			long cAddr = getFree();
			int[] keys = new int[order-1];
			keys[0] = val;
			long[] child = new long[order];
			child[0] = root;
			child[1] = loc;
			
			BTreeNode newRoot = new BTreeNode(1, keys, child, cAddr);
			newRoot.writeNode(cAddr);
			root = newRoot.address;
		}
		
		return true;
	}
	
	private boolean insertKey(BTreeNode b, int key, long addr) {
		int size = Math.abs(b.count);
		int sizeChild=0;
		long toSave = 0;
		
		for(int i=0; i < b.children.length; i++) {
			if(b.children[i] != 0) {
				sizeChild++;
			}
		}
		
		int index = size;
		
		for(int i=(size-1); i > -1; i--) {
			if(key < b.keys[i]) {
				b.keys[i+1] = b.keys[i];
				
				if(b.children[i+1] != 0) {
					toSave = b.children[i+1];
				}
				b.children[i+1] = b.children[i];
				index = i;
				if(toSave != 0) {
					b.children[i+2] = toSave;
					toSave = 0;
				}
			} else if(key == b.keys[i]) {
				return false;
			} else {
				break;
			}
		}

		b.keys[index] = key;
		
		if(sizeChild > size) {
			b.children[index+1] = addr;
		} else {
			b.children[index] = addr;
		}
		return true;
	}
	
	// Returns free space
	private long getFree() {
		long toReturn;
		if(free == 0) {
			toReturn = blockSize;
			free = blockSize*2;
		} else {
			toReturn = free;
			free = free + blockSize;
		}
		
		return toReturn;
	}
	
	//Gets the searchPath for where the node should go 
	private Stack<BTreeNode> searchPath(int key) throws IOException {
		Stack<BTreeNode> path = new Stack<>();
		BTreeNode s = new BTreeNode(root);
		
		path.add(s);
		while(s.count > 0) {
			for(int i=0; i < s.keys.length; i++) {
				if(key < s.keys[i] || s.keys[i] == 0 ) {
					s = new BTreeNode(s.children[i]);
					break;
					
				} else if(key >= s.keys[i] && i == (s.keys.length-1)) {
					s = new BTreeNode(s.children[i+1]);
				} 
			}
			path.add(s);
		}
		return path;
	}
	
	public long remove(int key) {
		/*
		If the key is in the Btree, remove the key and return the address of the row
		return 0 if the key is not found in the B+tree */
		return 0;
	}
	
	public long search(int k) throws IOException {
		/*
		This is an equality search
		
		If the key is found return the address of the row with the key
		otherwise return 0 */
		long toReturn;
		
		if(root == 0) {
			return 0;
		} else {
			toReturn = search(k, root);
		}
		
		return toReturn;
	}
	
	//Recursive Method for Searching for a value
	private long search(int k, long addr) throws IOException {
		BTreeNode cur = new BTreeNode(addr);
		
		if(cur.count < 0) {
			for(int i=0; i < cur.keys.length; i++) {
				if(cur.keys[i] == k) {
					return cur.children[i];
				}
			}
		} else {
			for(int i=0; i < cur.children.length; i++) {
				if(i == (cur.children.length - 1)) {
					return search(k, cur.children[i]);
				} else if(k < cur.keys[i]) {
					return search(k, cur.children[i]);
				} else if(cur.children[i+1] == 0) {
					return search(k, cur.children[i]);
				}
			}
		}
		
		return 0;
	}
	
	public LinkedList<Long> rangeSearch(int low, int high) throws IOException {
		//PRE: low <= high
		/*
		return a list of row addresses for all keys in the range low to high inclusive
		return an empty list when no keys are in the range */
		LinkedList<Long> rowAddr = new LinkedList<>();
		if(root==0) {
			return rowAddr;
		} else if(low == high) {
			long addr = search(low);
			rowAddr.add(addr);
			return rowAddr;
		} else {			
			return rangeSearch(low, high, root, rowAddr);
		}
	}
	
	//Recursive Method for rangeSearch
	private LinkedList<Long> rangeSearch(int low, int high, long addr, LinkedList<Long> l) throws IOException {
		BTreeNode cur = new BTreeNode(addr);
		if(cur.count < 0) {
			for(int i=0; i < cur.keys.length; i++) {
				if(cur.keys[i] >= low && cur.keys[i] <= high) {
					l.add(cur.children[i]);
				}
			}
		} else {
			for(int i=0; i < cur.children.length; i++) {
				if(i == (cur.children.length - 1)) {
					return rangeSearch(low, high, cur.children[i], l);
				} else {
					if(low < cur.keys[i]) {
						rangeSearch(low, high, cur.children[i], l);
					} else if(cur.children[i+1] == 0) {
						rangeSearch(low, high, cur.children[i], l);
						return l;
					}
				}
			}
		}
		
		return l;
	}
	
	public void print() throws IOException {
		//print the B+Tree to standard output 
		//print one node per line
		//This method can be helpful for debugging
		if(root==0) {
			return;
		}
		
		print(root);
		System.out.println();
	}
	
	//Recursive Method for printing BTree Nodes
	private void print(long r) throws IOException {
		BTreeNode cur = new BTreeNode(r);
		if(cur.count > 0) {
			System.out.print("(" + cur.count + " ");
			for(int j=0; j < cur.keys.length; j++) {
				System.out.print(cur.keys[j] + " ");
			}
			for(int k=0; k < cur.children.length; k++) {
				System.out.print(cur.children[k] + " ");
			}
			System.out.println(cur.address + ")");
			for(int i=0; i < cur.children.length; i++) {
				print(cur.children[i]);
			}
		} else {
			System.out.print("(" + cur.count + " ");
			for(int j=0; j < cur.keys.length; j++) {
				System.out.print(cur.keys[j] + " ");
			}
			for(int k=0; k < cur.children.length; k++) {
				System.out.print(cur.children[k] + " ");
			}
			System.out.println(cur.address + ")");
		}
	}
	
	public void close() throws IOException {
		//close the B+tree. The tree should not be accessed after close is called
		f.close();
	}
}











