package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

// <-- tilsvarer til variabler og navn på objekter og andre ting
// som er selvdeklarert med navn
public class AspName extends AspAtom {
    public String name;

    AspName(int n) {
        super(n);
    }

    static AspName parse(Scanner s) {
        enterParser("name");
        AspName aspName = new AspName(s.curLineNum());
        aspName.name = s.curToken().name;
        skip(s, TokenKind.nameToken);
        leaveParser("name");
        return aspName;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(name.replaceAll("'", "\""));
    }

    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 02.11.2022
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return curScope.find(name, this);
    }
    // --------------------------------------------------- Kode hentet fra IN2030
    // forelesningen 02.11.2022 ^

}
