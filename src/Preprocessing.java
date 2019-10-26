import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preprocessing {
    public static String[] process(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner s = new Scanner(f);
        ArrayList<String> list = new ArrayList<>();

        while (s.hasNext()){
            String word=s.next();
            word=word.toLowerCase();
            word=word.replaceAll("\\.|,|;|:|'","");
            if (word.length()>2 && !word.equals("the")){
                list.add(word);
            }
        }
        String[] ret= new String[list.size()];
        ret=list.toArray(ret);
        return ret;
    }
}
