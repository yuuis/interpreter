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

    public boolean parse() throws Exception {
        return true;
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
        return StmtListNode.getHandler(lexicalType, environment);
    }

    public static boolean isMatch(LexicalType lexicalType) {
        return true;
    }

    public String toString() {
        if (type == NodeType.PROGRAM) return "PROGRAM";
        else return "Node";
    }
}
