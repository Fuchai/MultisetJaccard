import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NearDuplicates {
	
	final int bands;
	final int bandwidth;
	MinHashSimilarities minSim;
	double simThreshold;
	LSH lsh;
	
	/**
	 * 
	 * @param folder 			Name of the folder containing documents
	 * @param numPermutations	Number of Permutations to be used for MinHash
	 * @param simThreshold		Similarity threshold s, which is a double
	 * @throws IOException 
	 */
    public NearDuplicates(String folder, int numPermutations, double simThreshold) {
    	this.simThreshold = simThreshold;
    	bandwidth=BandSizeCalculator.bestFactorR(numPermutations, simThreshold);
		bands = numPermutations/bandwidth;
		MinHash minhash = new MinHash(folder, numPermutations);
    	minSim = new MinHashSimilarities(folder, numPermutations);
    	lsh = new LSH(minhash.minHashMatrix(), minhash.allDocs(), bands);
    }
    
    public ArrayList<String> nearDuplicateDetector(String fileName) {
    	List<String> nearDuplicates = lsh.nearDuplicatesOf(fileName);
    	ArrayList<String> sSimilar = new ArrayList<String>();
    	
		for(String str : nearDuplicates) {
			double similarity = minSim.exactJaccard(fileName, str);
			if(similarity > simThreshold) {
				sSimilar.add(str);
			} 
		}
		
		return sSimilar;
    }
    
    
}
