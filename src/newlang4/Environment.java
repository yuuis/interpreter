package newlang4;

import newlang3.*;
import java.util.HashMap;

public class Environment {
    LexicalAnalyzer input;
    HashMap var_map;

    public Environment(LexicalAnalyzer my_input) {
        input = my_input;
        var_map = new HashMap();
    }

    public LexicalAnalyzer getInput() {
        return input;
    }
}
