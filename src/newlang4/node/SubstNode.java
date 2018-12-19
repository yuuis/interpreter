package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SubstNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME));

    private SubstNode(Environment env) {
        super(env);
        type = NodeType.SUBST_STMT;
    }

    public boolean parse() throws Exception {
        return true;
    }

    public static SubstNode getHandler(Environment environment) {
        return new SubstNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
