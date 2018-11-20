package newlang4.node;

import newlang3.*;
import newlang4.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StmtListNode extends Node {

    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList());

    public boolean parse() throws Exception {
        LexicalUnit lexicalUnit;
        lexicalUnit = env.getInput().get();
        if(StmtNode.isMatch(lexicalUnit.getType())) {
            Node handler = StmtNode.getHandler(lexicalUnit.getType());
            handler.parse();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
