package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspPassStmt extends AspSmallStmt {

    AspPassStmt(int n) {
        super(n);
    }

    @Override
    public void prettyPrint() {
        prettyWrite("Pass");
    }

    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 02.11.2022
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("pass");
        return null;
    }
    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 02.11.2022 ^

    static AspPassStmt parse(Scanner s) {
        enterParser("pass");
        AspPassStmt AspPass = new AspPassStmt(s.curLineNum());
        skip(s, TokenKind.passToken);
        leaveParser("pass");
        return AspPass;
    }

}
