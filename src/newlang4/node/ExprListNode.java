package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

// <expr_list>  ::=
// <expr>
// | <expr_list> <COMMA> <expr>

public class ExprListNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME, LexicalType.SUB, LexicalType.LP, LexicalType.INTVAL, LexicalType.DOUBLEVAL, LexicalType.LITERAL));
    List<Node> child = new ArrayList<Node>();

    private ExprListNode(Environment env) {
        super(env);
        type = NodeType.EXPR_LIST;
    }

    public void parse() throws Exception {
        // check <expr>
        if (ExprNode.isMatch(env.getInput().peep(1).getType())){
            Node exprHandler = ExprNode.getHandler(env);
            exprHandler.parse();
            child.add(exprHandler);
        } else throw new Exception("syntax error. wrong argument for call function. line: " + env.getInput().getLine());


        // loop comma and expr
        while(true) {
            LexicalType inputType = env.getInput().get().getType();

            // when comma
            if(inputType == LexicalType.COMMA) {
                env.getInput().get();
            } else break;

            // when expr
            if(ExprNode.isMatch(inputType)) {
                Node exprHandler = ExprNode.getHandler(env);
                child.add(exprHandler);
                exprHandler.parse();
            } else throw new Exception("syntax error. wrong argument list for call function. line: " + env.getInput().getLine());
        }
    }

    public static Node getHandler(Environment environment) {
        return new ExprListNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
