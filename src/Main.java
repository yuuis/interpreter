import newlang3.LexicalAnalyzer;
import newlang3.LexicalAnalyzerImpl;
import newlang3.LexicalType;
import newlang3.LexicalUnit;

import java.io.File;
import java.io.FileReader;
import java.io.PushbackReader;

public class Main {
    public static void main(String[] args) {
        final String SOURCE_PATH = "test1.bas";
        try {
            FileReader fileReader = new FileReader(new File(SOURCE_PATH));
            try {
                LexicalUnit lexicalUnit;
                PushbackReader reader  = new PushbackReader(fileReader);
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl(reader);
                while(true) {
                    lexicalUnit = lexicalAnalyzer.get();
                    System.out.println(lexicalUnit);
                    if(lexicalUnit.getType() == LexicalType.EOF) break;
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    fileReader.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
