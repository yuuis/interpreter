package newlang4.node;
import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO));

    private BlockNode(Environment env) {
        super(env);
        type = NodeType.BLOCK;
    }

//    public boolean Parse() throws Exception {
//        return true;
//    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
        switch (lexicalType) {
            case IF: return StmtNode.getHandler(lexicalType, environment);
            case WHILE: return StmtNode.getHandler(lexicalType, environment);
            case DO: return StmtNode.getHandler(lexicalType, environment);
            default: return null;
        }
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
