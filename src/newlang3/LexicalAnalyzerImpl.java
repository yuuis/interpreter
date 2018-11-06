package newlang3;

import java.io.*;
import java.util.HashMap;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    PushbackReader reader;
    static HashMap<String, LexicalUnit> RESERVED_WORD_MAP = new HashMap<>();
    static HashMap<String, LexicalUnit> SYMBOL_MAP = new HashMap<>();

    public LexicalAnalyzerImpl(PushbackReader reader) {
        this.reader = reader;
    }

    public LexicalUnit get() throws Exception {
        while(true) {
            int ci = reader.read();
            if(ci < 0) {
                return new LexicalUnit(LexicalType.EOF);
            } else {
                char c = (char) ci;

                if((c == ' ') || (c == '\t') || (c == '\n')) { continue; }

                // when alphabet
                if((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z')) {
                    reader.unread(ci);
                    return getString();
                }

                // when number
                if(c >= '0' && c <= '9') {
                    reader.unread(ci);
                    return getNunber();
                }

                // when literal
                if(c == '"') {
                    return getLiteral();
                }

                // when symbol
                if(SYMBOL_MAP.containsKey(c + "")) {
                    return getSymbol(c);
                }

                throw new Exception("syntax error");
            }
        }
    }

    private LexicalUnit getString() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if(ci < 0) break;
            if(((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0'  && c <= '9'))) {
                target += c;
                continue;
            }
            reader.unread(ci);
            break;
        }
        if(RESERVED_WORD_MAP.containsKey(target)) {
            return RESERVED_WORD_MAP.get(target);
        } else {
            return new LexicalUnit(LexicalType.NAME, new ValueImpl(target));
        }
    }

    private LexicalUnit getNunber() throws Exception {
        String target = "";
        Boolean decimalFlag = false;

        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if(ci < 0) { break; }
            if (c >= '0' && c <= '9') {
                target += c;
                continue;
            } else if (c == '.' && !decimalFlag) {
                decimalFlag = true;
                target += c;
                continue;
            } else if(c == '.' && decimalFlag) {
                throw new Exception("syntax error");
            }
            reader.unread(ci);
            break;
        }
        if(decimalFlag) {
            return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(Double.parseDouble(target)));
        } else {
            return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(Integer.parseInt(target)));
        }
    }

    private LexicalUnit getSymbol(char c) throws Exception {
        String target = c + "";

        while(true) {
            int ci = reader.read();
            if(ci < 0) return SYMBOL_MAP.get(target);
            target += (char) ci;

            if(SYMBOL_MAP.containsKey(target)) {
                continue;
            } else {
                reader.unread(ci);
                target = target.substring(0, target.length() - 1);
                return SYMBOL_MAP.get(target);
            }
        }
    }

    private LexicalUnit getLiteral() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();
            if(ci < 0) break;
            char c = (char) ci;
            if(c != '"') {
                target += c;
                continue;
            }
            return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(target));
        }
        throw new Exception("syntax error");
    }

    static {
        RESERVED_WORD_MAP.put("IF", new LexicalUnit(LexicalType.IF));
        RESERVED_WORD_MAP.put("THEN", new LexicalUnit(LexicalType.THEN));
        RESERVED_WORD_MAP.put("ELSE", new LexicalUnit(LexicalType.ELSE));
        RESERVED_WORD_MAP.put("ELSEIF", new LexicalUnit(LexicalType.ELSEIF));
        RESERVED_WORD_MAP.put("FOR", new LexicalUnit(LexicalType.FOR));
        RESERVED_WORD_MAP.put("FORALL", new LexicalUnit(LexicalType.FORALL));
        RESERVED_WORD_MAP.put("NEXT", new LexicalUnit(LexicalType.NEXT));
        RESERVED_WORD_MAP.put("SUB", new LexicalUnit(LexicalType.FUNC));
        RESERVED_WORD_MAP.put("DIM", new LexicalUnit(LexicalType.DIM));
        RESERVED_WORD_MAP.put("AS", new LexicalUnit(LexicalType.AS));
        RESERVED_WORD_MAP.put("END", new LexicalUnit(LexicalType.END));
        RESERVED_WORD_MAP.put("WHILE", new LexicalUnit(LexicalType.WHILE));
        RESERVED_WORD_MAP.put("DO", new LexicalUnit(LexicalType.DO));
        RESERVED_WORD_MAP.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
        RESERVED_WORD_MAP.put("LOOP", new LexicalUnit(LexicalType.LOOP));
        RESERVED_WORD_MAP.put("TO", new LexicalUnit(LexicalType.TO));
        RESERVED_WORD_MAP.put("PRINT", new LexicalUnit(LexicalType.PRINT));
        RESERVED_WORD_MAP.put("WEND", new LexicalUnit(LexicalType.WEND));
        SYMBOL_MAP.put("=", new LexicalUnit(LexicalType.EQ));
        SYMBOL_MAP.put("<", new LexicalUnit(LexicalType.LT));
        SYMBOL_MAP.put(">", new LexicalUnit(LexicalType.GT));
        SYMBOL_MAP.put("<=", new LexicalUnit(LexicalType.LE));
        SYMBOL_MAP.put("=<", new LexicalUnit(LexicalType.LE));
        SYMBOL_MAP.put(">=", new LexicalUnit(LexicalType.GE));
        SYMBOL_MAP.put("=>", new LexicalUnit(LexicalType.GE));
        SYMBOL_MAP.put("<>", new LexicalUnit(LexicalType.NE));
        SYMBOL_MAP.put(".", new LexicalUnit(LexicalType.DOT));
        SYMBOL_MAP.put("+", new LexicalUnit(LexicalType.ADD));
        SYMBOL_MAP.put("-", new LexicalUnit(LexicalType.SUB));
        SYMBOL_MAP.put("*", new LexicalUnit(LexicalType.MUL));
        SYMBOL_MAP.put("/", new LexicalUnit(LexicalType.DIV));
        SYMBOL_MAP.put(")", new LexicalUnit(LexicalType.LP));
        SYMBOL_MAP.put("(", new LexicalUnit(LexicalType.RP));
        SYMBOL_MAP.put(",", new LexicalUnit(LexicalType.COMMA));
        SYMBOL_MAP.put("\n", new LexicalUnit(LexicalType.NL));
    }

    public boolean expect(LexicalType type) {
        return true;
    }

    public void unget(LexicalUnit token) {}

}
