
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


