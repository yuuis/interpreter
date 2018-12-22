package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValiableNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME));

    private ValiableNode(Environment env) {
        super(env);
        type = NodeType.VARIABLE;
    }

    public void parse() {}

    public static Node getHandler(Environment environment) {
        return new ValiableNode(environment);
    }
}
