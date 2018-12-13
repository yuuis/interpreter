package newlang4.node;

import newlang3.LexicalType;
import newlang3.LexicalUnit;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CondNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList());

    private CondNode(Environment env) {
        super(env);
        type = NodeType.COND;
    }

    public boolean parse() throws Exception {

        return true;
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {

    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}

