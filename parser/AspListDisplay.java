package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr_List = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay list_Display = new AspListDisplay(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);
        while (s.curToken().kind != TokenKind.rightBracketToken) {
            list_Display.expr_List.add(AspExpr.parse(s));
            if (s.curToken().kind == TokenKind.commaToken) {
                skip(s, TokenKind.commaToken);

            }
        }
        skip(s, TokenKind.rightBracketToken);
        leaveParser("list display");
        return list_Display;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");

        if (expr_List.size() != 0) {
            expr_List.get(0).prettyPrint();
            for (int i = 1; i < expr_List.size(); i++) {
                prettyWrite(", ");
                expr_List.get(i).prettyPrint();
            }
        }
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> list = new ArrayList<>();
        for (int i = 0; i < expr_List.size(); i++) {
            list.add(expr_List.get(i).eval(curScope));
        }
        return new RuntimeListValue(list);
    }
}
