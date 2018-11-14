package newlang4;

import newlang3.*;
import newlang4.node.*;

import java.io.FileInputStream;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        FileInputStream fin = null;
        LexicalAnalyzer lex;
        LexicalUnit first;
        Environment env;
        Node program;

        System.out.println("basic parser");
        fin = new FileInputStream("test.txt");
        lex = new LexicalAnalyzerImpl(fin);
        env = new Environment(lex);
        first = lex.get();

        program = ProgramNode.isMatch(env, first);
        if (program != null && program.Parse()) {
            System.out.println(program);
            System.out.println("value = " + program.getValue());
        }
        else System.out.println("syntax error");
    }

}
