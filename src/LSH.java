import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class LSH {

    int[][] minHashMatrix;
    String[] docNames;
    int bands;
    private int numDocs;
	private int rows;
	private Map<String, String> buckets;
	private HashFunctionRan hashFunction;

    public LSH(int[][] minHashMatrix, String[] docNames, int bands){
        this.minHashMatrix=minHashMatrix;
        this.docNames=docNames;
        this.bands=bands;
        
        hashFunction = new HashFunctionRan();
        numDocs = minHashMatrix.length;
		rows = minHashMatrix[0].length / bands;
		
		buckets = new HashMap<String, String>();
        int currBand = 0;
		String hashValue = "init";
		for(int i = 0; i < numDocs; i++) { 
			for(int j = 0; j < minHashMatrix[0].length; j++) { 
				currBand = j / rows;
				hashValue = hashValue + minHashMatrix[i][j];

				if((j + 1) % rows == 0 || (j + 1) == minHashMatrix[0].length) {
					hashValue = hashFunction.hash(hashValue) + "";
					String key = currBand + "," + hashValue;
					String names = buckets.get(key);
					if (names == null) {
						names = docNames[i];
					}
					else {
						names = names + "$@#" + docNames[i];
					}
					
					buckets.put(key, names);
					hashValue = "init";
				}	
			}
		}
    }

    public ArrayList<String> nearDuplicatesOf(String docName) {
		Set<String> setDuplicates = new HashSet<String>();
		ArrayList<String> nearDuplicates = new ArrayList<String>();
		
		int docIndex = 0;
		for(int i = 0; i < numDocs; i++) {
			if(docNames[i].equals(docName)) {
				docIndex = i;
			}
		}
		
		int currBand = 0;
		String hashValue = "init";
		String[] currString;
		for(int i = 0; i < minHashMatrix[docIndex].length; i++) {
			currBand = i / rows;
			hashValue = hashValue + minHashMatrix[docIndex][i];
			
			if((i + 1) % rows == 0 || (i + 1) == minHashMatrix[docIndex].length) {
				hashValue = hashFunction.hash(hashValue) + "";
				String key = currBand + "," + hashValue;
				String names = buckets.get(key);
				if (names == null) {
					names = "";
				}
				
				if(!names.equals("")) {
					currString = names.split("$@#");
					setDuplicates.addAll(Arrays.asList(currString));
				}
				hashValue = "init";
			}	
		}
			
		nearDuplicates.addAll(setDuplicates);
		return nearDuplicates;
	}
    
    private class HashFunctionRan extends HashFunction {
		int prime = getPrime(numDocs);
		int a = ThreadLocalRandom.current().nextInt(0, prime);
		int b = ThreadLocalRandom.current().nextInt(0, prime);

		public int hash(String s) {
			return hash(s.hashCode());
		}

		private int hash(int x) {
			return mod((a * x) + b, prime);
		}

		/**
		 * get prime number bigger than n
		 * 
		 * @param n
		 * @return boolean
		 */
		private int getPrime(int n) {
			boolean found = false;

			while (!found) {
				if (isPrime(n)) {
					found = true;
				} else {
					if (n == 1 || n % 2 == 0) {
						n = n + 1;
					} else {
						n = n + 2;
					}
				}
			}
			return n;
		}

		/**
		 * return true if inputNum is prime
		 * 
		 * @param inputNum
		 * @return boolean
		 */
		private boolean isPrime(int inputNum) {
			if (inputNum <= 3 || inputNum % 2 == 0)
				return inputNum == 2 || inputNum == 3;
			int divisor = 3;
			while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
				divisor += 2;
			return inputNum % divisor != 0;
		}
	}
}