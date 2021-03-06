package newlang4.node;

import newlang3.LexicalType;
import newlang3.Value;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EndNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.END));

    private EndNode(Environment env) {
        super(env);
        type = NodeType.END;
    }

    public void parse() throws Exception {
        if(env.getInput().peep(1).getType() == LexicalType.END) {
            // skip <END>
            env.getInput().get();
        } else throw new Exception("syntax error. missing END. line: " + env.getInput().getLine());
    }

    public static Node getHandler(Environment environment) {
        return new EndNode(environment);
    }

    public static boolean isMatch(LexicalType type) { return first.contains(type); }

    public String toString() { return "end"; }

    public Value getValue() throws Exception {
        System.exit(0);
        return null;
    }
}
