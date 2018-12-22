package newlang3;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        final String SOURCE_PATH = "test1.bas";
        try(FileInputStream fileInputStream = new FileInputStream(SOURCE_PATH)) {
            LexicalUnit lexicalUnit;
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl(fileInputStream);
            while(true) {
                lexicalUnit = lexicalAnalyzer.get();
                System.out.println(lexicalUnit);
                if(lexicalUnit.getType() == LexicalType.EOF) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
