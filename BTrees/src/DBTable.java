//Craig Glassbrenner, CS340
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class DBTable {
	private RandomAccessFile rows; // the file stores the rows in the table
	private long free; //head of the free list space for rows
	private int numOtherFields;
	private int otherFieldLengths[];
	
	public BTree tree;
	private String treeFileName;
	private int sizeOfRow;

	private int smallestKey;
	private int largestKey;
	private boolean firstInsert = true;
	
	private class Row {
		private int keyField;
		private char otherFields[][];
		
		private Row(int k, char fields[][]) {
			//Constructor for a new Row
			keyField = k;
			otherFields = fields;
		}
		
		private Row(long addr) throws IOException {
			//Constructor for row that exists and is stored in a file
			rows.seek(addr);
			keyField = rows.readInt();
			
			otherFields = new char[numOtherFields][];
			for(int i=0; i < numOtherFields; i++) {
				otherFields[i] = new char[otherFieldLengths[i]];
				for(int j=0; j < otherFieldLengths[i]; j++) {
					otherFields[i][j] = rows.readChar();
				}
			}
		}
		
		private void writeRow(long addr) throws IOException {
			//writes the node to the file at location addr
			rows.seek(addr);
			rows.writeInt(keyField);
			for(int i=0; i < otherFields.length; i++) {
				for(int j=0; j < otherFields[i].length; j++) {
					rows.writeChar(otherFields[i][j]);
				}
			}
		}
	}
	
	public DBTable(String filename, int fl[], int bsize) throws IOException {
		/*
		Use this constructor to create a new DBTable.
		filename is the name of the file used to store the table
		fL is the lengths of the otherFields
		fL.length indicates how many other fields are part of the row
		bsize is the block size. It is used to calculate the order of the B+Tree
		A B+Tree must be created for the key field in the table
		If a file with name filename exists, the file should be deleted before the
		new file is created. */
		File path = new File(filename);
		if(path.exists()) {
			path.delete();
		}
		rows = new RandomAccessFile(path, "rw");
		numOtherFields = fl.length;
		otherFieldLengths = fl;
		sizeOfRow = 4;
		
		rows.seek(0);
		rows.writeInt(numOtherFields);
		for(int i=0; i < otherFieldLengths.length; i++) {
			rows.writeInt(otherFieldLengths[i]);
			sizeOfRow = sizeOfRow + (2*otherFieldLengths[i]);
		}
		
		free = 0;
		rows.writeLong(free);
		
		treeFileName = "btreeFile";
		tree = new BTree(treeFileName, bsize);
	}
	
	public DBTable(String filename) throws IOException {
		//Open an existing DBTable
		File path = new File(filename);
		rows = new RandomAccessFile(path, "rw");
		rows.seek(0);
		numOtherFields = rows.readInt();
		
		otherFieldLengths = new int[numOtherFields];
		for(int i=0; i < numOtherFields; i++) {
			otherFieldLengths[i] = rows.readInt();
		}
		free = rows.readLong();
		treeFileName = "btreeFile";
		tree = new BTree(treeFileName);
		
	}
	
	public boolean insert(int key, char fields[][]) throws IOException {
		//PRE: the length of each row is fields matches the expected length
		/* If a row with the key is not in the table, the row is added and the method returns true otherwise the row is not added and the method returns false.
		The method must use the B+tree to determine if a row with the key exists.
		If the row is added the key is also added into the B+tree. */
		long addr = getFree();
		
		if(tree.insert(key, addr)) {
			Row newRow = new Row(key, fields);
			newRow.writeRow(addr);
			
			if(firstInsert) {
				smallestKey = key;
				largestKey = key;
			} else {
				if(key < smallestKey) {
					smallestKey = key;
				} else if(key > largestKey) {
					largestKey = key;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	private long getFree() {
		long toReturn;
		if(free == 0) {
			toReturn = sizeOfRow;
			free = sizeOfRow*2;
		} else {
			toReturn = free;
			free = free + sizeOfRow;
			firstInsert = false;
		}
		
		return toReturn;
	}
	
	public boolean remove(int key) {
		/*
		If a row with the key is in the table it is removed and true is returned otherwise false is returned.
		The method must use the B+Tree to determine if a row with the key exists.
		If the row is deleted the key must be deleted from the B+Tree */
		
		return false;
	}
	
	public LinkedList<String> search(int key) throws IOException { 
		/*
		If a row with the key is found in the table return a list of the other fields in the row.
		The string values in the list should not include the null characters.
		If a row with the key is not found return an empty list
		The method must use the equality search in B+Tree */
		long addr = tree.search(key);
		LinkedList<String> strList = new LinkedList<>();
		
		if(addr == 0) {
			return strList;
		} else {
			Row r = new Row(addr);
			
			for(int i=0; i < numOtherFields; i++) {
				String str = "";
				for(int j=0; j < otherFieldLengths[i]; j++) {
					if(r.otherFields[i][j] != '\0') {
						str = str + r.otherFields[i][j];
					}
				}
				strList.add(str);
			}
			
			return strList;
		}
		
	}
	
	public LinkedList<LinkedList<String>> rangeSearch(int low, int high) throws IOException {
		//PRE: low <= high
		/*
		For each row with a key that is in the range low to high inclusive a list of the fields (including the key) in the row is added to the list returned by the call.
		If there are no rows with a key in the range return an empty list
		The method must use the range search in B+Tree */
		LinkedList<Long> addrList = tree.rangeSearch(low, high);
		
		if(addrList.isEmpty()) {
			return new LinkedList<LinkedList<String>>();
		} else {
			LinkedList<LinkedList<String>> strList = new LinkedList<>();
			
			for(int i=0; i < addrList.size(); i++) {
				Row r = new Row(addrList.get(i));
				LinkedList<String> ind = new LinkedList<>();
				ind.add("" + r.keyField);
				
				for(int j=0; j < numOtherFields; j++) {
					String str = "";
					for(int k=0; k < otherFieldLengths[j]; k++) {
						if(r.otherFields[j][k] != '\0') {
							str = str + r.otherFields[j][k];
						}
					}
					ind.add(str);
				}
				strList.add(ind);
			}
			return strList;
		}
	}
	
	public void print() throws IOException {
		//Print the rows to standard output is ascending order (based on the keys) 
		//One row per line
		
		if(free == 0) {
			return;
		} else {
			LinkedList<LinkedList<String>> l = rangeSearch(smallestKey, largestKey);
			LinkedList<String> s;
			boolean broke = false;
			
			for(int i=0; i < l.size(); i++) {
				s = l.get(i);
				for(int j=0; j < s.size(); j++) {
					if(j < (s.size()-2) && s.get(j+2).compareTo("") == 0) {
						broke = true;
						break;
					}
					System.out.print(s.get(j) + " ");
				}
				if(!broke) {
					System.out.println();
				}
				broke = false;
			}
		}
	}
	
	public void close() throws IOException {
		//close the DBTable. The table should not be used after it is closed
		tree.close();
		rows.close();
	}
}
