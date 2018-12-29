package newlang3;

import newlang4.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    PushbackReader reader;
    int line = 1;
    static HashMap<String, LexicalUnit> RESERVED_WORD_MAP = new HashMap<>();
    static HashMap<String, LexicalUnit> SYMBOL_MAP = new HashMap<>();
    List<LexicalUnit> lexicalUnits = new ArrayList<>();


    public LexicalAnalyzerImpl(FileInputStream fileInputStream) throws Exception {
        Reader inputStreamReader = new InputStreamReader(fileInputStream);
        this.reader = new PushbackReader(inputStreamReader);
    }

    public LexicalUnit get() throws Exception {

        // check ungot list
        if (!lexicalUnits.isEmpty()) {
            int index = lexicalUnits.size() - 1;
            LexicalUnit unit = lexicalUnits.get(index);
            lexicalUnits.remove(index);
            if (unit.getType() == LexicalType.NL) this.line++;
            return unit;
        }

        while(true) {
            int ci = reader.read();
            reader.unread(ci);

            if (ci < 0) {
                return new LexicalUnit(LexicalType.EOF);
            } else {
                char c = (char) ci;

                if ((c == ' ') || (c == '\t')) {
                    reader.read();
                    continue;
                }

                // when alphabet
                if ((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z')) return getString();

                // when number
                if (c >= '0' && c <= '9') return getNumber();

                // when literal
                if (c == '"') return getLiteral();

                // when symbol
                if (SYMBOL_MAP.containsKey(c + "")) return getSymbol();

                System.out.println(c);
                throw new InternalError("shinda");
//                throw new Exception("character cant be interpreted");
            }
        }
    }

    private LexicalUnit getString() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();

            if (ci < 0) break;
            char c = (char) ci;

            if (((c >= 'a' && c<= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0'  && c <= '9'))) {
                target += c;
                continue;
            }
            reader.unread(ci);
            break;
        }
        if (RESERVED_WORD_MAP.containsKey(target)) return RESERVED_WORD_MAP.get(target);
        else return new LexicalUnit(LexicalType.NAME, new ValueImpl(target, ValueType.STRING));
    }

    private LexicalUnit getNumber() throws Exception {
        String target = "";
        Boolean decimalFlag = false;

        while(true) {
            int ci = reader.read();

            if (ci < 0) break;
            char c = (char) ci;

            if (c >= '0' && c <= '9') {
                target += c;
                continue;
            } else if (c == '.' && !decimalFlag) {
                decimalFlag = true;
                target += c;
                continue;
            } else if (c == '.' && decimalFlag) {
                throw new Exception("too many dots");
            }
            reader.unread(ci);
            break;
        }
        if (decimalFlag) return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(target, ValueType.DOUBLE));
        else return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(target, ValueType.INTEGER));
    }

    private LexicalUnit getSymbol() throws Exception {
        String target = "";

        while(true) {
            int ci = reader.read();

            if (ci < 0) return SYMBOL_MAP.get(target);
            char c = (char) ci;

            if (SYMBOL_MAP.containsKey(target + c)) {
                target += c;
            } else {
                reader.unread(ci);
                return SYMBOL_MAP.get(target);
            }
        }
    }

    private LexicalUnit getLiteral() throws Exception {
        String target = "";
        reader.read();

        while(true) {
            int ci = reader.read();

            if (ci < 0) break;
            char c = (char) ci;

            if (c != '"') {
                target += c;
                continue;
            }
            return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(target, ValueType.STRING));
        }
        throw new Exception("cant find closing double quote");
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
        SYMBOL_MAP.put(")", new LexicalUnit(LexicalType.RP));
        SYMBOL_MAP.put("(", new LexicalUnit(LexicalType.LP));
        SYMBOL_MAP.put(",", new LexicalUnit(LexicalType.COMMA));
        SYMBOL_MAP.put("\n", new LexicalUnit(LexicalType.NL));
    }

    public boolean expect(LexicalType type) throws Exception { return peep(1).getType() == type; }

    public void unget(LexicalUnit token) {
        if (token.getType() == LexicalType.NL) line--;
        lexicalUnits.add(token);
    }

    public int getLine() { return line; }

    public LexicalUnit peep(int c) throws Exception {
        List<LexicalUnit> temp = new ArrayList<>();

        for(int i = 0; i < c - 1; i++) {
            temp.add(get());
        }

        LexicalUnit lu = get();
        temp.add(lu);

        while(!temp.isEmpty()) {
            unget(temp.get(temp.size() - 1));
            temp.remove(temp.size() - 1);
        }
        return lu;
    }
}
