import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.TerminalNode;
public class updateblocklistener extends JavaParserBaseListener{
    TokenStreamRewriter rewriter;
    boolean el_se;

    public updateblocklistener(TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
        this.el_se = false;
    }
    @Override
    public void visitTerminal(TerminalNode node) {//find else statement
        if (node.getText().equals("else")) el_se = true;
        if (node.getText().equals("if")) el_se = false;
    }


    @Override
    public void enterStatement(JavaParser.StatementContext ctx) {
        String text = ctx.getStart().getText();
        if (text.equals("if") || text.equals("while") || text.equals("do") || text.equals("for")) {
            if (!ctx.statement(0).start.getText().equals("{")) {//if statament0 else statment1
                rewriter.insertBefore(ctx.statement(0).start, "{");//start of statemnt
                rewriter.insertAfter(ctx.statement(0).stop, "}");// end of statement
            }
        } else if (el_se) {
            el_se = false;
            if (ctx.getText().length() >= 3 && ctx.getText().substring(0, 3).equals("if(")) return;// not else if(
            if (ctx.getText().charAt(0) != '{') {
                rewriter.insertBefore(ctx.getStart(), "{");
                rewriter.insertAfter(ctx.getStop(), "}");
            }
        }

    }
}
