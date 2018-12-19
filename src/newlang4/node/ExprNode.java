package newlang4.node;

import newlang3.LexicalType;
import newlang4.Environment;
import newlang4.Node;
import newlang4.NodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// <expr> ::=
// <expr> <ADD> <expr>
// | <expr> <SUB> <expr>
// | <expr> <MUL> <expr>
// | <expr> <DIV> <expr>
// | <SUB> <expr>
// | <LP> <expr> <RP>
// | <NAME>
// | <INTVAL>
// | <DOUBLEVAL>
// | <LITERAL>
// | <call_func>

public class ExprNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME, LexicalType.SUB, LexicalType.LP, LexicalType.INTVAL, LexicalType.DOUBLEVAL, LexicalType.LITERAL));


    private ExprNode(Environment env) {
        super(env);
        type = NodeType.EXPR;
    }

    public void parse() throws Exception {

    }

    public static Node getHandler(Environment environment) {
        return new ExprNode(environment);
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }

    public String toString() {
        return "expr";
    }
}


//  オペランド オペレータ オペランド の順でしかない
//  最後は必ずオペランド
//  getしてみて name, 定数, () 以外が来たら終了
//  getOperand, getOperatorをつくってlistに入れまくる
//  ()がきたら新しくexprを新しく作る
//  まとめられるもの3つをnodeにして置き換えていく
