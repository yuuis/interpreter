package newLang1;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        final String SORCE_PATH = "test1.bas";
        try {

            File file = new File(SORCE_PATH);
            Reader fileReader = new FileReader(file);
            for(int i = 0; i < file.length(); i++) {
                System.out.print((char)fileReader.read());
            }
//            while (fileReader.read() != -1) {
//                System.out.print((char)fileReader.read());
//            }

        } catch(IOException e) {
            System.out.println(SORCE_PATH + " is not found.");
        }
    }
}
