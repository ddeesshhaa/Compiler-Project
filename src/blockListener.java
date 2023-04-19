import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.io.FileWriter;

public class blockListener extends JavaParserBaseListener {
    int i;
    TokenStreamRewriter rewriter;
    int c=0;
    boolean exp=false;
    public blockListener(TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
        this.i = 0;
    }

    @Override
    public void enterBlock(JavaParser.BlockContext ctx) {

        this.i++;
        rewriter.insertAfter(ctx.getStart(), "//block number " + this.i + "\n");


        if (i == 1) {

            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "try{\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "File output = new File(\"task2.txt\");" + "\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "output.createNewFile();" + "\n");

            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "FileWriter w" + this.i + " = new FileWriter(\"task2.txt\",true);" + "\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "w" + this.i + ".write(\"block " + this.i + " is Visited\"+\"\\n\");" + "\n");

            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "w" + this.i + ".close();" + "\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "}catch (IOException e) {throw new RuntimeException(e);}\n");
        } else {
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "try{\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "FileWriter w" + this.i + " = new FileWriter(\"task2.txt\",true);" + "\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "w" + this.i + ".write(\"block " + this.i + " is Visited\"+\"\\n\");" + "\n");

            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "w" + this.i + ".close();" + "\n");
            rewriter.insertAfter(ctx.getStart(), "\t" + "\t" + "}catch (IOException e) {throw new RuntimeException(e);}\n");
        }

    }


    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        rewriter.insertBefore(ctx.getStart(), "import java.io.File;" + "\n");
        rewriter.insertBefore(ctx.getStart(), "import java.io.FileWriter;" + "\n");
        rewriter.insertBefore(ctx.getStart(), "import java.io.IOException;" + "\n");
    }

    @Override public void enterClassBody(JavaParser.ClassBodyContext ctx) {
        rewriter.insertAfter(ctx.getStart(),"public static boolean check(int numexp) {\n" +
                "\t"  +   " try{FileWriter myWriter = new FileWriter(\"visitexpr.txt\",true);\n" +
                "\t"  +"  myWriter.write(\"exp\"+numexp+\"is visited\\n\");\n" +
                "\t"  +" myWriter.close();\n}" +
                "\t"  +" catch(Exception e){}\n"+
                "\t"  +" return false;\n" +
                "\t"  +" }\n");

    }
    @Override public void visitTerminal(TerminalNode node) {
        if(node.getText().equals("||")||node.getText().equals("&&")){exp=true;}
    }


    @Override
    public void enterParExpression(JavaParser.ParExpressionContext ctx) {exp=true;}

    @Override
    public void enterExpression(JavaParser.ExpressionContext ctx) {

        if(exp&&ctx.AND()==null&&ctx.OR()==null){//exp true and does not contain and ,or

            c++;

            rewriter.insertBefore(ctx.getStart(),"(check("+c+")||");// write number of expr visited

            rewriter.insertAfter(ctx.getStop(),")");

            exp=false;

            if(ctx.getText().charAt(0)=='(')exp=true;
        }
    }

}