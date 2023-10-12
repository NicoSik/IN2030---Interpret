package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspNotTest extends AspSyntax {
    AspComparison comp;
    boolean notornot;

    AspNotTest(int n) {
        super(n);
    }

    static AspNotTest parse(Scanner s) {
        enterParser("not test");
        AspNotTest aspNotTest = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == TokenKind.notToken) {
            aspNotTest.notornot = true;
            skip(s, TokenKind.notToken);
        }
        aspNotTest.comp = AspComparison.parse(s);
        leaveParser("not test");
        return aspNotTest;
    }

    @Override
    void prettyPrint() {
        if (notornot) {
            prettyWrite("not ");
        }
        comp.prettyPrint();

    }

    // Tatt fra forelesningen 12.10.2022
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = comp.eval(curScope);
        if (notornot) {
            v = v.evalNot(this);
        }
        return v;
    }

}
