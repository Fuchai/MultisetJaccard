public class LSH {

    int[][] mhm;
    String[] docNames;
    int bands;

    public LSH(int[][] minHashMatrix, String[] docNames, int bands){
        this.mhm=minHashMatrix;
        this.docNames=docNames;
        this.bands=bands;
    }

    public String[] nearDuplicatesOf(String docName){
        return null;
    }
}
