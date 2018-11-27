package newlang4.node;

import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import java.util.*;

public class StmtNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList());
    List<Node> child = new ArrayList<Node>();

    public StmtNode(Environment env) {
        super(env);
    }

    public static Node getHandler(LexicalType lexicalType, Environment environment) {
        switch (lexicalType) {

        }
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
