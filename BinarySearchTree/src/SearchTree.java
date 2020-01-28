
public class SearchTree<T extends Comparable<? super T>> {
	
	private class Node {
		private Node left;
		private Node right;
		private T data;
		private int count;
		
		private Node(Node l, T d, Node r) {
			left = l;
			data = d;
			right = r;
			count = 1;
		}
	}
	private Node root;
	
	public SearchTree() {
		root = null;
	}
	
	public void insert(T d) {
		root = insert(root, d);
	}
	
	private Node insert(Node r, T d) {
		if(r == null) {
			return new Node(null, d, null);
		} if(r.data.compareTo(d) > 0) {
			r.left = insert(r.left, d);
		} else if(r.data.compareTo(d) < 0) {
			r.right = insert(r.right, d);
		} else {
			r.count++;
		}
		return r;
	}
	
	public void delete(T d) {
		root = delete(root, d);
	}
	
	private Node delete(Node r, T d) {
		if(r == null) {
			return null;
		}
		Node toReturn = r;
		if(r.data.compareTo(d) == 0) {
			r.count--;
			if(r.count == 0) {
				if(r.left == null) {
					toReturn = r.right;
				} else if(r.right == null) {
					toReturn = r.left;
				} else {
					r.left = replace(r.left, r);
				}
			}
		} else if(r.data.compareTo(d) > 0) {
			r.left = delete(r.left, d);
		} else {
			r.right = delete(r.right, d);
		}
		return toReturn;
			
	}
	
	private Node replace(Node r, Node repHere) {
		if(r.right != null) {
			r.right = replace(r.right, repHere);
			return r;
		}
		else {
			repHere.data = r.data;
			repHere.count = r.count;
			return r.left;
		}
	}
}
