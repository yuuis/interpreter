package newlang4.node;

import newlang3.LexicalType;
import newlang3.Value;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// <call_sub> ::=
// <NAME> <expr_list>
// <call_func> ::=
// <NAME> <LP> <expr_list> <RP>

public class CallSubNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME));
    String name;
    ExprListNode arguments;

    private CallSubNode(Environment env) {
        super(env);
        type = NodeType.FUNCTION_CALL;
    }

    public void parse() throws Exception {
        boolean bracketFlag = false;
        LexicalType inputType = env.getInput().peep(1).getType();

        // get function name
        this.name = env.getInput().get().getValue().getSValue();

        // when `(`
        if (inputType == LexicalType.LP) {
            bracketFlag = true;
            env.getInput().get();
        }

        // get arguments and parse
        if (ExprListNode.isMatch(inputType)) {
            this.arguments = ExprListNode.getHandler(env);
            this.arguments.parse();
        }

        // check bracket is closed
        if (bracketFlag) {
            if(env.getInput().get().getType() != LexicalType.RP) throw new Exception("syntax error. missing closing bracket. line: " + env.getInput().getLine());
        }
    }

    public static Node getHandler(Environment environment) {
        return new CallSubNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() { return "function: " + name + "(" + arguments + ")\n"; }

    public Value getValue() throws Exception {
        return env.getFunction(name).invoke(arguments);
    }
}
