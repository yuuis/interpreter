package newlang3;

import java.io.*;
import java.util.HashMap;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    PushbackReader reader;
    static HashMap<String, LexicalType> stringMap = new HashMap<>();
    static HashMap<String, LexicalType> symbolMap = new HashMap<>();
    static HashMap<String, LexicalType> firstSymbolMap = new HashMap<>();

    public LexicalAnalyzerImpl(PushbackReader reader) throws Exception {
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
                if(firstSymbolMap.containsKey(c + "")) {
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
        if(stringMap.containsKey(target)) {
            return new LexicalUnit(stringMap.get(target));
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
                    return new LexicalUnit(symbolMap.get(target));
                } else {
                    reader.unread(ci);
                    return new LexicalUnit(symbolMap.get(target));
                }
            case '>':
                if (nextC == '=') {
                    target += nextC;
                    return new LexicalUnit(symbolMap.get(target));
                } else {
                    reader.unread(ci);
                    return new LexicalUnit(symbolMap.get(target));
                }
            case '=':
                if (nextC == '<' || nextC == '>') {
                    target += nextC;
                    return new LexicalUnit(symbolMap.get(target));
                } else {
                    reader.unread(ci);
                    return new LexicalUnit(symbolMap.get(target));
                }
            default:
                reader.unread(ci);
                return new LexicalUnit(symbolMap.get(target));
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
        stringMap.put("IF", LexicalType.IF);
        stringMap.put("THEN", LexicalType.THEN);
        stringMap.put("ELSE", LexicalType.ELSE);
        stringMap.put("ELSEIF", LexicalType.ELSEIF);
        stringMap.put("FOR", LexicalType.FOR);
        stringMap.put("FORALL", LexicalType.FORALL);
        stringMap.put("NEXT", LexicalType.NEXT);
        stringMap.put("SUB", LexicalType.FUNC);
        stringMap.put("DIM", LexicalType.DIM);
        stringMap.put("AS", LexicalType.AS);
        stringMap.put("END", LexicalType.END);
        stringMap.put("WHILE", LexicalType.WHILE);
        stringMap.put("DO", LexicalType.DO);
        stringMap.put("UNTIL", LexicalType.UNTIL);
        stringMap.put("LOOP", LexicalType.LOOP);
        stringMap.put("TO", LexicalType.TO);
        stringMap.put("WEND", LexicalType.WEND);
        firstSymbolMap.put("=", null);
        firstSymbolMap.put("<", null);
        firstSymbolMap.put(">", null);
        firstSymbolMap.put(".", LexicalType.DOT);
        firstSymbolMap.put("+", LexicalType.ADD);
        firstSymbolMap.put("-", LexicalType.SUB);
        firstSymbolMap.put("*", LexicalType.MUL);
        firstSymbolMap.put("/", LexicalType.DIV);
        firstSymbolMap.put(")", LexicalType.LP);
        firstSymbolMap.put("(", LexicalType.RP);
        firstSymbolMap.put(",", LexicalType.COMMA);
        firstSymbolMap.put("\n", LexicalType.NL);
        symbolMap.put("=", LexicalType.EQ);
        symbolMap.put("<", LexicalType.LT);
        symbolMap.put(">", LexicalType.GT);
        symbolMap.put("<=", LexicalType.LE);
        symbolMap.put("=<", LexicalType.LE);
        symbolMap.put(">=", LexicalType.GE);
        symbolMap.put("=>", LexicalType.GE);
        symbolMap.put("<>", LexicalType.NE);
        symbolMap.put(".", LexicalType.DOT);
        symbolMap.put("+", LexicalType.ADD);
        symbolMap.put("-", LexicalType.SUB);
        symbolMap.put("*", LexicalType.MUL);
        symbolMap.put("/", LexicalType.DIV);
        symbolMap.put(")", LexicalType.LP);
        symbolMap.put("(", LexicalType.RP);
        symbolMap.put(",", LexicalType.COMMA);
        symbolMap.put("\n", LexicalType.NL);
    }

    public boolean expect(LexicalType type) {
        return true;
    }

    public void unget(LexicalUnit token) {}

}
