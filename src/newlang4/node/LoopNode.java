package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// <block> ::=
// | <WHILE> <cond> <NL> <stmt_list> <NL> <WEND>
// | <DO> <WHILE> <cond> <NL> <stmt_list> <LOOP> <NL>
// | <DO> <UNTIL> <cond> <NL> <stmt_list> <LOOP> <NL>
// | <DO> <NL> <stmt_list> <LOOP> <WHILE> <cond> <NL>
// | <DO> <NL> <stmt_list> <LOOP> <UNTIL> <cond> <NL>

public class LoopNode extends Node {
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.WHILE, LexicalType.DO));
    private Node condition;
    private Node process;
    private boolean doWhileFlag = false; // do while
    private boolean untilFlag = false;


    private LoopNode(Environment env) {
        super(env);
        type = NodeType.BLOCK;
    }

    public void parse() throws Exception {

        // check <WHILE>
        if (env.getInput().peep(1).getType() == LexicalType.WHILE) {
            // skip <WHILE>
            env.getInput().get();

            // check <cond>
            if (CondNode.isMatch(env.getInput().peep(1).getType())) {
                condition = CondNode.getHandler(env);
                condition.parse();
            } else throw new Exception("syntax error. missing condition. line: " + env.getInput().getLine());

            // check <NL>
            if (env.getInput().peep(1).getType() == LexicalType.NL) {
                // skip <NL>
                env.getInput().get();
            } else throw new Exception("syntax error. missing new line. line: " + env.getInput().getLine());

            // check <stmt_list>
            if (StmtListNode.isMatch(env.getInput().peep(1).getType())) {
                process = StmtListNode.getHandler(env);
                process.parse();
            } else throw new Exception("syntax error. missing process for WHILE. line: " + env.getInput().getLine());

            // check <NL>
            if (env.getInput().peep(1).getType() == LexicalType.NL) {
                // skip <NL>
                env.getInput().get();
            } else throw new Exception("syntax error. missing new line. line: " + env.getInput().getLine());

            // check <WEND>
            if (env.getInput().peep(1).getType() == LexicalType.WEND) {
                // skip <WEND>
                env.getInput().get();
            } else throw new Exception("syntax error. missing WEND. line: " + env.getInput().getLine());

        // check <DO>
        } else if (env.getInput().peep(1).getType() == LexicalType.DO) {
            // skip <DO>
            env.getInput().get();

            doWhileFlag = true;

            // check <cond>
            doBlockCond();

            // check <NL>
            if (env.getInput().peep(1).getType() == LexicalType.NL) {
                // skip <NL>
                env.getInput().get();
            } else throw new Exception("syntax error. missing new line. line: " + env.getInput().getLine());

            // check <stmt_list>
            if (StmtListNode.isMatch(env.getInput().peep(1).getType())) {
                process = StmtListNode.getHandler(env);
                process.parse();
            } else throw new Exception("syntax error. missing process for DO. line: " + env.getInput().getLine());

            // check <LOOP>
            if (env.getInput().peep(1).getType() == LexicalType.LOOP) {
                // skip <LOOP>
                env.getInput().get();
            } else throw new Exception("syntax error. missing LOOP. line: " + env.getInput().getLine());

            // check condition
            if (condition == null) {
                if(!doBlockCond()) throw new Exception("syntax error. missing condition starts WHILE or UNTIL. line: " + env.getInput().getLine());
            }
        } else throw new Exception("syntax error. loop block should starts WHILE or DO. line: " + env.getInput().getLine());

        // check <NL>
        if (env.getInput().peep(1).getType() == LexicalType.NL) {
            // skip <NL>
            env.getInput().get();
        } else throw new Exception("syntax error. missing new line. line: " + env.getInput().getLine());
    }

    // check condition for while or until
    private boolean doBlockCond() throws Exception {
        switch (env.getInput().peep(1).getType()){
            case UNTIL:
                untilFlag = true;
                // skip <UNTIL>
                env.getInput().get();

                // check <cond>
                if (CondNode.isMatch(env.getInput().peep(1).getType())){
                    condition = CondNode.getHandler(env);
                    condition.parse();
                } else throw new Exception("syntax error. missing condition. line: " + env.getInput().getLine());
                break;

            case WHILE:
                // skip <WHILE>
                env.getInput().get();

                // check <cond>
                if (CondNode.isMatch(env.getInput().peep(1).getType())){
                    condition = CondNode.getHandler(env);
                    condition.parse();
                } else throw new Exception("syntax error. missing condition. line: " + env.getInput().getLine());
                break;
            default:
                return false;
        }
        return true;
    }

    public static Node getHandler(Environment environment) throws Exception {
        return new LoopNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {
        String temp = "LOOP: ";
        temp += "condition = (";
        if (untilFlag) temp += "!";
        temp += condition + ")\n";
        temp += process;
        return temp;
    }
}
