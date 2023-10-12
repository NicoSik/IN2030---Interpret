package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspStringLiteral extends AspAtom {
    public String str;

    AspStringLiteral(int n) {
        super(n);
    }

    static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");
        AspStringLiteral aspStringLiteral = new AspStringLiteral(s.curLineNum());
        aspStringLiteral.str = s.curToken().stringLit;
        skip(s, TokenKind.stringToken);
        leaveParser("string literal");
        return aspStringLiteral;
    }

    @Override
    public void prettyPrint() {
        if (str.contains(Character.toString('\"'))) {
            prettyWrite("'" + str + "'");

        } else {
            prettyWrite("\"" + str + "\"");

        }
    }

    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // System.out.println(str);
        return new RuntimeStringValue(str);

    }

}
