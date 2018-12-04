package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

public class StmtNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME, LexicalType.FOR, LexicalType.END));
    List<Node> child = new ArrayList<Node>();

    private StmtNode(Environment env) {
        super(env);
        type = NodeType.STMT;
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
        if (!isMatch(lexicalType)) return null;
        else {
            switch (lexicalType) {
                case NAME: return StmtNode.getHandler(lexicalType, environment);
                case FOR: return StmtNode.getHandler(lexicalType, environment);
                case END: return StmtNode.getHandler(lexicalType, environment);
                default: return null;
            }
        }
    }

    public static boolean isMatch(LexicalType type) { return first.contains(type); }
}
