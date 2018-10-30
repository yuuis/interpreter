package newlang3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class Main {
    public static void main(String[] args) {
        final String SORCE_PATH = "/Users/yuuis/tut/after_semester_3/practical_programming/interpreter/test1.bas";
        FileReader fileReader;

        try {
            LexicalUnit lexicalUnit;
            fileReader = new FileReader(new File(SORCE_PATH));
            PushbackReader reader  = new PushbackReader(fileReader);
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl(reader);
            while(true) {
                lexicalUnit = lexicalAnalyzer.get();
                System.out.println(lexicalUnit);
                if(lexicalUnit.getType() == LexicalType.EOF) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            // TODO: close of file
//            fileReader.close();
        }
    }
}
