package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.RuntimeIntValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspIntegerLiteral extends AspAtom {
    long integer;

    AspIntegerLiteral(int n) {
        super(n);
    }

    static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral aspIntegerLiteral = new AspIntegerLiteral(s.curLineNum());
        aspIntegerLiteral.integer = s.curToken().integerLit;
        skip(s, TokenKind.integerToken);
        leaveParser("integer literal");
        return aspIntegerLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(String.valueOf(integer));
    }

    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeIntValue(integer);

    }

}