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
        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z')) {
                target += c;
                continue;
            }
            reader.unread(ci);
            break;
        }
        // work in process
        return new LexicalUnit();
    }

    public boolean expect(LexicalType type) {
        return true;
    }

    public void unget(LexicalUnit token) {}
}
