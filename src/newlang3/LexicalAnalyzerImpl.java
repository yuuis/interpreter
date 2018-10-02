package newlang3;

import java.io.IOException;
import java.io.Reader;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {

    public LexicalAnalyzerImpl(Reader file) throws Exception {
        // read char and print it until eof
        try {
            for(int i = 0; i < file.toString().length(); i++) {
                System.out.print((char)file.read());
            }
        } catch (IOException e) {
            System.out.println("io error occured.");
        }
    }

    public LexicalUnit get() throws Exception {
        return new LexicalUnit;
    }

    public boolean expect(LexicalType type) throws Exception {
        return true;
    }

    public void unget(LexicalUnit token) throws Exception{}
}
