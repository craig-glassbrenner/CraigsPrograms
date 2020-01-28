//Craig Glassbrenner
import java.io.*;

public class HuffmanInputStream {
	//add additional private variables and methods as needed
	//DO NOT modify the public method signatures or add public methods
	private String tree;
	private int totalChars;
	private DataInputStream d;
	private int count = 0;
	private int[] numArr = new int[8];
    
	public HuffmanInputStream(String filename) {
		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();
		}
        catch (IOException e) {
        	
        }
        //add other initialization statements as needed
    }
    
	public int readBit() throws IOException {
		//returns the next bit is the file
		//the value returned will be either a 0 or a 1
		int i =0;
		
		if(count == 0) {
			int nextByte = d.readUnsignedByte();
			count = 8;
			
			for(i=0; i < numArr.length; i++) {
				int bit = nextByte % 2;
				numArr[7-i] = bit;
				nextByte = nextByte / 2;
			}
		}
		int toReturn = numArr[8-count];
		count--;
		return toReturn;
		
	}
	
	public String getTree() throws IOException {
        //return the tree representation read from the file
		return tree;
    }
	
	public int getTotalChars() throws IOException {
        //return the character count read from the file
		return totalChars;
    }
}
