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

    private ProgramNode(Environment env) {
        super(env);
        type = NodeType.PROGRAM;
    }

    public static Node getHandler(Environment environment) {
        return StmtListNode.getHandler(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {
        if (type == NodeType.PROGRAM) return "PROGRAM";
        else return "Node";
    }
}
