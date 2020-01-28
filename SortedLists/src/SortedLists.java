//Craig Glassbrenner

import java.io.*;
import java.util.*;

public class SortedLists<T1 extends Comparable <? super T1>, T2 extends Comparable <? super T2>> {
	
	private class Node {
		
		private T1 data1;
		private T2 data2;
		private Node nextT1;
		private Node nextT2;
		private Node prevT1;
		private Node prevT2;
		
		private Node( T1 d1, T2 d2, Node n1, Node n2, Node p1, Node p2) {
			
			data1 = d1;
			data2 = d2;
			nextT1 = n1;
			nextT2 = n2;
			prevT1 = p1;
			prevT2 = p2;
		}
	}
	
	private Node head1;
	private Node head2;
	
	public SortedLists() {
		head1 = null;
		head2 = null;
	}
	
	public void insert( T1 d1, T2 d2) {
		//a new element (d1,d2) is inserted so both listT1 and listT2 remain sorted
		//only one new Node is created by this function
		//duplicates are allowed
		
		if(head1 == null) {
			head1 = new Node(d1, d2, null, null, null, null);
			head2 = head1;
			
		} else if (head1.data1.compareTo(d1) <= 0 && head2.data2.compareTo(d2) <= 0 ) {
			Node newNode = new Node(d1, d2, head1, head2, null, null);
			newNode.nextT1.prevT1 = newNode;
			newNode.nextT2.prevT2 = newNode;
			head1 = newNode;
			head2 = head1;
			
		} else if(head2.data2.compareTo(d2) <= 0 ) {
			Node curNode = head1;
			while(curNode.nextT1 != null && curNode.nextT1.data1.compareTo(d1) > 0) {
				curNode = curNode.nextT1;
			}
			
			Node newNode = new Node(d1, d2, curNode.nextT1, head2, curNode, null);
			
			if(newNode.nextT1 != null) {
				newNode.nextT1.prevT1 = newNode;
				
			} if(newNode.nextT2 != null) {
				newNode.nextT2.prevT2 = newNode;
				
			}

			curNode.nextT1 = newNode;
			head2 = newNode;
			
		} else if(head1.data1.compareTo(d1) <= 0) {
			Node curNode = head2;
			while(curNode.nextT2 != null && curNode.nextT2.data2.compareTo(d2) > 0) {
				curNode = curNode.nextT2;
			}
			
			Node newNode = new Node(d1, d2, head1, curNode.nextT2, null, curNode);
			
			if(newNode.nextT1 != null) {
				newNode.nextT1.prevT1 = newNode;
				
			} if(newNode.nextT2 != null) {
				newNode.nextT2.prevT2 = newNode;
				
			}

			curNode.nextT2 = newNode;
			head1 = newNode;
			
		} else {
			Node curNode = head1;
			Node curNode2 = head2;
			
			while(curNode.nextT1 != null && curNode.nextT1.data1.compareTo(d1) > 0) {
				curNode = curNode.nextT1;
			}
			
			while(curNode2.nextT2 != null && curNode2.nextT2.data2.compareTo(d2) > 0) {
				curNode2 = curNode2.nextT2;
			}
			
			Node newNode = new Node(d1, d2, curNode.nextT1, curNode2.nextT2, curNode, curNode2);
			
			if(newNode.nextT1 != null) {
				newNode.nextT1.prevT1 = newNode;
				
			} if(newNode.nextT2 != null) {
				newNode.nextT2.prevT2 = newNode;
				
			}
			curNode.nextT1 = newNode;
			curNode2.nextT2 = newNode;
		}
	}
	
