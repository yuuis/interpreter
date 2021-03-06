package newlang4.node;

import newlang3.LexicalType;
import newlang3.Value;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class VariableNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME));
    private String name;
    private Value value;

    public VariableNode(String name) {
        this.name = name;
        type = NodeType.VARIABLE;
    }

    private VariableNode(Value value, Environment env) {
        super(env);
        type = NodeType.VARIABLE;
        this.value = value;
    }

    public void parse() throws Exception {
        // check NAME
        if(env.getInput().peep(1).getType() == LexicalType.NAME) {
            this.name = env.getInput().get().getValue().getSValue();
        } else throw new Exception("error. cant make NAME. line: " + env.getInput().getLine());
    }

    public static Node getHandler(Value value, Environment environment) {
        return new VariableNode(value, environment);
    }

    public String toString() {
        return "variable: " + this.name;
    }

    public Value getValue() { return this.value; }

    public void setValue(Value value) {
        this.value = value;
    }
}
