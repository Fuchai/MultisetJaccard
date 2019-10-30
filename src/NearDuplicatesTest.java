import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NearDuplicatesTest {

    @Test
    void nearDuplicateDetector() {
        String folderPath="./resources/nearduplicatetest";
        File folder=new File(folderPath);
        NearDuplicates nd = new NearDuplicates(folderPath, 100, 0.9);
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
        ArrayList<String> positives;
        for (File originFile: originalFiles){
            truePositive=0;
            falsePositive=0;
            positives=nd.nearDuplicateDetector(originFile.getName());
            for (String fname:positives
                 ) {
                if (fname.contains(originFile.getName())){
                    truePositive++;
                }else{
                    falsePositive++;
                }
            }
            cumulativePrecision+= truePositive/(truePositive+falsePositive);
            cumulativeRecall+= truePositive/8;
        }
        System.out.println("Average precision:"+cumulativePrecision/originalFiles.size());
        System.out.println("Average recall:"+cumulativeRecall/originalFiles.size());

    }
}