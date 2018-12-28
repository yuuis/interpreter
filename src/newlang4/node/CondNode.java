package newlang4.node;

import newlang3.LexicalType;
import newlang3.Value;
import newlang3.ValueImpl;
import newlang3.ValueType;
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
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME, LexicalType.SUB, LexicalType.LP, LexicalType.INTVAL, LexicalType.DOUBLEVAL, LexicalType.LITERAL));
    private final static Set<LexicalType> operators = new HashSet<>(Arrays.asList(LexicalType.EQ, LexicalType.LT, LexicalType.LE, LexicalType.GT, LexicalType.GE, LexicalType.NE));
    private Node left;
    private Node right;
    private LexicalType operator;

    private CondNode(Environment env) {
        super(env);
        type = NodeType.COND;
    }

    public void parse() throws Exception {
        // check <expr>
        if (ExprNode.isMatch(env.getInput().peep(1).getType())) {
            left = ExprNode.getHandler(env);
            left.parse();
        } else throw new Exception("syntax error. missing left expr. line: " + env.getInput().getLine());

        // check operator
        if (operators.contains(env.getInput().peep(1).getType())) {
            operator = env.getInput().get().getType();
        } else throw new Exception("syntax error. missing operator. line: " + env.getInput().getLine());

        if (ExprNode.isMatch(env.getInput().peep(1).getType())) {
            right = ExprNode.getHandler(env);
            right.parse();
        } else throw new Exception("syntax error. missing right expr. line: " + env.getInput().getLine());
    }

    public static Node getHandler(Environment environment) {
        return new CondNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {
        return "cond: " + left + " " + operator + " " + right;
    }

    public Value getValue() throws Exception {
        Value leftVal = left.getValue();
        Value rightVal = right.getValue();

        // null check
        if (leftVal == null || rightVal == null) throw new Exception("calculation of null");

        // when string
        if (leftVal.getType() == ValueType.STRING || rightVal.getType() == ValueType.STRING) {
            switch (operator) {
                case EQ: return new ValueImpl(leftVal.getSValue().equals(rightVal.getSValue()));
                case NE: return new ValueImpl(!leftVal.getSValue().equals(rightVal.getSValue()));
                default: throw new Exception("designated invalid operator for string condition. (not = or !=)");
            }
        }

        switch (operator) {
            case LT: return new ValueImpl(leftVal.getDoubleValue() < rightVal.getDoubleValue());
            case LE: return new ValueImpl(leftVal.getDoubleValue() <= rightVal.getDoubleValue());
            case GT: return new ValueImpl(leftVal.getDoubleValue() > rightVal.getDoubleValue());
            case GE: return new ValueImpl(leftVal.getDoubleValue() >= rightVal.getDoubleValue());
            case EQ: return new ValueImpl(leftVal.getDoubleValue() == rightVal.getDoubleValue());
            case NE: return new ValueImpl(leftVal.getDoubleValue() != rightVal.getDoubleValue());
            default: throw new Exception("designated invalid operator for condition.");
        }
    }
}
