//Craig Glassbrenner
import java.io.*;
import java.util.*;

/*Implements a ALV tree of ints (the keys) and fixed length character strings fields
stored in a random access file.
Duplicates keys are not allowed. There will be at least 1 character string field
*/
public class AVLTree {

	private RandomAccessFile f;
	private long root; //the address of the root node in file
	private long free; //the address in the file of the first node in the free list
	private int numStringFields;
	private int fieldLengths[];
	private int numIntFields;
	
	private class Node {
		private int key;
		private char stringFields[][];
		private int intFields[];
		private long left;
		private long right;
		private int height;
		
		private Node(long l, int d, long r, char sFields[][], int iFields[]) {
			//Constructor for a new Node
			left = l;
			right = r;
			key = d;
			intFields = iFields;
			stringFields = sFields;
			height = 0;
			
		}
		
		private Node(long addr) throws IOException {
			//constructor for a node that exists and is stored in the file
			f.seek(addr);
			key = f.readInt();
			stringFields = new char[numStringFields][];
			for(int i=0; i < numStringFields; i++) {
				stringFields[i] = new char[fieldLengths[i]];
				for(int j=0; j < stringFields[i].length; j++) {
					stringFields[i][j] = f.readChar();
				}
			}
			
			intFields = new int[numIntFields];
			for(int j=0; j < numIntFields; j++) {
				intFields[j] = f.readInt();
			}
			left = f.readLong();
			right = f.readLong();
			height = f.readInt();
			
		}
		
		private void writeNode(long addr) throws IOException {
			//writes the node to the file at location addr
			f.seek(addr);
			f.writeInt(key);
			for(int i=0; i<stringFields.length; i++) {
				for(int j=0; j<stringFields[i].length; j++) {
					f.writeChar(stringFields[i][j]);
				}
			}
			
			for(int i=0; i<intFields.length; i++) {
				f.writeInt(intFields[i]);
			}
			
			f.writeLong(left);
			f.writeLong(right);
			f.writeInt(height);
		}
	}
		
	public AVLTree(String fname, int stringFieldLengths[], int numIntField) throws IOException {
		//creates a new empty AVL Tree stored in the file fname
		//the number of character string fields is stringFieldLengths.length
		//stringFieldLengths contains the length of each string field
		File path = new File(fname);
		f = new RandomAccessFile(path, "rw");
		f.seek(0);
		root = 0;
		f.writeLong(root);
		free = 0;
		f.writeLong(free);
		
		numStringFields = stringFieldLengths.length;
		f.writeInt(numStringFields);
		
		fieldLengths = new int[numStringFields];
		for(int i=0; i < numStringFields; i++) {
			fieldLengths[i] = stringFieldLengths[i];
			f.writeInt(fieldLengths[i]);
		}
		numIntFields = numIntField;
		f.writeInt(numIntFields);
	}
	
	public AVLTree(String fname) throws IOException {
		//reuse an existing tree store in the file fname
		File path = new File(fname);
		f = new RandomAccessFile(path, "rw");
		f.seek(0);
		root = f.readLong();
		free = f.readLong();
		numStringFields = f.readInt();
		for(int i=0; i < numStringFields; i++) {
			fieldLengths[i] = f.readInt();
		}
		numIntFields = f.readInt();
	}
	
	public void insert(int k, char sFields[][], int iFields[]) throws IOException {
		//PRE: the number and lengths of the sFields and iFields match the expected number and lengths
		//insert k and the fields into the tree
		//the string fields are null ('\0') padded
		//if k is in the tree do nothing
		root = insert(root, k, sFields, iFields);
	}
	
	private long insert(long r, int k, char sFields[][], int iFields[]) throws IOException {
		Node y;
		if(r == 0) {
			y = new Node(0, k, 0, sFields, iFields);
			long addr = getFree();
			y.writeNode(addr);
			return addr;
		}
		
		y = new Node(r);
		if( k < y.key) {
			y.left = insert(y.left, k, sFields, iFields);
			
			if(y.left == 0) {
				y.height = getHeight(y.right) + 1;
			} else if(y.right == 0) {
				y.height = getHeight(y.left) + 1;
			} else {
				y.height = max(getHeight(y.left), getHeight(y.right)) + 1;
			}
			
			if(checkBalance(y.left) - checkBalance(y.right) >= 2) {
				
				if(k < y.key) {
					//singleRotationLeft
					Node x = new Node(y.left);
					long b = x.right;
					x.right = r;
					r = y.left;
					y.left = b;
					y.height = fixHeight(y);
					y.writeNode(x.right);
					x.height = fixHeight(x);
					x.writeNode(r);
					
				} 
			} else {
				y.writeNode(r);
			}
			
		} else if( k > y.key) {
			y.right = insert(y.right, k, sFields, iFields);
			
			if(y.left == 0) {
				y.height = getHeight(y.right) + 1;
			} else if(y.right == 0) {
				y.height = getHeight(y.left) + 1;
			} else {
				y.height = max(getHeight(y.left), getHeight(y.right)) + 1;
			}
			
			if(checkBalance(y.right) - checkBalance(y.left) >= 2) {
				
				if( k > y.key) {
					//singleRotationRight
					Node x = new Node(y.right);
					long b = x.left;
					x.left = r;
					r = y.right;
					y.right = b;
					y.height = fixHeight(y);
					y.writeNode(x.left);
					x.height = fixHeight(x);
					x.writeNode(r);
					
				} 
			} else {
				y.writeNode(r);
			}
			
		} else {
			;
		}
		
		return r;
		
	}
	
