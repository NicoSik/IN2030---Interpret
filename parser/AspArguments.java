package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspArguments extends AspPrimarySuffix {
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // RuntimeValue v = exprs.get(0).eval(curScope);
        // for (int i = 0; i < exprs.size(); i++) {
        // v = exprs.get(i).eval(curScope);

        // }
        return null;

    }

    public static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments aspArguments = new AspArguments(s.curLineNum());
        skip(s, TokenKind.leftParToken);
        while (s.curToken().kind != TokenKind.rightParToken) {
            aspArguments.exprs.add(AspExpr.parse(s));
            if (s.curToken().kind == TokenKind.commaToken) {
                skip(s, TokenKind.commaToken);
            }
        }
        skip(s, TokenKind.rightParToken);
        leaveParser("arguments");
        return aspArguments;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");

        if (exprs.size() != 0) {
            exprs.get(0).prettyPrint();
            for (int i = 1; i < exprs.size(); i++) {
                prettyWrite(", ");
                exprs.get(i).prettyPrint();
            }
        }
        prettyWrite(")");

    }
}
