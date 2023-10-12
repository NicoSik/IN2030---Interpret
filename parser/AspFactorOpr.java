package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorOpr extends AspSyntax {
    TokenKind opr;

    AspFactorOpr(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + opr + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr aspFactorOpr = new AspFactorOpr(s.curLineNum());
        if (s.isFactorOpr()) {
            aspFactorOpr.opr = s.curToken().kind;
            skip(s, s.curToken().kind);
        }
        leaveParser("factor opr");
        return aspFactorOpr;
    }

}