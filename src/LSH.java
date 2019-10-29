import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LSH {

    int[][] minHashMatrix;
    String[] docNames;
    int bands;
    private int numDocs;
	private int rows;
	ArrayList<MyHashTable> tables;

    public LSH(int[][] minHashMatrix, String[] docNames, int bands){
        this.minHashMatrix=minHashMatrix;
        this.docNames=docNames;
        this.bands=bands;
        
        numDocs = minHashMatrix.length;
		rows = minHashMatrix[0].length / bands;
		
		initLSH();
    }
    
    private void initLSH() {
    	tables = new ArrayList<MyHashTable>(bands);
    	for (int b = 0; b < bands; b++) { // band
			MyHashTable table = new MyHashTable(numDocs);
			for (int i = 0; i < numDocs; i++) { // column
				String key = "";
				for (int r = 0; r < rows; r++) { // row
					key = key + minHashMatrix[i][b*rows+r];
					if ((b+1) == bands && (r+1) == minHashMatrix[0].length % bands) // last
						break;
				}
				String names = table.find(key).val;
				if (names == null)
					table.add(key, docNames[i]);
				else 
					table.add(key, table.find(key) + "$#@" + docNames[i]);
			}
			tables.add(table);
		}
    }

    public ArrayList<String> nearDuplicatesOf(String docName) {
		Set<String> hashSet = new HashSet<String>();
		ArrayList<String> nearDuplicates = new ArrayList<String>();
		
		int docIndex = 0;
		for(int i = 0; i < numDocs; i++) {
			if(docNames[i].equals(docName)) {
				docIndex = i;
			}
		}
		
		String[] inSameBucket;
		for(int b = 0; b < bands; b++) { // band
			String key = "";
			for (int r = 0; r < rows; r++) { // row
				key = key + minHashMatrix[docIndex][b*rows+r];
				
				if ((b+1) == bands && (r+1) == minHashMatrix[0].length % bands) // last
					break;
			}
			String names = tables.get(b).find(key).val;
			if (names == null) {
				names = "";
			}
			
			if(!names.equals("")) {
				inSameBucket = names.split("$#@");
				hashSet.addAll(Arrays.asList(inSameBucket));
			}
		}
			
		nearDuplicates.addAll(hashSet);
		return nearDuplicates;
	}
}
