package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspPrimarySuffix extends AspSyntax {

    AspPrimarySuffix(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

    static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix aspPrimary = null;

        switch (s.curToken().kind) {
            case leftParToken:
                aspPrimary = AspArguments.parse(s);
                break;
            case leftBracketToken:
                aspPrimary = AspSubscription.parse(s);
                break;
            default:
                parserError("Expected an expression primary suffix but found a " + s.curToken().kind + "!",
                        s.curLineNum());
        }
        leaveParser("primary suffix");
        return aspPrimary;
    }

}