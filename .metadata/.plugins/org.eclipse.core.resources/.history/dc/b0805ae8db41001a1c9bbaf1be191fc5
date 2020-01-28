//Craig Glassbrenner
import java.util.*;

public class BinaryHeap <T1 extends Comparable <? super T1>, T2 extends Object>  {
	//implements a binary heap where the heap rule is the value
	//of the key in the parent node is less than 
	//or equal to the values of the keys in the child nodes
	
	private class Node {
		T1 priority;
		T2 tree;
		
		private Node(T1 k, T2 t) {
			priority = k;
			tree = t;
		}
	}
	
	private int size;
	ArrayList< Node > pq;
	
	public BinaryHeap() {
		size = 0;
		pq = new ArrayList<Node>();
	}
	
	public void removeMin() {
		//PRE: size != 0
		//removes the item at the root of the heap
		pq.remove(0);
		size--;
	}
	
	public T1 getMinKey() {
		//PRE: size != 0
		//return the priority (key) in the root of the heap
		Node root = pq.get(0);
		T1 key = root.priority;
		return key;
	}
	
	public T2 getMinOther() {
		//PRE: size != 0
		//return the other data in the root of the heap
		Node root = pq.get(0);
		T2 other = root.tree;
		return other;
	}
	
	public void insert(T1 k, T2 t) {
		//insert the priority k and the associated data into the heap
		Node toInsert = new Node(k, t);
		
		if(pq.isEmpty()) {
			pq.add(toInsert);
		} else {
			int i = 0;
			while( i < getSize()) {
				if(k.compareTo(pq.get(i).priority) <= 0) {
					pq.add(i, toInsert);
					size++;
					return;
				}
				i++;
			}		
			pq.add(i, toInsert);
			
		}
		size++;
	}
	
	public int getSize() {
		//return the number of values (key, other) pairs in the heap
		return size;
	}
}
