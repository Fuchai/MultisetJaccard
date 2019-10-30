import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NearDuplicatesTest {

    @Test
    void nearDuplicateDetector() {
        // you must use the F17PA2 dataset.
        // I have assumed that the conditional positive has 8 for each text.
        // Or else you must do exact jaccard for all, which is certainly doable.
        String folderPath="./resources/nearduplicatetest";
        File folder=new File(folderPath);
        NearDuplicates nd = new NearDuplicates(folderPath, 100, 0.8);
        System.out.println("Near Duplicate Constructed");
        File[] allFiles=folder.listFiles();
        ArrayList<File> originalFiles = new ArrayList<>();
        for (File file:allFiles
             ) {
            if (!file.getName().contains("copy")){
                originalFiles.add(file);
            }
        }
        double truePositive;
        double falsePositive;
        double cumulativePrecision=0;
        double cumulativeRecall=0;
        double trueSim=0;
        int positiveCnt=0;
        ArrayList<String> positives;
        for (File originFile: originalFiles){
            truePositive=0;
            falsePositive=0;
            positives=nd.nearDuplicateDetector(originFile.getName());
            for (String fname:positives
                 ) {
                positiveCnt++;
                if (fname.contains(originFile.getName())){
                    truePositive++;
                }else{
                    falsePositive++;
                }
                trueSim+=nd.minSim.exactJaccard(originFile.getName(), fname);
            }
            if (truePositive+falsePositive!=0){
                cumulativePrecision+= truePositive/(truePositive+falsePositive);
                cumulativeRecall+= truePositive/8;
            }
        }
        System.out.println("Average precision:"+cumulativePrecision/originalFiles.size());
        System.out.println("Average recall:"+cumulativeRecall/originalFiles.size());
        System.out.println("Average true Jaccard:"+trueSim/positiveCnt);

    }
}