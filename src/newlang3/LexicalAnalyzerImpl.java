package newlang3;

import java.io.*;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    PushbackReader reader;

    public LexicalAnalyzerImpl(String filePath) throws Exception {
        reader = new PushbackReader(new FileReader(new File(filePath)));
    }

    public LexicalUnit get() throws Exception{
        while(true) {
            int ci = reader.read();
            if(ci < 0) {
                return new LexicalUnit(LexicalType.EOF);
            } else {
                char c = (char) ci;

                if((c == ' ') || (c == '\t') || (c == '\r')) { continue; }
                // when alphabet
                if((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z')) {
                    return getString();
                }

                // other conditions
            }

        }

    }

    // process after the second letter of the alphabet
    private LexicalUnit getString() throws Exception{
        String target = "";

        // make target
        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if((true)) {
                System.out.println("hoge");
                target += c;
                continue;
            }
            reader.unread(ci);
            break;
        }

        // return LexicalUnit
        // LexicalUnit constructor receive LexicalType

        System.out.println(target);
        return new LexicalUnit(LexicalType.valueOf(target), new ValueImpl(target));
    }

    public boolean expect(LexicalType type) {
        return true;
    }

    public void unget(LexicalUnit token) {}

}
