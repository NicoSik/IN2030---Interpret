package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

//En stmt tilsvarer yil en lije med kode i en fil.
//Denne klassen har to underklasser og dermed har den ikke en liste;
// Underklassene fungerer som beholdere istedet
//Det er to typer stmt: CompoundStmt og SmallStmtList:
//CompoundStmt tar for seg koder som tar deg en "nivå dyppere":
    // if, for, while og def
//Alt annet er en SmallStmtList
public abstract class AspStmt extends AspSyntax {

    AspStmt(int n) {
        super(n);
    }

    static AspStmt parse(Scanner s) {
        enterParser("stmt");
        AspStmt aspStmt = null;

        // du vet om en stmt er en compound stmt eller en smallStmtlist
        // basert på første token i linjen
        switch (s.curToken().kind) {
            case forToken:
                aspStmt = AspCompoundStmt.parse(s);
                break;
            case defToken:
                aspStmt = AspCompoundStmt.parse(s);
                break;
            case ifToken:
                aspStmt = AspCompoundStmt.parse(s);
                break;
            case whileToken:
                aspStmt = AspCompoundStmt.parse(s);
                break;
            default:
                aspStmt = AspSmallStmtList.parse(s);
                break;
        }
        leaveParser("stmt");
        return aspStmt;
    }

    @Override
    public void prettyPrint() {

    }

}
