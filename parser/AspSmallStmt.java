package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

// Så en small stmt er en "sesjon med kode"
// Fokuset mitt nå er på assigment og expr stmt; 
// Det som er spesielt med assigment og expr er at begge kodene begynner med nameToken
// Du kan skille mellom dem med å sjekke om det neste token er en equalToken
// Hvis neste er en equalToken er det en assigment ellers er det en expr stmt
// De andre kan lett sjekkes med å se hva curToken er
// Men det skal skjøres tester i alle
public abstract class AspSmallStmt extends AspSyntax {

    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");

        AspSmallStmt aspSmallS = null;
        switch (s.curToken().kind) {
            case returnToken:
                aspSmallS = AspReturnStmt.parse(s);
                break;
            case globalToken:
                aspSmallS = AspGlobalStmt.parse(s);
                break;
            case passToken:
                aspSmallS = AspPassStmt.parse(s);
                break;
            default:
                // Scanner.anyEqualToken: Hvis det returnerer True skal man parse gjennom
                // Assigment ellers skal man parse gjennom ExprStmt
                if (s.anyEqualToken()) {
                    aspSmallS = AspAssignment.parse(s);
                    break;
                } else {
                    aspSmallS = AspExprStmt.parse(s);
                    break;
                }

        }

        leaveParser("small stmt");
        return aspSmallS;

    }

    @Override
    public void prettyPrint() {
    }

}