	private long getFree() throws IOException {
		//IDK
		if(free == 0) {
			free = 16;
			for(int i=0; i < numStringFields; i++) {
				free = free + 4;
			}
			free = free + 8;
			return free;
		}
		free = free + 16;
		for(int i=0; i < numStringFields; i++) {
			free = free + (fieldLengths[i]*2);
		}
		for(int j=0; j < numIntFields; j++) {
			free = free + 4;
		}
		free = free + 8;
		return free;
	}
	
	private int checkBalance(long addr) throws IOException {
		if(addr == 0) {
			return -1;
		}
		Node x = new Node(addr);
		return x.height;
		
	}
	
	private int fixHeight(Node x) throws IOException {
		if(x.right == 0 && x.left == 0) {
			return 0;
		} else if(x.right == 0) {
			return getHeight(x.left) + 1;
		} else if(x.left == 0) {
			return getHeight(x.right) + 1;
		} else {
			return max(getHeight(x.left), getHeight(x.right)) + 1;
		}
	}
	
	private int getHeight(long addr) throws IOException {
		Node high = new Node(addr);
		return high.height;
	}
	
	private int max(int h1, int h2) {
		if(h1 > h2) {
			return h1;
		} else {
			return h2;
		}
	}
	
	public void print() throws IOException {
		//Print the contents of the nodes in the tree in ascending order of the key
		//do not print the null characters
		print(root);
		System.out.println();
	}
	
	private void print(long r) throws IOException {
		//Private recursive method for print()
		if(r == 0 ) {
			return;
		}
		Node x = new Node(r);
		if(x.left != 0) {
			print(x.left);
		} 
		
		System.out.print("(" + x.key + " ");
		for(int i=0; i < x.stringFields.length; i++) {
			for(int j=0; j < x.stringFields[i].length; j++) {
				if(x.stringFields[i][j] != '\0') {
					System.out.print(x.stringFields[i][j]);
				}
			}
			System.out.print(" ");
		}
		for(int i=0; i < x.intFields.length; i++ ) {
			System.out.print(" " + x.intFields[i]);
		}
		System.out.println(" " + x.left + " " + x.right + " " + x.height + ")");
		
		if(x.right != 0) {
			print(x.right);
		} else {
			return;
		}
	}
	
	public LinkedList<String> stringFind(int k) throws IOException {
		//if k is in the tree return a linked list of the strings fields associated with k
		//otherwise return null
		//The strings in the list must NOT include the padding(null chars)
		LinkedList<String> toReturn = new LinkedList<String>();
		Node r = new Node(root);
		Node kNode = find(r, k);
		
		if(kNode == null) {
			return null;
		}
		
		for(int i=0; i < kNode.stringFields.length; i++) {
			String toAdd = "";
			for(int j=0; j < kNode.stringFields[i].length; j++) {
				toAdd = toAdd + kNode.stringFields[i][j];
			}
			toReturn.add(toAdd);
		}
		return toReturn;
	}
	
	private Node find(Node r, int k) throws IOException {
		if(r.key == k) {
			return r;
		} else if(r.left == 0 && r.right == 0) {
			return null;
		} else {
			Node toReturn;
			if(r.key > k) {
				toReturn = find(new Node(r.left), k);
			} else {
				toReturn = find(new Node(r.right), k);
			}
			
			return toReturn;
		}
	}
	
	public LinkedList<Integer> intFind(int k) throws IOException {
		//if k is in the tree return a linked list of the integer fields associated with k
		//otherwise return null
		LinkedList<Integer> toReturn = new LinkedList<Integer>();
		Node r = new Node(root);
		Node kNode = find(r, k);
		
		if(kNode == null) {
			return null;
		}
		
		for(int i=0; i < kNode.intFields.length; i++) {
			toReturn.add(kNode.intFields[i]);
		}
		
		return toReturn;
	}
	
	public void remove(int k) throws IOException {
		//if k is in the tree remove the node with key k from the tree
		//otherwise do nothing
		root = remove(root, k);
	}
	
	private long remove(long r, int k) throws IOException {
		if(r == 0) {
			return 0;
		} 
		
		Node cur = new Node(r);
		if(cur.key != k && cur.left == 0 && cur.right == 0) {
			return 0;
		}
		
		if(cur.key > k) {
			cur.left = remove(cur.left, k);
		} else if(cur.key < k) {
			cur.right = remove(cur.right, k);
		} else {
			if(cur.right == 0 || cur.left == 0) {
				Node temp;
				if(cur.right == 0 && cur.left == 0) {
					temp = cur;
				} else if(cur.right == 0) {
					temp = new Node(cur.left);
				} else {
					temp = new Node(cur.right);
				}
				
				cur = temp;
			} else {
				Node temp = cur;
				while(temp.left != 0) {
					temp = new Node(temp.left);
				}
				
				cur.key = temp.key;
				cur.right = remove(cur.right, temp.key);
			}
		}
		
		cur.height = max(getHeight(cur.left), getHeight(cur.right)) + 1;
		
		if(checkBalance(cur.left) - checkBalance(cur.right) >= 2) {
			
			if(k < cur.key) {
				//singleRotationLeft
				Node x = new Node(cur.left);
				long b = x.right;
				x.right = r;
				r = cur.left;
				cur.left = b;
				cur.height = fixHeight(cur);
				cur.writeNode(x.right);
				x.height = fixHeight(x);
				x.writeNode(r);
				
			} else if( k > cur.key) {
				//singleRotationRight
				Node x = new Node(cur.right);
				long b = x.left;
				x.left = r;
				r = cur.right;
				cur.right = b;
				cur.height = fixHeight(cur);
				cur.writeNode(x.left);
				x.height = fixHeight(x);
				x.writeNode(r);
				
			} 
		} else {
			cur.writeNode(r);
		}
		
		return r;
	}
	
	public void close() throws IOException {
		//update root and free in the file(if necessary)
		//close the random access file
		f.close();
	}
}
