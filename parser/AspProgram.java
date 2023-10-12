// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {

    // Denne klassen tilsvarer til en asp program/fil
    // Hvert program inneholder en liste av stmt; en stmt er basically en linje med
    // med kode i en fil;
    // Etter itereringen av linjene og adderingen av dem i listen skal det skje en
    // ny iterering av listen av stmt
    // De skal selv utføre parsing på seg selv
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
        super(n);
    }

    // Programmet begynner herfra inn i det recursive inntrappingen
    // Hver gang du treffer en "primær token"
    // skal det skje en fjerning av en token i tokenlisten; med skip(s, tokenkind)
    // Dermed hvis det ikke blir registrert en eofToken skal det adderes en AspStmt
    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {

            // Ingen token vet hva neste token kommer til å være
            // og noen av parse metodene vil bidra med å fjerne
            // tokens fra curlineToken, men ingen av dem vil fjerne
            // newLine dermed kan det ikke være noen andre klasser
            // enn program som kan fjerne det for sjekke om vi har
            // kommet til eof eller til en ny stmt
            if (s.curToken().kind == newLineToken) {
                skip(s, newLineToken);
            }

            ap.stmts.add(AspStmt.parse(s));

        }

        leaveParser("program");
        return ap;
        // Programmet slutter her
    }

    @Override
    public void prettyPrint() {
        for (AspStmt stm : stmts) {
            stm.prettyPrint();

        }
    }

    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 08.11.2022
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspStmt as : stmts) {
            try {
                as.eval(curScope);
            } catch (RuntimeReturnValue rrv) {
                RuntimeValue.runtimeError("Return statement outside function!",
                        rrv.lineNum);
            }
        }
        return null;
    }
    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 08.11.2022^
}
