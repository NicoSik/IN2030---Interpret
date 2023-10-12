package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspNoneLiteral extends AspAtom {

    AspNoneLiteral(int n) {
        super(n);
    }

    static AspNoneLiteral parse(Scanner s){
        enterParser("none literal");
        AspNoneLiteral aspNoneLiteral = new AspNoneLiteral(s.curLineNum());
        leaveParser("none literal");
        skip(s, TokenKind.noneToken);
        return aspNoneLiteral;
    }

}
