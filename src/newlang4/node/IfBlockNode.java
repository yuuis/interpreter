package newlang4.node;

import newlang3.*;
import newlang4.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IfBlockNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF));

    private IfBlockNode(Environment env) {
        super(env);
        type = NodeType.IF_BLOCK;
    }

    public boolean parse() throws Exception {
        env.getInput().get();
        LexicalUnit lexicalUnit = env.getInput().get();
        env.getInput().unget(lexicalUnit);

        // if cond
        if(!CondNode.isMatch(lexicalUnit.getType())) return false;
        Node condHandler = CondNode.getHandler(lexicalUnit.getType(), env);
        if(!condHandler.parse()) return false;

        // if then (skip)
        LexicalUnit next = env.getInput().get();
        if(next.getType() != LexicalType.THEN) return false;

        // if ln (stmt)
        next = env.getInput().get();
        env.getInput().unget(next);
        if(next.getType() == LexicalType.NL) {
            next = env.getInput().get();
            env.getInput().unget(next);

            // when stmt list
            if(StmtListNode.isMatch(next.getType())){
                Node stmtListHandler = StmtListNode.getHandler(env);
                if (!stmtListHandler.parse()) return false;
            } else return false;
        } else if(StmtListNode.isMatch(next.getType())) {
            Node stmtHandler = StmtNode.getHandler(env);
            if (!stmtHandler.parse()) return false;
        } else {
            return false;
        }
        return true;
    }

    public static Node getHandler(Environment environment) {
        return new IfBlockNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
