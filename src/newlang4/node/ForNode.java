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

// <FOR> <subst> <TO> <INTVAL> <NL> <stmt_list> <NEXT> <NAME>

public class ForNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.FOR));
    private Node subst; // initialize
    private Node to; // max value
    private Node operation;
    private String step; // variable name

    private ForNode(Environment env) {
        super(env);
        type = NodeType.FOR_STMT;
    }

    public void parse() throws Exception {

        // skip <FOR>
        env.getInput().get();

        LexicalType inputType = env.getInput().peep(1).getType();

        // check <subst>
        if (SubstNode.isMatch(inputType)) {
            this.subst = SubstNode.getHandler(env);
            subst.parse();
        } else throw new Exception("syntax error. initialize may be wrong. line: " + env.getInput().getLine());

        // check <TO>
        if (env.getInput().get().getType() != LexicalType.TO) throw new Exception("syntax error. missing TO. line: " + env.getInput().getLine());

        // check <INTVAL>
        if (env.getInput().peep(1).getType() == LexicalType.INTVAL) to = ConstNode.getHandler(env.getInput().get().getValue(), env);
        else throw new Exception("syntax error. missing max value. line: " + env.getInput().getLine());

        // check <NL>
        if (env.getInput().get().getType() != LexicalType.NL) throw new Exception("syntax error. missing new line. line: " + env.getInput().getLine());

        // check <stmt_list>
        if (StmtListNode.isMatch(inputType)){
            operation = StmtListNode.getHandler(env);
            operation.parse();
        } else throw new Exception("syntax error. missing stmt_list. line: " + env.getInput().getLine());

        // check <NL>
//        if (env.getInput().get().getType() != LexicalType.NL) throw new Exception("syntax error. missing new line. line: " + env.getInput().getLine());

        // check <NEXT>
        if (env.getInput().get().getType() != LexicalType.NEXT) throw new Exception("syntax error. missing NEXT. line: " + env.getInput().getLine());

        // check <NAME>
        if (inputType == LexicalType.NAME) step = env.getInput().get().getValue().getSValue();
        else throw new Exception("syntax error. missing NAME for update. line: " + env.getInput().getLine());
    }

    public static Node getHandler(Environment environment) {
        return new ForNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public Value getValue() throws Exception {
        subst.getValue();
        while (true) {
            if (env.getVariable(step).getValue().getIntValue() > to.getValue().getIntValue()) return null;
            operation.getValue();
            env.getVariable(step).setValue(new ExprNode(env.getVariable(step), ConstNode.getHandler(new ValueImpl("1", ValueType.INTEGER), env), LexicalType.ADD).getValue());
        }
    }
}
