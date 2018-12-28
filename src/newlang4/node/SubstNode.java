package newlang4.node;

import newlang3.LexicalType;
import newlang3.Value;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// <subst> ::=
// <leftvar> <EQ> <expr>

public class SubstNode extends Node {
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME));
    String left;
    Node expr;

    private SubstNode(Environment env) {
        super(env);
        type = NodeType.SUBST_STMT;
    }

    public void parse() throws Exception {

        // get <leftvalue>
        if (env.getInput().peep(1).getType() == LexicalType.NAME) {
            left = env.getInput().get().getValue().getSValue();
        } else throw new Exception("syntax error. assign to not name. line: " + env.getInput().getLine());

        // check <EQ> and skip
        if (env.getInput().peep(1).getType() == LexicalType.EQ) {
            env.getInput().get();
        } else throw new Exception("syntax error. missing EQ. line: " + env.getInput().getLine());

        // check <expr>
        if (ExprNode.isMatch(env.getInput().peep(1).getType())) {
            expr = ExprNode.getHandler(env);
            expr.parse();
        } else throw new Exception("syntax error. substitution is not Expr. line: " + env.getInput().getLine());
    }

    public static SubstNode getHandler(Environment environment) {
        return new SubstNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() { return "subst: " + left + " <= " + expr.toString() + "\n"; }

    public Value getValue() throws Exception {
        // get variable and set value to it
        env.getVariable(left).setValue(expr.getValue());
        return null;
    }
}
