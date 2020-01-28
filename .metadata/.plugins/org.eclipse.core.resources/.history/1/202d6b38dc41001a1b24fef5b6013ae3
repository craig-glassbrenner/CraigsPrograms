//Craig Glassbrenner
import java.io.*;
public class HuffmanOutputStream  {
	//add additional private variables as needed
	//do not modify the public methods signatures or add public methods
	DataOutputStream d;
	private int count = 0;
	private char[] numArr = new char[8];
	
	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);
		}
		catch (IOException e) {
    	  
		}
	}
	
	public void writeBit(char bit) throws IOException {
		//PRE:bit == '0' ||  bit == '1'
		count++;
		numArr[count-1] = bit;
		
		if(count == 8) {
			int num = 0;
			for(int i=0; i < numArr.length; i++) {
				if(numArr[i] == '1') {
					num = num + (int)Math.pow(2, 7-i);
				}
			}
			
			d.writeByte(num);
			count = 0;
		}
	}
	
	public void close() throws IOException {
	    //write final byte (if needed)
	    //close the DataOutputStream
		if( count > 0) {
			while(count < 8) {
				numArr[count] = '0';
				count ++;
			}
			count = 8;
			int num = 0;
			for(int i=0; i < numArr.length; i++) {
				if(numArr[i] == '1') {
					num = num + (int)Math.pow(2, 7-i);
				}
			}
			d.writeByte(num);
			count = 0;
		}
		d.close();
	}
}