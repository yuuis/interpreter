package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

// <program> ::=
// <stmt_list>

public class ProgramNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO, LexicalType.NAME, LexicalType.FOR, LexicalType.END));
    List<Node> child = new ArrayList<Node>();

    private ProgramNode(Environment env) {
        super(env);
        type = NodeType.PROGRAM;
    }

    public void parse() {}

    public static Node getHandler(Environment environment) {
        return StmtListNode.getHandler(environment);
    }

    public static boolean isMatch(LexicalType lexicalType) {
        return true;
    }

    public String toString() {
        if (type == NodeType.PROGRAM) return "PROGRAM";
        else return "Node";
    }
}
