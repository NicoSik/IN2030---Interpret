package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspSubscription extends AspPrimarySuffix {
    AspExpr expr;

    AspSubscription(int n) {
        super(n);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = expr.eval(curScope);
        return v;

    }

    static AspSubscription parse(Scanner s) {
        enterParser("subscription");
        AspSubscription sub = new AspSubscription(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);
        sub.expr = AspExpr.parse(s);
        skip(s, TokenKind.rightBracketToken);
        leaveParser("subscription");
        return sub;

    }

    @Override
    void prettyPrint() {
        prettyWrite("[");
        expr.prettyPrint();
        prettyWrite("]");
    }
}