package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> exprs = new ArrayList<>();
    ArrayList<AspSuite> suites1 = new ArrayList<>();
    AspSuite suite;

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt ifstmt = new AspIfStmt(s.curLineNum());
        skip(s, TokenKind.ifToken);
        while (true) {
            ifstmt.exprs.add(AspExpr.parse(s));
            skip(s, TokenKind.colonToken);
            ifstmt.suites1.add(AspSuite.parse(s));
            if (s.curToken().kind != TokenKind.elifToken) {
                break;
            }
            skip(s, TokenKind.elifToken);

        }
        if (s.curToken().kind == TokenKind.elseToken) {
            skip(s, TokenKind.elseToken);
            skip(s, TokenKind.colonToken);
            ifstmt.suite = AspSuite.parse(s);
        }
        leaveParser("if stmt");
        return ifstmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("if ");
        exprs.get(0).prettyPrint();
        prettyWrite(": ");
        suites1.get(0).prettyPrint();
        for (int i = 1; i < exprs.size(); i++) {
            prettyWrite("elif");
            exprs.get(i).prettyPrint();
            prettyWrite(":");
            suites1.get(i).prettyPrint();

        }
        if (suite != null) {
            prettyWrite("else");
            prettyWrite(":");
            suite.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (int i = 0; i < exprs.size(); i++) {
            RuntimeValue val = (exprs.get(i).eval(curScope));
            if (val.getBoolValue("If test", this)) {
                // if True alt #1: ...
                trace("if True alt #" + (i + 1) + ": ...");
                suites1.get(i).eval(curScope);
                return null;
            }
        }
        if (suite != null) {
            trace("else: ...");
            suite.eval(curScope);
        }
        return null;
    }

}
