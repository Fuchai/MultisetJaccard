
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Song
 *
 */
public class MinHash {
	File folder;
	int numPermutations;
	int numTerms;
	// Number of hash functions
	public int numHashes;
	// Set of hash functions
	public HashFunctionRan hashFunction;

	Preprocessing pre = new Preprocessing();

	List<String> allTerms;
	
	HashMap<String, Integer> fileList;

	/**
	 * 
	 * @param folder
	 * @param numPermutations
	 * @throws IOException
	 */
	public MinHash(String folder, int numPermutations) throws IOException {
		this.folder = new File(folder);
		this.numPermutations = numPermutations;
		hashFunction = new HashFunctionRan();
		allTerms = allTerms();
		numTerms = numTerms();
		fileList = fileList();
	}
	
	private HashMap<String, Integer> fileList() {
		File[] contents = folder.listFiles();
		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isFile()) {
				map.put(contents[i].getName(), i);
			}
		}
		return map;
	}

	/**
	 * 
	 * @return
	 */
	public String[] allDocs() {
		return folder.list();
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public int[] minHashSig(String fileName) throws IOException {
		String[] words = pre.process(folder + File.separator + fileName);
		int hashVal;
		int[] minHashVals = new int[numPermutations];
		Arrays.fill(minHashVals, Integer.MAX_VALUE);

		for (int j = 0; j < words.length; j++) {
			for (int i = 0; i < numPermutations; i++) {
				hashVal = hashFunction.hash(words[j]);
				if (hashVal < minHashVals[i])
					minHashVals[i] = hashVal;
			}
		}

		return minHashVals;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public int[][] minHashMatrix() throws IOException {
		File[] contents = folder.listFiles();
		int[][] minHashMatrix = new int[contents.length][numPermutations];

		int[] doc;
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isFile()) {
				doc = minHashSig(contents[i].getName());

				for (int j = 0; j < numPermutations; j++) {
					minHashMatrix[i][j] = doc[j];
				}
			}
		}
		return minHashMatrix;
	}

	public HashMap<String, Integer> termDocumentFrequency(String fileName) throws IOException {
		HashMap<String, Integer> map = new HashMap<>();
		String[] words = pre.process(folder + File.separator + fileName);
		for (int i = 0; i < allTerms.size(); i++) {
			map.put(allTerms.get(i), 0);
		}

		for (int i = 0; i < words.length; i++) {
			map.put(words[i], map.get(words[i]) + 1);
		}

		return map;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public int[][] termDocumentMatrix() throws IOException {
		File[] contents = folder.listFiles();
		int[][] termDocumentMatrix = new int[contents.length][numTerms];

		int[] doc = new int[numTerms];
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isFile()) {
				HashMap<String, Integer> temp = termDocumentFrequency(contents[i].getName());
				for (int j = 0; j < numTerms; i++) {
					doc[j] = temp.get(j);
				}

				for (int j = 0; j < numPermutations; j++) {
					termDocumentMatrix[i][j] = doc[j];
				}
			}
		}
		return termDocumentMatrix;
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public int numTerms() throws FileNotFoundException {
		return allTerms.size();
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<String> allTerms() throws FileNotFoundException {
		List<String> s = new ArrayList<String>();
		File[] contents = folder.listFiles();

		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isFile()) {
				String[] words = pre.process(contents[i].getName());
				for (int j = 0; j < words.length; j++) {
					if (!s.contains(words[j]))
						s.add(words[j]);
				}
			}
		}
		return s;
	}

	/**
	 * 
	 * @return
	 */
	public int numPermutations() {
		return numPermutations;
	}

	/**
	 * 
	 * @param filename
	 */
	public int getIndex(String filename) {
		return fileList.get(filename);
	}

	/**
	 * 
	 * @author Song
	 *
	 */
	private class HashFunctionRan extends HashFunction {
		int prime = getPrime(numPermutations);
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
