package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

// En smallstmtlist er en liste av smallStmt fra en linje med kode,
// så en linje med kode der kode er skilt fra hverandre med en ";" lager en liste av small stmt
// Hvis det ikke eksisterer en semikolon er det kun en small stmt i smallstmtlisten 
public class AspSmallStmtList extends AspStmt {

    ArrayList<AspSmallStmt> aspSmallstmt_list = new ArrayList<>();

    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList aspSmallStmtList = new AspSmallStmtList(s.curLineNum());
        while (s.curToken().kind != TokenKind.newLineToken) {
            aspSmallStmtList.aspSmallstmt_list.add(AspSmallStmt.parse(s));
            if (s.curToken().kind == TokenKind.newLineToken) {
                break;
            }
            skip(s, TokenKind.semicolonToken);
        }
        if (s.curToken().kind == TokenKind.semicolonToken) {
            skip(s, TokenKind.semicolonToken);
        }
        skip(s, TokenKind.newLineToken);
        leaveParser("small stmt list");

        return aspSmallStmtList;
    }

    @Override
    public void prettyPrint() {
        for (AspSmallStmt x : aspSmallstmt_list) {
            x.prettyPrint();
            prettyWriteLn();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspSmallStmt aspSmallStmt : aspSmallstmt_list) {
            aspSmallStmt.eval(curScope);

        }
        return null;
        // RuntimeValue v = aspSmallstmt_list.get(0).eval(curScope);
        // for (int i = 1; i < aspSmallstmt_list.size(); i++) {
        // v = aspSmallstmt_list.get(i).eval(curScope);
        // }
        // return null;
    }
}