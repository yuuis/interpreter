package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

public class StmtListNode extends Node {

    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO, LexicalType.NAME, LexicalType.FOR, LexicalType.END));
    List<Node> child = new ArrayList<Node>();

    StmtListNode(Environment env) {
        super(env);
        type = NodeType.STMT_LIST;
    }

    public boolean parse() throws Exception {
        LexicalUnit lexicalUnit;
        lexicalUnit = env.getInput().get();
        if(StmtNode.isMatch(lexicalUnit.getType())) {
            Node handler = StmtNode.getHandler(lexicalUnit.getType(), env);
            handler.parse();
            return true;
        } else {
            return false;
        }
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
        if (!isMatch(lexicalType)) return null;
        else {
            switch (lexicalType) {
                case IF: return new StmtNode(environment);
                case WHILE: return new StmtNode(environment);
                case DO: return new StmtNode(environment);
                case NAME: return new BlockNode(environment);
                case FOR: return new BlockNode(environment);
                case END: return new BlockNode(environment);
                default: return null;
            }
        }
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
