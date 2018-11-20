package newlang4.node;

import newlang3.*;
import newlang4.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StmtNode extends Node {
    static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList());

    public static Node getHandler(LexicalType lexicalType) {
        switch (lexicalType) {

        }
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
}
