public class MinHashAccuracy {
    public static void main(String[] args) {
        int[] numPermutationsSet={400,600,800};
        double[] errorsSet={0.04,0.07,0.09};
        for (int numPermutation:numPermutationsSet
        ) {
            for (double error:errorsSet
            ) {
                MinHashAccuracy.accuracy("./resources/space", numPermutation, error);
            }
        }
    }
    public static int accuracy(String folder, int numPermutations, double error) {
        MinHashSimilarities mhs= new MinHashSimilarities(folder, numPermutations);
        String[] allDocs = mhs.minHash.allDocs;

        double exJac;
        double appJac;
        String f1;
        String f2;
        int differCount=0;

        for (int i = 0; i <allDocs.length ; i++) {
            for (int j = i; j < allDocs.length; j++) {
                f1=allDocs[i];
                f2=allDocs[j];
                exJac=mhs.exactJaccard(f1, f2);
                appJac=mhs.approximateJaccard(f1,f2);
                if (exJac-appJac>error || appJac-exJac>error){
                    differCount+=1;
                }
            }
        }

        System.out.println("Number of pairs exceeding the error margin: "+differCount);
        return differCount;
    }
}
