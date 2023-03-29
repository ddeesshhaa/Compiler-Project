import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputFile = "code.java";
        FileInputStream inputStream = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(inputStream);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();      //first rule of grammer
        ParseTreeWalker walker = new ParseTreeWalker(); //traverse on tree with listner
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokens); //
        walker.walk(new listner1(rewriter), tree);

        File output = new File("task1.java");
        output.createNewFile();
        FileWriter w = new FileWriter("task1.java");
        w.write(rewriter.getText());
        w.close();

    }
}