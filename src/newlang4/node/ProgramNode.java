package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends Node {
    List<Node> child = new ArrayList<Node>();

    private ProgramNode(Environment env) {
        super(env);
        type = NodeType.PROGRAM;
    }

    public void parse() {
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
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
