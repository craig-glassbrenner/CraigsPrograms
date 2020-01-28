//Craig Glassbrenner
import java.io.*;

public class HuffmanDecode {
	
	private char nonLeaf = 0;
	
	public HuffmanDecode(String in, String out) throws IOException {
		//implements the Huffman Decode Algorithm
		//Add private methods and instance variables as needed
		HuffmanInputStream input = new HuffmanInputStream(in);
		String t = input.getTree();
		for(int i=0; i < t.length(); i++) {
			if(nonLeaf == 0 && (t.charAt(i) == t.charAt(i+1))) {
				nonLeaf = t.charAt(i);
			}
		}
		System.out.println(nonLeaf);
		
		HuffmanTree theTree = new HuffmanTree(input.getTree(), nonLeaf);	
		int numChars = input.getTotalChars();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));
		
		int numWritten = 0;
		theTree.moveToRoot();
		while(numWritten < numChars) {
			int bit = input.readBit();
			if(bit == 0) {
				theTree.moveToLeft();
			} else {
				theTree.moveToRight();
			}
			
			if(theTree.atLeaf()) {
				bw.write(theTree.current());
				numWritten++;
				theTree.moveToRoot();
			}
		}
		System.out.println(numWritten);
		bw.close();
	}
	
	public static void main(String args[]) throws IOException {
		//args[0] is the name of a input file (a file created by Huffman Encode)
		//args[1] is the name of the output file for the uncompressed file
        new HuffmanDecode(args[0], args[1]);
        //do not add anything here
    }
}