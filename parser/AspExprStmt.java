package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspExprStmt extends AspSmallStmt {
    AspExpr expr;

    AspExprStmt(int n) {
        super(n);
    }

    @Override
    public void prettyPrint() {
        expr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        RuntimeValue t = expr.eval(curScope);
        trace(t.showInfo());
        return null;
    }

    static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");
        AspExprStmt aspExprStmt = new AspExprStmt(s.curLineNum());
        aspExprStmt.expr = AspExpr.parse(s);
        leaveParser("expr stmt");
        return aspExprStmt;

    }

}
