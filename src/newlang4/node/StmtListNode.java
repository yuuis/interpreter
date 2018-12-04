package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

public class StmtListNode extends Node {

    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO, LexicalType.NAME, LexicalType.FOR, LexicalType.END));
    List<Node> child = new ArrayList<Node>();

    private StmtListNode(Environment env) {
        super(env);
        type = NodeType.STMT_LIST;
    }

    public boolean parse() throws Exception {

        while(true) {
            LexicalUnit lexicalUnit = env.getInput().get();
            env.getInput().unget(lexicalUnit);

            // when statement
            if(StmtNode.isMatch(lexicalUnit.getType())) {
                Node stmtHandler = StmtNode.getHandler(lexicalUnit.getType(), env);
                child.add(stmtHandler);
                stmtHandler.parse();
            }

            // when block
            if(BlockNode.isMatch(lexicalUnit.getType())) {
                Node blockHandler = StmtNode.getHandler(lexicalUnit.getType(), env);
                child.add(blockHandler);
                blockHandler.parse();
            }

            return false;
        }
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
        if (!isMatch(lexicalType)) return null;
        else return new StmtListNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
