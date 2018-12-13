package newlang4.node;

import newlang4.Node;

public class ExprNode extends Node {
}


//  オペランド オペレータ オペランド の順でしかない
//  最後は必ずオペランド
//  getしてみて name, 定数, () 以外が来たら終了
//  getOperand, getOperatorをつくってlistに入れまくる
//  ()がきたら新しくexprを新しく作る
//  まとめられるもの3つをnodeにして置き換えていく
