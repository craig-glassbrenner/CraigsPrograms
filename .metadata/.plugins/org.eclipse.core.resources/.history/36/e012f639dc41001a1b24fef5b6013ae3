//Craig Glassbrenner
import java.util.*;

public class HuffmanTree {
//DO NOT include the frequency or priority in the tree
	
	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;
		
		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}
	
	private Node root;
	private Node current; //this value is changed by the move methods
	
	public HuffmanTree() {
		root = null;
		current = null;
	}
	
	public HuffmanTree(char d) {
		//make a single node tree
		root = new Node(null, d, null, null);
		current = root;
	}
	
	public HuffmanTree(String t, char nonLeaf) {
		//Assumes t represents a post order representation of the tree as discussed
		// in class
		//nonLeaf is the char value of the data in the non-leaf nodes
		//in class we used (char) 128 for the non-leaf value
		if(t.compareTo("") == 0) {
			root = null;
			current = null;
			return;
		}
		Stack<Node> nodeStack = new Stack<Node>();

		int j = 0;
		while(j < t.length()) {
			Node toMake;
			if(t.charAt(j) == nonLeaf) {
				Node right = nodeStack.pop();
				Node left = nodeStack.pop();
				
				toMake = new Node(left, nonLeaf, right, null);
				right.parent = toMake;
				left.parent = toMake;
				nodeStack.push(toMake);
				
			} else {
				toMake = new Node(null, t.charAt(j), null, null);
				nodeStack.push(toMake);
			}
			j++;
		}
		
		root = nodeStack.pop();
		current = root;
	}
	
	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
		//makes a new tree where b1 is the left subtree and b2 is the right subtree
		//d is the data in the root
		root = new Node(b1.root, d, b2.root, null);
		b1.root.parent = root;
		b2.root.parent = root;
		current = root;
	}
	
	//use the move methods to traverse the tree
	//the move methods change the value of current
	//use these in the decoding process
	public void moveToRoot() {
	 //change current to reference the root of the tree
		current = root;
	}
	
	public void moveToLeft() {
	 //PRE: the current node is not a leaf
	 //change current to reference the left child of the current node
		current = current.left;
	}
	
	public void moveToRight() {
	 //PRE: the current node is not a leaf
	 //change current to reference the right child of the current node
		current = current.right;
	}
	
	 public void moveToParent() {
	 //PRE: the current node is not the root
	 //change current to reference the parent of the current node
		 current = current.parent;
	 }
	 
	 public boolean atRoot() {
		 //returns true if the current node is the root
		 if(current.parent == null && current.data == root.data) {
			 return true;
		 }
		 
		 return false;
	 }
	 
	 public boolean atLeaf() {
		 //returns true if current references a leaf
		 if(current.left == null && current.right == null) {
			 return true;
		 }
		 
		 return false;
	 }
	 
	 public char current() {
		 //returns the data value in the node referenced by current
		 return current.data;
	 }
	 
	 public String[] pathsToLeaves() {
		 /* returns an array of strings with all paths from the root to the leaves
		 each value in the array contains a leaf value followed by a seqeunce of
		 0s and 1s. The 0s and 1s represent the path from the root to the node
		 containing the leaf value.
		 */
		 moveToRoot();
		 String huffCode = "";
		 String arrStringForm = pathsToLeaves(current, huffCode);
		 String[] arrayOfStrings = new String[arrStringForm.length()];
		 
		 int i=0;
		 while(i < arrayOfStrings.length) {
			 arrayOfStrings[i] = Character.toString(arrStringForm.charAt(i));
			 i++;
		 }
		 return arrayOfStrings;
	 }
	 
	 public String oneEncode;
	 public String zeroEncode;

	 private String pathsToLeaves(Node cur, String h) {
		 
		 current = cur;
		 
		 if(atLeaf()) {
			 if(cur.data == '1') {
				 oneEncode = h;
				 return "";
			 } else if(cur.data == '0') {
				 zeroEncode = h;
				 return "";
			 }
			 return Character.toString(cur.data) + h;
		 } else {
			 String l = pathsToLeaves(cur.left, h + "0");
			 String r = pathsToLeaves(cur.right, h + "1");
			 return l + r;
		 }
	 }
	
	 public String toString() {
		 //returns a string representation of the tree using the postorder format
		 moveToRoot();
		 if(root == null) {
			 return "";
		 } else if(root.left == null && root.right == null) {
			 return Character.toString(root.data);
		 }
		 String toReturn = toString(current);
		 return toReturn;
		 
	 }
	 
	 private String toString(Node cur) {
		 String toReturn = "";
		 
		 if(cur.left == null && cur.right == null) {
			 return toReturn + cur.data;
		 } if(cur.left != null) {
			 toReturn = toReturn + toString(cur.left);
		 } if(cur.right != null) {
			 toReturn = toReturn + toString(cur.right);
		 }
		 return toReturn + cur.data;
	 }

}











