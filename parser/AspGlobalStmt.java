package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

// Det er mulig å deklarere flere globale variabler med kun global kall
// ved å skille variablene med en komma (hvorfor vi har en liste med AspName)
public class AspGlobalStmt extends AspSmallStmt {
    ArrayList<AspName> names = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
    }

    @Override
    public void prettyPrint() {
        prettyWrite("global ");
        int nPrinted = 0;
        for (AspName x : names) {
            if (nPrinted > 0) {
                prettyWrite(", ");
            }
            x.prettyPrint();
            ++nPrinted;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspName aspName : names) {
            curScope.registerGlobalName(aspName.name);
        }
        return null;
    }

    public static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt aspGlobalStmt = new AspGlobalStmt(s.curLineNum());
        skip(s, TokenKind.globalToken);
        while (true) {
            aspGlobalStmt.names.add(AspName.parse(s));
            if (s.curToken().kind != TokenKind.commaToken) {
                break;
            }
            skip(s, TokenKind.commaToken);
        }
        leaveParser("global stmt");
        return aspGlobalStmt;
    }

}
