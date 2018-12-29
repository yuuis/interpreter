package newlang4.functions;

import newlang3.Value;
import newlang4.Function;
import newlang4.node.ExprListNode;

public class PrintFunction extends Function {
    public PrintFunction() {}

    public Value invoke(ExprListNode arguments) throws Exception {
        System.out.print(arguments.get(0).getSValue());
        return null;
    }
}
