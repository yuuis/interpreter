package newlang4.functions;

import newlang3.Value;
import newlang4.Function;
import newlang4.node.ExprListNode;

public class PrintFunction extends Function {
    public PrintFunction() {}

    public Value invoke(ExprListNode arguments) throws Exception {
        switch (arguments.get(0).getType()) {
            case INTEGER:
                System.out.print(arguments.get(0).getIntValue());
                break;
            case DOUBLE:
                System.out.print(arguments.get(0).getDoubleValue());
                break;
            case STRING:
                System.out.print(arguments.get(0).getSValue());
                break;
            case BOOL:
                System.out.print(arguments.get(0).getBooleanValue());
                break;
        }
        return null;
    }
}
