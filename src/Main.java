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
        String fileName = "test1.bas";

        System.out.println("basic parser");

        if (args.length > 0) fileName = args[0];
        fileInputStream = new FileInputStream(fileName);
        lexicalAnalyzer = new LexicalAnalyzerImpl(fileInputStream);
        env = new Environment(lexicalAnalyzer);

        program = ProgramNode.getHandler(env);
        if (program != null) {
            program.parse();
//            System.out.println(program);
            System.out.println(program.getValue());
        } else System.out.println("syntax error");
    }
}
