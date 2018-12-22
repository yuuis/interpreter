package newlang4.node;

import newlang3.LexicalType;
import newlang3.Value;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConstNode extends Node {
    private final static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.SUB, LexicalType.INTVAL, LexicalType.DOUBLEVAL, LexicalType.LITERAL));
    private Value value;

    private ConstNode(Value value, Environment env) {
        super(env);
        switch (value.getType()) {
            case INTEGER:
                type = NodeType.INT_CONSTANT;
                break;
            case DOUBLE:
                type = NodeType.DOUBLE_CONSTANT;
                break;
            case STRING:
                type = NodeType.STRING_CONSTANT;
                break;
            default: throw new InternalError("syntax error. wrong value for const. line: " + env.getInput().getLine());
        }
        this.value = value;
    }

    public static Node getHandler(Value value, Environment environment) {
        return new ConstNode(value, environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {
        String temp = "const: ";
        switch (type) {
            case INT_CONSTANT:
                temp += value.getIntValue();
                break;
            case DOUBLE_CONSTANT:
                temp += value.getDoubleValue();
                break;
            case STRING_CONSTANT:
                temp += value.getSValue();
                break;
        }
        return temp;
    }
}
