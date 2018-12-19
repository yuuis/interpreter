package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LoopNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.WHILE, LexicalType.DO));

    private LoopNode(Environment env) {
        super(env);
        type = NodeType.BLOCK;
    }

    public boolean parse() throws Exception {
        return true;
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) throws Exception {
        if(!isMatch(lexicalType)) return null;
        return new LoopNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {return "Block";}
}
