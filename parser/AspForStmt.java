package no.uio.ifi.asp.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

// en for-loop består av tre komponenter:
// name, expression og et suite
public class AspForStmt extends AspCompoundStmt {
    AspName name;
    AspExpr expr;
    AspSuite suite;

    AspForStmt(int n) {
        super(n);
    }

    static AspForStmt parse(Scanner s) {
        enterParser("for stmt");
        AspForStmt aspForStmt = new AspForStmt(s.curLineNum());
        skip(s, TokenKind.forToken);
        aspForStmt.name = AspName.parse(s);
        skip(s, TokenKind.inToken);
        aspForStmt.expr = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        aspForStmt.suite = AspSuite.parse(s);
        leaveParser("for stmt");
        return aspForStmt;
    }

    public void prettyPrint() {
        prettyWrite("for ");
        name.prettyPrint();
        prettyWrite(" in ");
        expr.prettyPrint();
        prettyWrite(":");
        suite.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue liste = expr.eval(curScope);
        if (liste instanceof RuntimeListValue) {
            RuntimeListValue liste_liste = (RuntimeListValue) liste;
            int teller = 0;
            for (RuntimeValue v : liste_liste.a) {
                teller++;
                curScope.assign(name.name, v);
                trace("for #" + teller + ": " + name.name + " = " + v);
                suite.eval(curScope);
            }

        }
        return null;
    }

}
