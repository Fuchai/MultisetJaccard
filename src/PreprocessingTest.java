import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PreprocessingTest {
    @org.junit.jupiter.api.Test
    public void process() throws FileNotFoundException {
        String filename="./resources/hello";
        String[] words=Preprocessing.process(filename);
        for (String word:words
             ) {
            assertTrue(!word.equals("the"));
            assertTrue(word.length()>2);
        }
    }
}