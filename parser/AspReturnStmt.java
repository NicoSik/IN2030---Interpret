package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

// ikke ferdig skrevet 
// men parse-metoden skal bli tilkalt hver gang det er en returnToken
// og deretter tilkalle en parse på en AspExpr; hver returnStmt har en expr 
public class AspReturnStmt extends AspSmallStmt {
    AspExpr expr;

    AspReturnStmt(int n) {
        super(n);
    }

    @Override
    public void prettyPrint() {
        prettyWrite("return ");
        expr.prettyPrint();
    }

    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 08.11.2022
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = expr.eval(curScope);
        trace("return " + v.showInfo());
        throw new RuntimeReturnValue(v, lineNum);
    }

    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 08.11.2022^

    static AspReturnStmt parse(Scanner s) {
        enterParser("return stmt");
        AspReturnStmt aspReturnStmt = new AspReturnStmt(s.curLineNum());
        skip(s, TokenKind.returnToken);
        aspReturnStmt.expr = AspExpr.parse(s);
        leaveParser("return stmt");
        return aspReturnStmt;
    }
}
