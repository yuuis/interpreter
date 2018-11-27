package newlang4.node;
import newlang3.*;
import newlang4.Environment;
import newlang4.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO));

    public BlockNode(Environment env) {
        super(env);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public static Node getHandler(LexicalType lexicalType) {
        switch (lexicalType) {
            case IF: return new IfBlockNode();
            case WHILE: return new WhileNode();
            case DO: return new DoNode;
            default: return null;
        }
    }

    public boolean Parse() throws Exception {
        LexicalUnit lexicalUnit;
        return true;
    }
}
