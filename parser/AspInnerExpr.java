package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspInnerExpr extends AspAtom {
    AspExpr expr;

    AspInnerExpr(int n) {
        super(n);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = expr.eval(curScope);
        return v;
    }

    static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aspInnerExpr = new AspInnerExpr(s.curLineNum());
        skip(s, TokenKind.leftParToken);
        aspInnerExpr.expr = AspExpr.parse(s);
        skip(s, TokenKind.rightParToken);
        leaveParser("inner expr");
        return aspInnerExpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        expr.prettyPrint();
        prettyWrite(")");
    }

}
