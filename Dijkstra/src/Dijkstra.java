//Craig Glassbrenner, HW6
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
	private class Item implements Comparable {

		private int distance;
		private int node;
		private int last;
		
		private Item(int d, int x, int y) {
			distance = d;
			node = x;
			last = y;
		}
		
		public int compareTo(Object z) {
			return distance - ((Item) z).distance;
		}
	}
	
	private class Vertex {
		private EdgeNode edges1;
		private EdgeNode edges2;
		//Says if Vertex is known or not, keeps track of min Distance
		private boolean k;
		private int minDistance = 0;
		
		private Vertex() {
			edges1 = null;
			edges2 = null;
		}
	}
	
	private class EdgeNode {
		private int vertex1;
		private int vertex2;
		private EdgeNode next1;
		private EdgeNode next2;
		private int weight;
		
		private EdgeNode(int v1, int v2, EdgeNode e1, EdgeNode e2, int w) {
			vertex1 = v1;
			vertex2 = v2;
			next1 = e1;
			next2 = e2;
			weight = w;
		}
	}
	
	private Vertex[] g;
	//Tally of number of Nodes Known out of total, s is just the size, arr is the array of paths as strings
	private int numNodesKnown;
	private int s;
	private String[] arr;
	
	public Dijkstra(int size) {
		g = new Vertex[size];
		s = size;
		numNodesKnown = 0;
		arr = new String[size];
	}
	
	public void addEdge(int v1, int v2, int w) {
		//PRE: v1 and v2 are legitmate vertices
		EdgeNode e;
		if(v1 < v2) {
			e = new EdgeNode(v1, v2, null, null, w);
		} else {
			int temp = v1;
			v1 = v2;
			v2 = temp;
			e = new EdgeNode(v1, v2, null, null, w);
		}
		
		if(g[v1] == null) {
			Vertex v = new Vertex();
			g[v1] = v;
			g[v1].edges1 = e;
		} else {
			EdgeNode next = g[v1].edges1;
			if(next == null) {
				g[v1].edges1 = e;
			} else {
				while(next.next1 != null) {
					next = next.next1;
				}
				next.next1 = e;
			}
		}
		if(g[v2] == null) {
			Vertex y = new Vertex();
			g[v2] = y;
			g[v2].edges2 = e;
		} else {
			EdgeNode next = g[v2].edges2;
			if(next == null) {
				g[v2].edges2 = e;
			} else {
				while(next.next2 != null) {
					next = next.next2;
				}
				next.next2 = e;
			}
		}
		

	}
	
	public void printRoutes(int j) {
		//Find and print the best routes from j to all other nodes in the graph
		//Note discussion in class of the limitation of priority queue for algorithm
		PriorityQueue<Item> p = new PriorityQueue<>();
		EdgeNode e = g[j].edges1;
		Vertex v = g[j];
		arr[j] = j + " ";
		v.k = true;
		v.minDistance = 0;
		numNodesKnown = 1;
		
		if(e != null) {
			while(e.next1 != null) {
				Item i = new Item(e.weight, e.vertex2, e.vertex1);
				p.add(i);
				
				e = e.next1;
			}
			Item n = new Item(e.weight, e.vertex2, e.vertex1);
			p.add(n);
		}
		
		EdgeNode e2 = g[j].edges2;
		if(e2 != null) {
			while(e2.next2 != null) {
				Item i = new Item(e2.weight, e2.vertex2, e2.vertex1);
				p.add(i);
				
				e2 = e2.next2;
			}
			Item m = new Item(e2.weight, e2.vertex2, e2.vertex1);
			p.add(m);
		}
		
		while(numNodesKnown < s || !p.isEmpty()) {
			Item i = p.poll();
			Vertex y = g[i.node];
			Vertex x = g[i.last];
			
			if(!y.k) {
				y.minDistance = i.distance + x.minDistance;
				arr[i.node] = arr[i.last] + i.node + " ";
				y.k = true;
				numNodesKnown++;
				
				EdgeNode e1 = y.edges1;
				EdgeNode e3 = y.edges2;
				if(e1 != null) {
					while(e1.next1 != null) {
						Item item1 = new Item(e1.weight, e1.vertex2, e1.vertex1);
						p.add(item1);
						
						e1 = e1.next1;
					}
					Item item2 = new Item(e1.weight, e1.vertex2, e1.vertex1);
					p.add(item2);
				}
				if(e3 != null) {
					while(e3.next2 != null) {
						Item item3 = new Item(e3.weight, e3.vertex2, e3.vertex1);
						p.add(item3);
						
						e3 = e3.next2;
					}
					Item item4 = new Item(e3.weight, e3.vertex2, e3.vertex1);
					p.add(item4);
				}
			} else {
				if(x.k) {
					int d = i.distance + x.minDistance;
					if(d < y.minDistance) {
						y.minDistance = d;
						arr[i.node] = arr[i.last] + i.node + " ";
					}
				}
			} if(!x.k && y.k) {
				x.minDistance = i.distance + y.minDistance;
				arr[i.last] = arr[i.node] + i.last + " ";
				x.k = true;
				numNodesKnown++;
				
				EdgeNode e1 = x.edges1;
				EdgeNode e3 = x.edges2;
				if(e1 != null) {
					while(e1.next1 != null) {
						Item item1 = new Item(e1.weight, e1.vertex2, e1.vertex1);
						p.add(item1);
						
						e1 = e1.next1;
					}
					Item item2 = new Item(e1.weight, e1.vertex2, e1.vertex1);
					p.add(item2);
				}
				if(e3 != null) {
					while(e3.next2 != null) {
						Item item3 = new Item(e3.weight, e3.vertex2, e3.vertex1);
						p.add(item3);
						
						e3 = e3.next2;
					}
					Item item4 = new Item(e3.weight, e3.vertex2, e3.vertex1);
					p.add(item4);
				}
			} else {
				int d = i.distance + y.minDistance;
				if(d < x.minDistance) {
					x.minDistance = d;
					arr[i.last] = arr[i.node] + i.last + " ";
				}
			}
		}
		
		for(int i=0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	

	 public static void main(String args[]) throws IOException {
        BufferedReader b = new BufferedReader(new FileReader(args[0]));
        String line = b.readLine();
        int numNodes = new Integer(line);
        line = b.readLine();
        int source = new Integer(line);
        System.out.println(source);
        Dijkstra g = new Dijkstra(numNodes);
        line = b.readLine();
        while (line != null) {
            Scanner scan = new Scanner(line);
            g.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
            line = b.readLine();
            scan.close();
        }
        g.printRoutes(source);
        b.close();
	 }
}
