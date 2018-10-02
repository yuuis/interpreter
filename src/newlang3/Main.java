package newlang3;

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

        //
        try {
            LexicalUnit lexicalUnit;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl(fileReader);
            while(true) {
                lexicalUnit = lexicalAnalyzer.get();
                if(lexicalUnit.getType() == LexicalType.EOF) {
                    break;
                }
                System.out.println(lexicalUnit);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // close file
        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("file cant close.");
        }
    }
}
