package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspTermOpr extends AspSyntax {
    TokenKind term;

    AspTermOpr(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + term + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr aspTermOpr = new AspTermOpr(s.curLineNum());
        if (s.isTermOpr()) {
            aspTermOpr.term = s.curToken().kind;
            skip(s, s.curToken().kind);

        } else {
            parserError("Expected an term opr but found a " + s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("term opr");
        return aspTermOpr;

    }
}