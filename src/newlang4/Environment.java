package newlang4;

import newlang3.*;
import newlang4.functions.PrintFunction;
import newlang4.node.VariableNode;

import java.util.HashMap;
import java.util.Hashtable;

public class Environment {
    LexicalAnalyzer input;
    HashMap<String, Function> functions;
    HashMap<String, VariableNode> variables;

    public Environment(LexicalAnalyzer my_input) {
        input = my_input;
        variables = new HashMap<>();
        functions = new HashMap<>();
        functions.put("PRINT", new PrintFunction());
    }

    public LexicalAnalyzer getInput() {
        return input;
    }

    public Function getFunction(String name) throws Exception {
        if (functions.containsKey(name)) return functions.get(name);
        else throw new Exception("error. undefined function. function name: " + name);
    }

    public VariableNode getVariable(String name) {
        if (variables.containsKey(name)) return variables.get(name);
        else {
            VariableNode variable = new VariableNode(name);
            variables.put(name, variable);
            return variable;
        }
    }
}
