//import static java.lang.Math.max;
//import static java.lang.Math.min;
//
//public class MinHashSimilarities {
//
//    MinHash minHash;
//    int[][] termDocMatrix;
//    int[][] mhMatrix;
//
//    public MinHashSimilarities(String folder, int numPermutations) {
//        minHash = new MinHash(folder, numPermutations);
//        termDocMatrix= minHash.termDocMatrix();
//        mhMatrix=minHash.minHashMatrix();
//    }
//
//    public double exactJaccard(String file1, String file2){
//        int f1index=minHash.getIndex(file1);
//        int f2index=minHash.getIndex(file2);
//
//        int[] f1TermCount=termDocMatrix[f1index];
//        int[] f2TermCount=termDocMatrix[f2index];
//
//        assert (f1TermCount.length==f2TermCount.length);
//
//        double sumMin=0;
//        double sumMax=0;
//        for (int i = 0; i < f1TermCount.length; i++) {
//            int c1 = f1TermCount[i];
//            int c2 = f2TermCount[i];
//            sumMin+=min(c1,c2);
//            sumMax+=max(c1,c2);
//        }
//        return sumMin/sumMax;
//    }
//
//    public double approximateJaccard(String file1, String file2){
//
//    }
//
//}
