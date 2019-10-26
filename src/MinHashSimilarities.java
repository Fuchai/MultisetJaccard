import static java.lang.Math.max;
import static java.lang.Math.min;

public class MinHashSimilarities {
    boolean debug=false;

    MinHash minHash;
    static int[][] termDocMatrix;
    static int[][] mhMatrix;

    public MinHashSimilarities(String folder, int numPermutations) {
        minHash = new MinHash(folder, numPermutations);
        termDocMatrix= minHash.termDocMatrix();
        mhMatrix=minHash.minHashMatrix();
    }

    public double exactJaccard(String file1, String file2){
        int f1index=minHash.getIndex(file1);
        int f2index=minHash.getIndex(file2);

        int[] f1TermCount=termDocMatrix[f1index];
        int[] f2TermCount=termDocMatrix[f2index];

        if(debug){
            assert (f1TermCount.length==f2TermCount.length);
        }

        double sumMin=0;
        double sumMax=0;
        for (int i = 0; i < f1TermCount.length; i++) {
            int c1 = f1TermCount[i];
            int c2 = f2TermCount[i];
            sumMin+=min(c1,c2);
            sumMax+=max(c1,c2);
        }
        return sumMin/sumMax;
    }

    public double approximateJaccard(String file1, String file2){
        // when the min hash under pi of two files are not equal
        // then whichever word that has the lower hash does not exist in both
        // this word is sampled unbiasedly

        int f1index=minHash.getIndex(file1);
        int f2index=minHash.getIndex(file2);
        int[] minHashSig1 = mhMatrix[f1index];
        int[] minHashSig2 = mhMatrix[f2index];
        if (debug){
            assert (minHashSig1.length==minHashSig2.length);
        }

        double sumMin=0;
        double sumMax=0;

        for (int i = 0; i <minHashSig1.length ; i++) {
            int word1=minHashSig1[i];
            int word2=minHashSig2[i];
            int freq1=termDocMatrix[f1index][word1];
            int freq2=termDocMatrix[f2index][word2];
            if (word1==word2) {
                sumMin+=min(freq1,freq2);
                sumMax+=max(freq1,freq2);
            }else if(word1<word2){
                // then document 2 cannot have word 1
                if(debug){
                    assert(termDocMatrix[f2index][word1]==0);
                }
                sumMax+=freq1;
            }else if(word2 < word1){
                if (debug){
                    assert(termDocMatrix[f1index][word2]==0);
                }
                sumMax+=freq2;
            }
        }
        return sumMin/sumMax;
    }

    public int[] minHashSig(String filename){
        int findex=minHash.getIndex(filename);
        int[] minHashSig = mhMatrix[findex];
        return minHashSig;
    }
}
