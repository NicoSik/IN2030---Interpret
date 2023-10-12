package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorPrefix extends AspSyntax {
    TokenKind opr;

    AspFactorPrefix(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        prettyWrite(opr + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }

    static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix aspFactorPrefix = new AspFactorPrefix(s.curLineNum());
        switch (s.curToken().kind) {
            case plusToken:
            case minusToken:
                aspFactorPrefix.opr = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            default:
                parserError("Expected an term opr but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("factor prefix");
        return aspFactorPrefix;

    }
}
