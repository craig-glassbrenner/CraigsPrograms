//Craig Glassbrenner, BinaryTree HW
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class BinaryTree {

	private class Node {
		private Node left;
		private Node right;
		private String data;
		private Node parent;
		
		private Node(Node L, String d, Node r, Node p) {
			left= L;
			data = d;
			right = r;
			parent = p;
		}
	}
	
	private Node root;
	
	// Stores what string was used to create the BinaryTree, originally contains default values.
	private String emp = "!";
	private String cl = ")";
	private String op = "(";
	
	public BinaryTree() {
		//create an empty tree
		root = new Node(null, null, null, null);
	}
	
	public BinaryTree(String d) {
		//create a tree with a single node
		root = new Node(null, d, null, null);
		
	}
	
	public BinaryTree(BinaryTree b1, String d, BinaryTree b2) {
		//merge the trees b1 AND b2 with a common root with data d
		// this constructor must make a copy of the contents of b1 and b2
		Node root1;
		Node root2;
		
		if(b1 == null) {
			root1 = null;
		} else {
			root1 = b1.root;
		}
		
		if(b2 == null) {
			root2 = null;
		} else {
			root2 = b2.root;
		}
		
		root = new Node(root1, d, root2, null);
		
		if(root1 != null) {
			root1.parent = root;
		} if(root2 != null) {
			root2.parent = root;
		}
		
	}
	
	public BinaryTree(String t, String open, String close, String empty) {
		/*create a binary tree from the post order format discussed
		in class. Assume t is a syntactically correct string
		representation of the tree. Open and close are the strings
		which represent the beginning and end markers of a tree.
		Empty represents an empty tree.
		The example in class used ( ) and ! for open, close and
		empty respectively.
		The data in the tree will not include strings matching
		open, close or empty.
		All tokens (data, open, close and empty) will be separated
		By white space
		Most of the work should be done in a private recursive
		method
		*/
		
		root = new Node(null, null, null, null);
		emp = empty;
		cl = close;
		op = open;
		
		if(t.compareTo(empty) == 0) {
			return;
		}
		
		Scanner scan = new Scanner(t);
		scan.next();
		t = scan.nextLine();
		scan.close();
		
		BinaryTreeRecur(t, root);
		
	}
	
	// Recursively creates a BinaryTree rooted at Node r, given the remaining amount of a String.
	private void BinaryTreeRecur(String t, Node r) {
		Scanner scan = new Scanner(t);
		String toCompare = scan.next();
		Node newNode = null;
		
		if(toCompare == null) {
			scan.close();
			return;
			
		} if(toCompare.compareTo(op) == 0) {
			newNode = new Node(null, null, null, r);
			if(r.left != null) {
				r.right = newNode;
				
			} else {
				r.left = newNode;
			}
			
		} if(toCompare.compareTo(emp) == 0) {
			String d = t;
			Scanner scan2 = new Scanner(d);
			String next = scan2.next();
			next = scan2.next();
			scan2.close();
			if(next.compareTo(op) != 0 && next.compareTo(cl) != 0 && next.compareTo(emp) != 0) {
				r.right = null;
				
			} else {
				r.left = null;
			}
			newNode = r;
			
		} if(toCompare.compareTo(op) != 0 && toCompare.compareTo(cl) != 0 && toCompare.compareTo(emp) != 0) {
			r.data = toCompare;
			scan.next();
			newNode = r.parent;
			
		}
		
		if(scan.hasNextLine()) {
			t = scan.nextLine();
			scan.close();
			BinaryTreeRecur(t, newNode);
		} else {
			scan.close();
			return;
		}
	}
	
	public Iterator<String> postorder() {
		//return a new post order iterator object
		 return new PostorderIterator();
		 
	}
	
	public class PostorderIterator implements Iterator<String> {
		//An iterator that returns data in the tree in an post order pattern
		//the implementation must use the parent pointer and must not use an
		//additional data structure
		
		// Gives curNode and Node that we lastVisited, allows iterator to know if we already returned
		// child nodes of the parent Node.
		private Node curNode;
		private Node lastVisit;
		
		public PostorderIterator() {
			curNode = root;
		}
		
		public boolean hasNext() {
			return curNode != null;
		}
		
		public String next() {
			
			if(!hasNext()) {
				throw new NoSuchElementException();
			} else {
				String toReturn = "";
				
				while(curNode.left != null || curNode.right != null) {
					if((curNode.right != null && curNode.right == lastVisit) || curNode.right == null && curNode.left == lastVisit) {
						toReturn = curNode.data;
						lastVisit = curNode;
						curNode = curNode.parent;
						return toReturn;
						
					} else if(curNode.left != null && lastVisit != curNode.left) {
						curNode = curNode.left;
						
					} else if(lastVisit == curNode.left && curNode.right != null) {
						curNode = curNode.right;
						
					}  
				}
				
				if(curNode == root) {
					toReturn = root.data;
					curNode = null;
					return toReturn;
				}
				
				toReturn = curNode.data;
				lastVisit = curNode;
				curNode = curNode.parent;
				
				return toReturn;
			}
		}
		
		public void remove() {
			//optional method not implemented
		}
	}
	
	public Iterator<String> inorder() {
		return new InorderIterator();
	}
	
	public class InorderIterator implements Iterator<String> {
		//An iterator that returns data in the tree in a in order pattern
		//This implementation must use a stack and must not use the parent pointer
		//You must use Java’s stack class
		
		// Gives curNode and creates the stack of what will be Nodes
		private Node curNode;
		private Stack<Node> stackOfNodes = new Stack<Node>();
		
		public InorderIterator() {
			if(root == null) {
				return;
			}
			
			curNode = root;
			stackOfNodes.push(curNode);
			while(curNode.left != null) {
				stackOfNodes.push(curNode.left);
				curNode = curNode.left;
			}
		}
		
		public boolean hasNext() {
			return !stackOfNodes.isEmpty();
		}
		
		public String next() {
			
			if(!hasNext()) {
				throw new NoSuchElementException();
			} else {
				
				Node toReturn = stackOfNodes.pop();
				
				if(toReturn.right != null) {
					Node temp = toReturn.right;
					stackOfNodes.push(temp);
					
					while(temp.left != null) {
						stackOfNodes.push(temp.left);
						temp = temp.left;
					}
				}
				return toReturn.data;
			}
		}

		public void remove() {
			//optional method not implemented
		}
	}
	
	public String toString() {
		
		if(root.data == null) {
			return "!";
		}
		
		String tree = toString(emp, op, cl, root);
		return tree;
	}
	
	// Recursively takes in a binaryTree and creates a string representation of it, using a call to the root node
	private String toString(String empty, String open, String close, Node r) {
		
		if(r.left == null && r.right == null) {
			String toAdd = open + " " + empty + " " + empty + " " + r.data + " " + close;
			return toAdd;
			
		} 
		if(r.left != null && r.right != null) {
			return open + " " + toString(empty, open, close, r.left) + " " + toString(empty, open, close, r.right) + " " + r.data + " " + close;
			
		} else if(r.left == null && r.right != null) {
			return open + " " + empty + " " + toString(empty, open, close, r.right) + " " + r.data + " " + close;
			
		} else {
			return open + " " + toString(empty, open, close, r.left) + " " + empty + " " + r.data + " " + close;
		}
	}
}















