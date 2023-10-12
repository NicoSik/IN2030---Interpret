package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspSuite extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();
    AspSmallStmtList list;

    AspSuite(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        if (list != null) {
            list.prettyPrint();
        } else {
            prettyWriteLn();
            prettyIndent();
            for (AspStmt aspStmt : stmts) {
                aspStmt.prettyPrint();
            }
            prettyDedent();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if (list != null) {
            list.eval(curScope);
        } else {
            for (AspStmt aspStmt : stmts) {
                aspStmt.eval(curScope);
            }
        }
        return null;
    }

    public static AspSuite parse(Scanner s) {
        enterParser("suite");
        AspSuite suite = new AspSuite(s.curLineNum());
        if (s.curToken().kind == TokenKind.newLineToken) {
            skip(s, TokenKind.newLineToken);
            skip(s, TokenKind.indentToken);
            while (s.curToken().kind != TokenKind.dedentToken) {
                suite.stmts.add(AspStmt.parse(s));
            }
            skip(s, TokenKind.dedentToken);
        } else {

            suite.list = AspSmallStmtList.parse(s);
        }
        leaveParser("suite");
        return suite;
    }

}
