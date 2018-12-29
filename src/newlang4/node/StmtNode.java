package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

import static newlang3.LexicalType.*;

// <stmt>  ::=
// <subst>
// | <call_sub>
// | <FOR> <subst> <TO> <INTVAL> <NL> <stmt_list> <NEXT> <NAME>
// | <END>

public class StmtNode extends Node {
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME, LexicalType.FOR, LexicalType.END));
    List<Node> child = new ArrayList<Node>();

    private StmtNode(Environment env) {
        super(env);
        type = NodeType.STMT;
    }

    public static Node getHandler(Environment environment) throws Exception {
        switch (environment.getInput().peep(1).getType()) {

            case NAME:
                LexicalType inputType = environment.getInput().peep(2).getType();

                // when <NAME> <EQ> -> subst
                if (inputType == EQ ) return SubstNode.getHandler(environment);

                // when <NAME> <expr_list> -> call_sub
                if (ExprListNode.isMatch(inputType)) return CallSubNode.getHandler(environment);
                else throw new Exception("syntax error. wrong constitution for statement. line: " + environment.getInput().getLine());

            case FOR:
                return ForNode.getHandler(environment);

            case END:
                return EndNode.getHandler(environment);

            default:
                throw new Exception("syntax error. line: " + environment.getInput().getLine());
        }
    }

    public static boolean isMatch(LexicalType type) { return first.contains(type); }
    public String toString() { return "stmt"; }
}
