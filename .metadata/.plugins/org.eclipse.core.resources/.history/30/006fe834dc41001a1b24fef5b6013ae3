//Craig Glassbrenner
import java.io.*;

//Nonleaf: B1 = + , B2 = &, B3 = &
public class HuffmanEncode {
	
		private String oneEncoding;
		private String zeroEncoding;
		private char nonLeaf = 0;
	
	   	public HuffmanEncode(String in, String out) throws IOException {
	   		//Implements the Huffman encoding algorithm
	   		//Add private methods and instance variables as needed
		   int[] frequencies = getFrequencies(in);
		   BinaryHeap<Integer, HuffmanTree> heap = new BinaryHeap<Integer, HuffmanTree>();
		   
		   for(int i=0; i < frequencies.length; i++) {
			   if(frequencies[i] != 0) {
				   HuffmanTree t = new HuffmanTree((char) i);
				   heap.insert(frequencies[i], t);
//				   System.out.println((char) i + ": " + frequencies[i]);
			   } else {
				   if(i >= 33 && nonLeaf == 0) {
					   nonLeaf = (char) i;
				   }
			   }
		   }
		   System.out.println(nonLeaf);
	
		  HuffmanTree theTree = buildTree(heap);
		  String rep = theTree.toString();
//		  System.out.println(rep);
		  
		  String[] encodings = theTree.pathsToLeaves();
		  zeroEncoding = theTree.zeroEncode;
		  oneEncoding = theTree.oneEncode;
		  
//		  int numUnique = 0;
//		  for(int j=0; j < encodings.length; j++) {
//			  if(encodings[j].compareTo("0") != 0 && encodings[j].compareTo("1") != 0) {
//				  System.out.println();
//				  System.out.print(encodings[j]+ ": ");
//				  numUnique ++;
//			  } else {
//				  System.out.print(encodings[j]);
//			  }
//		  }
//		  System.out.println();
//		  System.out.println(numUnique);
//		  System.out.println();
		  
		  int sum = 0;
		  for(int i=0; i < frequencies.length; i++) {
			  sum = sum + frequencies[i];
		  }
		  System.out.println(sum);
		  
		  HuffmanOutputStream output = new HuffmanOutputStream(out, rep, sum);
		  writeToFile(in, output, encodings);
	   	}
	   	
	   	private void writeToFile(String filename, HuffmanOutputStream out, String[] en) throws IOException {
	   		BufferedReader br = new BufferedReader(new FileReader(filename));
	   		
	   		char c = (char) br.read();

	   		while(c >= 0 && c <= 127) {
	   			if(c == '1') {
	   				for(int j=0; j < oneEncoding.length(); j++) {
	   					if(oneEncoding.charAt(j) == '0') {
	   						out.writeBit('0');
	   					} else {
	   						out.writeBit('1');
	   					}
	   				}
	   			} else if(c == '0') {
	   				for(int j=0; j < zeroEncoding.length(); j++) {
	   					if(zeroEncoding.charAt(j) == '0') {
	   						out.writeBit('0');
	   					} else {
	   						out.writeBit('1');
	   					}
	   				}
	   			} else {
		   			for(int i=0; i < en.length;) {
		   				if(Character.toString(c).compareTo(en[i]) == 0) {
		   					i++;
		   					while(en[i].compareTo("0") == 0 || en[i].compareTo("1") == 0) {
		   						if(en[i].compareTo("0") == 0) {
		   							out.writeBit('0');
		   						} else {
		   							out.writeBit('1');
		   						}
		   						i++;
		   						
		   						if( i >= en.length) {
		   							break;
		   						}
		   					}
		   					break;
		   				}
		   				else {
		   					i++;
		   				}
		   			}
	   			}
	   			c = (char) br.read();
	   		}
	   		out.close();
	   		br.close();
	   	}
	   	
	   	private HuffmanTree buildTree(BinaryHeap<Integer, HuffmanTree> h) {
	   		
	   		while(h.getSize() > 1) {
		   		HuffmanTree a = h.getMinOther();
		   		int key1 = h.getMinKey();
		   		h.removeMin();
		   		
		   		HuffmanTree b = h.getMinOther();
		   		int key2 = h.getMinKey();
		   		h.removeMin();
		   		
		   		HuffmanTree combine = new HuffmanTree(a, b, nonLeaf);
		   		
		   		if(h.getSize() < 1) {
		   			return combine;
		   		} else {
		   			h.insert(key1+key2, combine);
		   		}
	   		}
	   		
	   		return new HuffmanTree();
	   	}
	   	
	   	private int[] getFrequencies(String filename) throws IOException {
	   		int[] freq = new int[128];
	   		for(int j=0; j< freq.length; j++) {
	   			freq[j] = 0;
	   		}
	   		
	   		BufferedReader br = new BufferedReader(new FileReader(filename));
	   		
	   		int c = br.read();
	   		
	   		while(c >= 0 && c <= 127) {
	   			freq[c] = freq[c] + 1;
	   			c = br.read();
	   		}
	   		
	   		br.close();
	   		return freq;
	   	}
	   	
	   	public static void main(String args[]) throws IOException {
	   		//args[0] is the name of the file whose contents should be compressed
	   		//args[1] is the name of the output file that will hold the compressed
	   		//content of the input file
	   		new HuffmanEncode(args[0], args[1]);
	   		//do not add anything here
	   	} 
}