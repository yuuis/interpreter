package newlang3;

import java.io.*;
import java.util.HashMap;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    PushbackReader reader;
    static HashMap<String, LexicalType> RESERVED_WORD_MAP = new HashMap<>();
    static HashMap<String, LexicalType> SYMBOL_MAP = new HashMap<>();
    static HashMap<String, LexicalType> SYMBOL_INITIAL_MAP = new HashMap<>();

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
                    return getInt();
                }

                // when literal
                if(c == '"') {
                    return getLiteral();
                }

                // when symbol
                if(SYMBOL_INITIAL_MAP.containsKey(c + "")) {
                    return getSymbol(c);
                }
            }
        }
    }

    private LexicalUnit getString() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if(((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z'))) {
                target += c;
                continue;
            }
            reader.unread(ci);
            break;
        }
        if(RESERVED_WORD_MAP.containsKey(target)) {
            return new LexicalUnit(RESERVED_WORD_MAP.get(target));
        } else {
            return new LexicalUnit(LexicalType.NAME, new ValueImpl(target));
        }
    }


    private LexicalUnit getInt() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if (c >= '0' && c <= '9') {
                target += c;
                continue;
            }
            reader.unread(ci);
            break;
        }
        return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(Integer.parseInt(target)));
    }

    private LexicalUnit getSymbol(char c) throws Exception {
        String target = c + "";

        int ci = reader.read();
        char nextC = (char) ci;

        switch (c) {
            case '<':
                if (nextC == '=' || nextC == '>') {
                    target += nextC;
                    return new LexicalUnit(SYMBOL_MAP.get(target));
                } else {
                    reader.unread(ci);
                    return new LexicalUnit(SYMBOL_MAP.get(target));
                }
            case '>':
                if (nextC == '=') {
                    target += nextC;
                    return new LexicalUnit(SYMBOL_MAP.get(target));
                } else {
                    reader.unread(ci);
                    return new LexicalUnit(SYMBOL_MAP.get(target));
                }
            case '=':
                if (nextC == '<' || nextC == '>') {
                    target += nextC;
                    return new LexicalUnit(SYMBOL_MAP.get(target));
                } else {
                    reader.unread(ci);
                    return new LexicalUnit(SYMBOL_MAP.get(target));
                }
            default:
                reader.unread(ci);
                return new LexicalUnit(SYMBOL_MAP.get(target));
        }
    }

    private LexicalUnit getLiteral() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();
            char c = (char) ci;
            if(c != '"') {
                target += c;
                continue;
            }
            return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(target));
        }
    }

    static {
        RESERVED_WORD_MAP.put("IF", LexicalType.IF);
        RESERVED_WORD_MAP.put("THEN", LexicalType.THEN);
        RESERVED_WORD_MAP.put("ELSE", LexicalType.ELSE);
        RESERVED_WORD_MAP.put("ELSEIF", LexicalType.ELSEIF);
        RESERVED_WORD_MAP.put("FOR", LexicalType.FOR);
        RESERVED_WORD_MAP.put("FORALL", LexicalType.FORALL);
        RESERVED_WORD_MAP.put("NEXT", LexicalType.NEXT);
        RESERVED_WORD_MAP.put("SUB", LexicalType.FUNC);
        RESERVED_WORD_MAP.put("DIM", LexicalType.DIM);
        RESERVED_WORD_MAP.put("AS", LexicalType.AS);
        RESERVED_WORD_MAP.put("END", LexicalType.END);
        RESERVED_WORD_MAP.put("WHILE", LexicalType.WHILE);
        RESERVED_WORD_MAP.put("DO", LexicalType.DO);
        RESERVED_WORD_MAP.put("UNTIL", LexicalType.UNTIL);
        RESERVED_WORD_MAP.put("LOOP", LexicalType.LOOP);
        RESERVED_WORD_MAP.put("TO", LexicalType.TO);
        RESERVED_WORD_MAP.put("WEND", LexicalType.WEND);
        SYMBOL_INITIAL_MAP.put("=", LexicalType.EQ);
        SYMBOL_INITIAL_MAP.put("<", LexicalType.LT);
        SYMBOL_INITIAL_MAP.put(">", LexicalType.GT);
        SYMBOL_INITIAL_MAP.put(".", LexicalType.DOT);
        SYMBOL_INITIAL_MAP.put("+", LexicalType.ADD);
        SYMBOL_INITIAL_MAP.put("-", LexicalType.SUB);
        SYMBOL_INITIAL_MAP.put("*", LexicalType.MUL);
        SYMBOL_INITIAL_MAP.put("/", LexicalType.DIV);
        SYMBOL_INITIAL_MAP.put(")", LexicalType.LP);
        SYMBOL_INITIAL_MAP.put("(", LexicalType.RP);
        SYMBOL_INITIAL_MAP.put(",", LexicalType.COMMA);
        SYMBOL_INITIAL_MAP.put("\n", LexicalType.NL);
        SYMBOL_MAP.put("=", LexicalType.EQ);
        SYMBOL_MAP.put("<", LexicalType.LT);
        SYMBOL_MAP.put(">", LexicalType.GT);
        SYMBOL_MAP.put("<=", LexicalType.LE);
        SYMBOL_MAP.put("=<", LexicalType.LE);
        SYMBOL_MAP.put(">=", LexicalType.GE);
        SYMBOL_MAP.put("=>", LexicalType.GE);
        SYMBOL_MAP.put("<>", LexicalType.NE);
        SYMBOL_MAP.put(".", LexicalType.DOT);
        SYMBOL_MAP.put("+", LexicalType.ADD);
        SYMBOL_MAP.put("-", LexicalType.SUB);
        SYMBOL_MAP.put("*", LexicalType.MUL);
        SYMBOL_MAP.put("/", LexicalType.DIV);
        SYMBOL_MAP.put(")", LexicalType.LP);
        SYMBOL_MAP.put("(", LexicalType.RP);
        SYMBOL_MAP.put(",", LexicalType.COMMA);
        SYMBOL_MAP.put("\n", LexicalType.NL);
    }

    public boolean expect(LexicalType type) {
        return true;
    }

    public void unget(LexicalUnit token) {}

}
