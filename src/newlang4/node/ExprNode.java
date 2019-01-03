package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

// <expr> ::=
// <expr> <ADD> <expr>
// | <expr> <SUB> <expr>
// | <expr> <MUL> <expr>
// | <expr> <DIV> <expr>
// | <SUB> <expr>
// | <LP> <expr> <RP>
// | <NAME>
// | <INTVAL>
// | <DOUBLEVAL>
// | <LITERAL>
// | <call_func>

public class ExprNode extends Node {
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME, LexicalType.SUB, LexicalType.LP, LexicalType.INTVAL, LexicalType.DOUBLEVAL, LexicalType.LITERAL));

    // operator and priority map
    private static final Map<LexicalType, Integer> operators_map = new HashMap<>();
    static {
        operators_map.put(LexicalType.DIV,1);
        operators_map.put(LexicalType.MUL,2);
        operators_map.put(LexicalType.SUB,3);
        operators_map.put(LexicalType.ADD,4);
    }
    private Node left;
    private Node right;
    private LexicalType operator;

    private ExprNode(Environment env) {
        super(env);
        type = NodeType.EXPR;
    }

    public ExprNode(Node left, Node right, LexicalType operator){
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public void parse() throws Exception {
        List<Node> exprs = new ArrayList<>();
        List<LexicalType> operators = new ArrayList<>();

        while (true) {
            // operand operator operand operator operand ...

            // check operand
            switch (env.getInput().peep(1).getType()) {
                case SUB:
                    LexicalType inputType = env.getInput().peep(2).getType();

                    // check value
                    if ((inputType == LexicalType.INTVAL) || (inputType == LexicalType.DOUBLEVAL) || (inputType == LexicalType.LP)) {

                        // skip <SUB>
                        env.getInput().get();

                        // add minus value
                        exprs.add(ConstNode.getHandler(new ValueImpl("-1", ValueType.INTEGER), env));
                        addOperator(exprs, operators, LexicalType.MUL);
                    } else
                        throw new Exception("syntax error. SUB is invalid location. line: " + env.getInput().getLine());
                    break;

                case LP:
                    // skip <LP>
                    env.getInput().get();

                    // add exprs
                    Node exprHandler = ExprNode.getHandler(env);
                    exprHandler.parse();
                    exprs.add(exprHandler);

                    // check close <RP>
                    if (env.getInput().get().getType() != LexicalType.RP)
                        throw new Exception("syntax error. missing close bracket. line: " + env.getInput().getLine());
                    break;

                case NAME:
                    // when <NAME><LP> -> function
                    if (env.getInput().peep(2).getType() == LexicalType.LP) {
                        Node callSub = CallSubNode.getHandler(env);
                        callSub.parse();
                        exprs.add(callSub);

                    // when <NAME> -> variable
                    } else exprs.add(env.getVariable(env.getInput().get().getValue().getSValue()));
                    break;

                case INTVAL:
                case DOUBLEVAL:
                case LITERAL:
                    exprs.add(ConstNode.getHandler(env.getInput().get().getValue(), env));
                    break;
                default:
                    throw new Exception("syntax error. exprsession starts wrong node. line: " + env.getInput().getLine());
            }

            // check operator and add operator
            if (operators_map.containsKey(env.getInput().peep(1).getType())) {
                addOperator(exprs, operators, env.getInput().get().getType());
            } else break;

        }

        for (int i = operators.size() - 1; i >= 0; i--) {
            if (operators.size() == 1) {
                left = exprs.get(0);
                right = exprs.get(1);
                operator = operators.get(0);
                return;
            }
            exprs.add(new ExprNode(exprs.get(exprs.size() - 2), exprs.get(exprs.size() - 1), operators.get(i)));
            exprs.remove(exprs.size() - 3);
            exprs.remove(exprs.size() - 2);
        }
        left = exprs.get(0);
    }

    // conversion to reverse polish notation
    // create expr for each operation
    private void addOperator(List<Node> rightList, List<LexicalType> operatorList, LexicalType operator) {
        for (int i = operatorList.size() - 1; i >= 0; i--){
            boolean flag = false;
            if (operators_map.get(operatorList.get(i))<operators_map.get(operator)){
                flag = true;
                rightList.add(new ExprNode(rightList.get(rightList.size() - 2),rightList.get(rightList.size() - 1),operatorList.get(i)));
                rightList.remove(rightList.size() - 3);
                rightList.remove(rightList.size() - 2);
                operatorList.remove(i);
            } else if (flag && operators_map.get(operatorList.get(i)) >= operators_map.get(operator)) break;
        }
        operatorList.add(operator);
    }

    public static Node getHandler(Environment environment) {
        return new ExprNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {
        String temp = left.toString();
        if (operator != null) {
            temp += " " + operator.toString() + " ";
            temp += right.toString();
        }
        return temp;
    }

    public Value getValue() throws Exception {
        // return val (INTVAL or DOUBLEVAL etc...)
        if (operator == null) return left.getValue();

        Value leftVal = left.getValue();
        Value rightVal = right.getValue();

        // null check
        if (leftVal == null || rightVal == null) throw new Exception("calculation of null");

        // when string
        if (leftVal.getType() == ValueType.STRING || rightVal.getType() == ValueType.STRING) {
            if (operator == LexicalType.ADD) return new ValueImpl(leftVal.getSValue() + rightVal.getSValue(), ValueType.STRING);
            else throw new Exception("designated invalid operator for string condition. (can use: `+`)");
        }

        double result;
        switch (operator) {
            case ADD:
                result = leftVal.getDoubleValue() + rightVal.getDoubleValue();
                break;
            case SUB:
                result = leftVal.getDoubleValue() - rightVal.getDoubleValue();
                break;
            case MUL:
                result = leftVal.getDoubleValue() * rightVal.getDoubleValue();
                break;
            case DIV:
                if (rightVal.getDoubleValue() == 0) throw new Exception("divide by zero.");
                result = leftVal.getDoubleValue() / rightVal.getDoubleValue();
                break;
            default: throw new Exception("designated invalid operator");
        }

        if (leftVal.getType() == ValueType.DOUBLE || rightVal.getType() == ValueType.DOUBLE) return new ValueImpl(result + "", ValueType.DOUBLE);
        else return new ValueImpl(result + "", ValueType.INTEGER);
    }
}
