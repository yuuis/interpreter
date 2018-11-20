package newlang4.node;

import newlang3.*;
import newlang4.Node;
import newlang4.NodeType;

public class ProgramNode extends Node {

    public boolean parse() throws Exception {
        // nodeごとの処理
        return true;
    }

    public String toString() {
        if (type == NodeType.PROGRAM) return "PROGRAM";
        else return "Node";
    }

    public static boolean isMatch(LexicalType lexicalType) {
        return true;
    }

    private static Node getHandler(LexicalUnit lexicalUnit) {
    }
}
