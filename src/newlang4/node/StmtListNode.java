package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.*;

// <stmt_list> ::=
// <stmt>
// | <stmt_list> <stmt> <NL>
// | <block>
// | <block> <stmt_list>

public class StmtListNode extends Node {

    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO, LexicalType.NAME, LexicalType.FOR, LexicalType.END));
    List<Node> child = new ArrayList<Node>();

    private StmtListNode(Environment env) {
        super(env);
        type = NodeType.STMT_LIST;
    }

    public void parse() {

        while (true) {
            try {
                while(env.getInput().peep(1).getType() == LexicalType.NL) {
                    // skip <NL>
                    env.getInput().get();
                }

                LexicalUnit lexicalUnit = env.getInput().peep(1);

                // when statement
                if (StmtNode.isMatch(lexicalUnit.getType())) {
                    Node stmtHandler = StmtNode.getHandler(env);
                    child.add(stmtHandler);
                    stmtHandler.parse();
                }

                // when block
                if (BlockNode.isMatch(lexicalUnit.getType())) {
                    Node blockHandler = BlockNode.getHandler(env);
                    child.add(blockHandler);
                    blockHandler.parse();
                }

                if (!StmtNode.isMatch(lexicalUnit.getType()) && !BlockNode.isMatch(lexicalUnit.getType())) break;
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
                try {
                    skipToLn();
                } catch (Exception ex) {
                    System.out.println(ex.fillInStackTrace());
                }
            }
        }
    }

    public static Node getHandler(Environment environment) {
        return new StmtListNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    private void skipToLn() throws Exception {
        LexicalType inputType = env.getInput().get().getType();
        while(inputType != LexicalType.NL) {
            env.getInput().get();
        }
    }

    public String toString() {
        String temp = "";

        for (Node ch : child) {
            temp += ch.toString();
        }

        return temp;
    }

    public Value getValue() throws Exception {
        for (Node ch: child) {
            ch.getValue();
        }
        return null;
    }
}
