package newlang4.node;

import newlang3.*;
import newlang4.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// <block> ::=
// <if_prefix> <stmt> <NL>
// | <if_prefix> <stmt> <ELSE> <stmt> <NL>
// | <if_prefix> <NL> <stmt_list> <else_block> <ENDIF> <NL>

// <if_prefix>  ::=
// <IF> <cond> <THEN>

// <else_block> ::=
// <else_if_block>
// | <else_if_block> <ELSE> <NL> <stmt_list>

// <else_if_block> ::=
// | <else_if_block> <ELSEIF> <cond> <THEN> <NL> <stmt_list>

public class IfBlockNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF));
    private Node condition;
    private Node process;
    private Node processOnElse;

    private IfBlockNode(Environment env) {
        super(env);
        type = NodeType.IF_BLOCK;
    }

    public void parse() throws Exception {

        // IF condition THEN process(STMT) ELSE process(STMT) ENDIF

        // IF condition THEN
        //   process(STMTLIST)
        // ELSEIF condition THEN
        //   process(STMTLIST)
        // ENDIF

        boolean elseifFlag = false;
        LexicalType inputType = env.getInput().peep(1).getType();

        // check IF or ELSEIF
        if (inputType == LexicalType.ELSEIF){
            elseifFlag = true;
        }
        env.getInput().get();

        // check cond
        if (CondNode.isMatch(inputType)){
            condition = CondNode.getHandler(env);
            condition.parse();
        } else throw new Exception("syntax error. missing condition. line: " + env.getInput().getLine());

        // check THEN
        if (env.getInput().get().getType() != LexicalType.THEN) throw new Exception("syntax error. missing THEN. line: " + env.getInput().getLine());


        // check process
        if (StmtNode.isMatch(env.getInput().peep(1).getType())) {
            process = StmtNode.getHandler(env);
            process.parse();

            // check ELSE
            if (env.getInput().peep(1).getType() == LexicalType.ELSE) {
                env.getInput().get();

                // check process when ELSE
                if (StmtNode.isMatch(env.getInput().peep(1).getType())){
                    processOnElse = StmtNode.getHandler(env);
                    processOnElse.parse();
                } else throw new Exception("syntax error. missing process. line: " + env.getInput().getLine());
            }

        // check NL
        } else if (env.getInput().peep(1).getType() == LexicalType.NL) {
            // skip NL
            env.getInput().get();

            // check process
            if (StmtListNode.isMatch(env.getInput().peep(1).getType())) {
                process = StmtListNode.getHandler(env);
                process.parse();
            }

            // check NL
            if (env.getInput().get().getType() != LexicalType.NL) throw new Exception("syntax error. missing NL. line: " + env.getInput().getLine());

            // check ELSEIF
            if (env.getInput().peep(1).getType() == LexicalType.ELSEIF) {
                processOnElse = IfBlockNode.getHandler(env);
                processOnElse.parse();

            // check ELSE
            } else if (env.getInput().peep(1).getType() == LexicalType.ELSE) {
                // skip ELSE
                env.getInput().get();

                // check NL
                if (env.getInput().get().getType() == LexicalType.NL) {

                    // check process
                    if (StmtListNode.isMatch(env.getInput().peep(1).getType())){
                        processOnElse = StmtNode.getHandler(env);
                        processOnElse.parse();
                    } else throw new Exception("syntax error. line: " + env.getInput().getLine());

                    // check NL
                    if (env.getInput().get().getType() != LexicalType.NL) throw new Exception("syntax error. missing NL. line: " + env.getInput().getLine());
                } else throw new Exception("syntax error. missing NL. line: " + env.getInput().getLine());
            } else throw new Exception("syntax error. it need ELSEIF or ELSE. line: " + env.getInput().getLine());

            // when ELSEIF
            if (!elseifFlag) {
                if (env.getInput().get().getType() != LexicalType.NL) throw new Exception("syntax error. missing NL. line: " + env.getInput().getLine());
            }

            // check ENDIF
            if (env.getInput().peep(1).getType() != LexicalType.ENDIF) throw new Exception("syntax error. missing ENDIF. line: " + env.getInput().getLine());
        }
    }

    public static Node getHandler(Environment environment) {
        return new IfBlockNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
