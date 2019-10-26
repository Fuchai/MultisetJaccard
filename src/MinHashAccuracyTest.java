import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinHashAccuracyTest {

    @Test
    void accuracy() {
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
}