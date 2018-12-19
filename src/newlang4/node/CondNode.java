package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


// <cond> ::=
// <expr> <EQ> <expr>
// | <expr> <GT> <expr>
// | <expr> <LT> <expr>
// | <expr> <GE> <expr>
// | <expr> <LE> <expr>
// | <expr> <NE> <expr>

public class CondNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList());

    private CondNode(Environment env) {
        super(env);
        type = NodeType.COND;
    }

    public void parse() throws Exception {

    }

    public static Node getHandler(Environment environment) {
        return new CondNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}

