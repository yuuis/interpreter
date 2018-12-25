package newlang4.node;
import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockNode extends Node {
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.IF, LexicalType.WHILE, LexicalType.DO));

    private BlockNode(Environment env) {
        super(env);
        type = NodeType.BLOCK;
    }

    public static Node getHandler(Environment environment) throws Exception {
        LexicalType inputType = environment.getInput().peep(1).getType();

        if(IfBlockNode.isMatch(inputType)) return IfBlockNode.getHandler(environment);
        if(LoopNode.isMatch(inputType)) return LoopNode.getHandler(environment);
        throw new InternalError("cant make block node. line: " + environment.getInput().getLine());
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() { return "block"; }
}
