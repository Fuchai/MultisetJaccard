import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BandSizeCalculatorTest {

    @Test
    void approximateR() {
        System.out.println(BandSizeCalculator.approximateR(200,0.9));
        System.out.println(BandSizeCalculator.bestFactorR(200,0.9));
    }
}