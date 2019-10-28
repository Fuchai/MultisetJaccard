import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preprocessing {
    private String folder;
    public Preprocessing(String folder) {
        this.folder=folder;
    }

    public String[] process(String fileName) throws FileNotFoundException {
        File f = new File(folder + File.separator + fileName);
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
