package newlang3;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        final String SORCE_PATH = "test1.bas";

        try {
            LexicalUnit lexicalUnit;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl(SORCE_PATH);
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

    }
}
