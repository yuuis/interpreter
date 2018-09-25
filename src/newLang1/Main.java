package newLang1;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        final String SORCE_PATH = "test1.bas";
        File file;
        Reader fileReader;

        // read file
        try {
            file = new File(SORCE_PATH);
            fileReader = new FileReader(file);
        } catch(IOException e) {
            System.out.println(SORCE_PATH + " is not found.");
            return;
        }

        // read char and print it until eof
        try {
            for(int i = 0; i < file.length(); i++) {
                System.out.print((char)fileReader.read());
            }
        } catch (IOException e) {
            System.out.println("io error occured.");
        }

        // close file
        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("file cant close.");
        }
    }
}
