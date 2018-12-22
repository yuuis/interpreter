import newlang3.*;
import newlang4.Environment;
import newlang4.Node;
import newlang4.node.*;

import java.io.FileInputStream;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream;
        LexicalAnalyzer lexicalAnalyzer;
        LexicalUnit first;
        Environment env;
        Node program;

        System.out.println("basic parser");
        fileInputStream = new FileInputStream("test1.bas");
        lexicalAnalyzer = new LexicalAnalyzerImpl(fileInputStream);
        env = new Environment(lexicalAnalyzer);
        first = lexicalAnalyzer.get();
        System.out.println(first);
        lexicalAnalyzer.unget(first);

        program = ProgramNode.getHandler(env);
        if (program != null) {
            program.parse();
            System.out.println(program);
//            System.out.println("value = " + program.getValue());
        }
        else System.out.println("syntax error");
    }
}
