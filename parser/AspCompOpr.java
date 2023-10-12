package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspCompOpr extends AspSyntax {

    TokenKind comp_opr;

    AspCompOpr(int n) {
        super(n);
    }

    static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr aspCompOpr = new AspCompOpr(s.curLineNum());
        if (s.isCompOpr()) {
            aspCompOpr.comp_opr = s.curToken().kind;
            skip(s, s.curToken().kind);
        } else {

            parserError("Expected an comp opr but found a " + s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("comp opr");
        return aspCompOpr;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + comp_opr + " ");

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }

}
