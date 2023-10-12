package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

// CompoundStmt tar for seg koder som tar deg en "nivå dyppere" kode messig:
    // if, for, while og def
// en linje med kode i asp kan bare ha en compund stmt
// og dermed er det ingen arraylist her
public abstract class AspCompoundStmt extends AspStmt {

    AspCompoundStmt(int n) {
        super(n);
    }

    static AspCompoundStmt parse(Scanner s) {
        enterParser("compound stmt");
        AspCompoundStmt aspCompoundStmt = null;
        switch (s.curToken().kind) {
            case forToken:
                aspCompoundStmt = AspForStmt.parse(s);
                break;
            case defToken:
                aspCompoundStmt = AspFuncDef.parse(s);
                break;
            case ifToken:
                aspCompoundStmt = AspIfStmt.parse(s);
                break;
            case whileToken:
                aspCompoundStmt = AspWhileStmt.parse(s);
                break;
            default:
                parserError("Expected an expression CompundStatment but found a " + s.curToken().kind + "!",
                        s.curLineNum());
        }
        leaveParser("compound stmt");
        return aspCompoundStmt;
    }

    @Override
    public void prettyPrint() {
    }
}
