import java.io.*;
import java.util.Scanner;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.TerminalNode;

public class htmlistener extends JavaParserBaseListener {//color all green and nonvisited red
    int i;
    int c = 0, end = 0;
    boolean exp = false;
    TokenStreamRewriter rewriter;
    public htmlistener(TokenStreamRewriter rewriter)  {
        this.rewriter = rewriter;
        this.i = 0;
    }

    @Override
    public void enterBlock(JavaParser.BlockContext ctx) {
        this.i++;
        boolean f=false;
        // Open the file
        File file = new File("task2.txt");
        Scanner scanner = null;//intialize scanner
        try {// found the file to create scanner
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {// throw exception if file not found
            throw new RuntimeException(e);
        }
        // Read the file contents
        while (scanner.hasNextLine()) {//read each line in the file
            String line = scanner.nextLine();
            if (line.equals("block "+this.i+" is Visited")) {//check each line contain the string block {} is visted with number in {}
                f = true;

            }

        }
        scanner.reset();
        scanner.close();
        if(!f){//if block not found in txt file color it red
            rewriter.insertBefore(ctx.getStart(),"<pre style=\"background-color:#FF4A4A;\">");//color block red
            rewriter.insertAfter(ctx.getStop(),"</pre>\n");
        }
    }

    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {//adding html tags and clor all body with green
        String HTTMLHeader = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <title>\n" +
                "        Background\n" +
                "    </title>\n" +
                "    <body style=\"margin: 0%;\">\n" +
                "        <pre style=\"background-color: rgb(181, 248, 190); margin: 0%;\">";
        rewriter.insertBefore(ctx.getStart(), "\n" + HTTMLHeader + "\n");
    }
    @Override
    public void exitCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        String HTMLFooter = "</pre>\n" +
                "    </body>\n" +
                "</html>";
        rewriter.insertAfter(ctx.getStop(), "\n" + HTMLFooter + "\n");
    }
/// branch covrage
    @Override
    public void visitTerminal(TerminalNode node) {
    if (node.getText().equals("||") || node.getText().equals("&&")) {
            exp = true;
        }
    }

    @Override
    public void enterParExpression(JavaParser.ParExpressionContext ctx) {
        exp = true;
        end = c +1;

    }

    @Override
    public void exitParExpression(JavaParser.ParExpressionContext ctx) {
        boolean reach = true;
        boolean found = false;
        for (int i = end; i <= c; i++) {
            //System.out.println(i);
            String s ="exp"+i+"is visited";
            //System.out.println(s);
            try {
                File myObj = new File("visitexpr.txt");
                Scanner myReader = new Scanner(myObj);
                boolean current_exp = false;
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.equals(s)) {
                        current_exp = true;
                    }
                }
                if (!current_exp) reach = false;
                else found = true;
                myReader.close();
            } catch (FileNotFoundException e) {
            }
        }
        if (!reach && found) {
            rewriter.insertBefore(ctx.getStart(), "<span style=\"background-color:orange;\">");
            rewriter.insertAfter(ctx.getStop(), "</span>");
        }
    }

    @Override
    public void enterExpression(JavaParser.ExpressionContext ctx) {
        if (exp && ctx.AND() == null && ctx.OR() == null) {
            c++;
            exp = false;
        }
    }


}