	public void remove( T1 d1, T2 d2 ) {
		//remove all occurences of elements that match (d1, d2)
		if(head1 == null) {
			return;
		}
		while(head1.data1.compareTo(d1) == 0 && head1.data2.compareTo(d2) == 0) {
			if(head1.nextT1 != null) {
				head1.nextT1.prevT1 = null;
				head1 = head1.nextT1;
			} if(head1.nextT1 == null) {
				head1 = null;
				break;
			}
		}
		
		while(head2.data1.compareTo(d1) == 0 && head2.data2.compareTo(d2) == 0) {
			if(head2.nextT2 != null) {
				head2.nextT2.prevT2 = null;
				head2 = head2.nextT2;
				
			} if(head2.nextT2 == null) {
				head2 = null;
				break;
			}
		}
		if(head1 != null) {
			Node prevNode = head1;
			Node curNode1 = head1.nextT1;
			
			while(curNode1 != null) {
				if(curNode1.data1.compareTo(d1) == 0 && curNode1.data2.compareTo(d2) == 0) {
					
					if(curNode1.nextT1 != null) {
						curNode1.nextT1.prevT1 = prevNode;
						prevNode.nextT1 = curNode1.nextT1;
						
					} else {
						prevNode.nextT1 = curNode1.nextT1;
						
					}
				}
				else {
					prevNode = prevNode.nextT1;
				}
				
				curNode1 = curNode1.nextT1;
			}
			
			Node prevNode2 = head2;
			Node curNode2 = head2.nextT2;
			
			while(curNode2 != null) {
				if(curNode2.data2.compareTo(d2) == 0 && curNode2.data1.compareTo(d1) == 0) {
					if(curNode2.nextT2 != null) {
						curNode2.nextT2.prevT2 = prevNode2;
						prevNode2.nextT2 = curNode2.nextT2;
						
					} else {
						prevNode2.nextT2 = curNode2.nextT2;
					}
				}
				else {
					prevNode2 = prevNode2.nextT2;
				}
				
				curNode2 = curNode2.nextT2;
			}
		}
		else {
			return;
		}
	}

	
	public LinkedList<T2> findT2s( T1 d1) {
		//return a LinkedList of T2 values for each T1 value that matches d1
		LinkedList<T2> type2 = new LinkedList<T2>();
		
		Node curNode = head2;
		
		while(curNode != null) {
			if(curNode.data1.compareTo(d1) == 0) {
				type2.add(curNode.data2);	
			}
			
			curNode = curNode.nextT2;
		}
		
		return type2;
	}
	
	public LinkedList<T1> findT1s(T2 d2) {
		//return an array of T1 values for each T2 value that matches d2
		//return null if no matches are found
		LinkedList<T1> type1 = new LinkedList<T1>();
		
		Node curNode = head1;
		
		while(curNode != null) {
			if(curNode.data2.compareTo(d2) == 0) {
				type1.add(curNode.data1);
			}
			
			curNode = curNode.nextT1;
		}
		
		return type1;
	}
	
	private class T1Iterator implements Iterator<String> {
		
		private Node curNode;
		
		public T1Iterator() {
			curNode = head1;
		}
		
		public boolean hasNext() {
			return curNode != null;
		}
		
		public String next() {
			String toReturn;
			
			if( !hasNext() ) {
				throw new NoSuchElementException();
			} else {
				toReturn = (String) curNode.data1 + " " + (String) curNode.data2;
			}
			
			curNode = curNode.nextT1;
			return toReturn;
		}
		
		public void remove() {
			//optional method not implemented
			
		}
	}
	
	public Iterator<String> T1_Order() {
		//return a new iterator T1Iterator object
		return new T1Iterator();
	}
	
	private class T2Iterator implements Iterator<String> {
		
		private Node curNode;
		
		public T2Iterator() {
			curNode = head2;
		}
		
		public boolean hasNext() {
			return curNode != null;
		}
		
		public String next() {
			String toReturn;
			
			if(!hasNext()) {
				throw new NoSuchElementException();
			} else {
				toReturn = (String) curNode.data1 + " " + (String) curNode.data2;
			}
			
			curNode = curNode.nextT2;
			return toReturn;
		}
		
		public void remove() {
			//optional method not implemented
			
		}
	}
	
	public Iterator<String> T2_Order() {
		//return a new iterator T1Iterator object
		return new T2Iterator();
	}
}














