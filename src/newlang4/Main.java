package newlang4;

import newlang3.*;
import newlang4.node.*;

import java.io.FileInputStream;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = null;
        LexicalAnalyzer lexicalAnalyzer;
        LexicalUnit first;
        Environment env;
        Node program;

        System.out.println("basic parser");
        fileInputStream = new FileInputStream("test.txt");
        lexicalAnalyzer = new LexicalAnalyzerImpl(fileInputStream);
        env = new Environment(lexicalAnalyzer);
        first = lexicalAnalyzer.get();

        program = ProgramNode.isMatch(env, first);
        if (program != null && program.Parse()) {
            System.out.println(program);
            System.out.println("value = " + program.getValue());
        }
        else System.out.println("syntax error");
    }

}
