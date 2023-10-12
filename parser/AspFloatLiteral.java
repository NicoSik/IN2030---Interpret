package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFloatLiteral extends AspAtom {
    double integer;

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral aspFloatLiteral = new AspFloatLiteral(s.curLineNum());
        aspFloatLiteral.integer = s.curToken().floatLit;
        skip(s, TokenKind.floatToken);
        leaveParser("float literal");

        return aspFloatLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(String.valueOf(integer));
    }

    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(integer);

    }
}
