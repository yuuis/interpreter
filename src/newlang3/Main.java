package newlang3;

public class Main {
    public static void main(String[] args) {
        final String SORCE_PATH = "/Users/yuuis/tut/after_semester_3/practical_programming/interpreter/test1.bas";

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
