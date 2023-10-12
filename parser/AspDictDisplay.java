package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.runtime.RuntimeDictValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspDictDisplay extends AspAtom {
    ArrayList<AspExpr> dict_List = new ArrayList<>();
    ArrayList<AspStringLiteral> strings = new ArrayList<>();

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s) {
        enterParser("dictionary");
        AspDictDisplay dict_Display = new AspDictDisplay(s.curLineNum());
        skip(s, TokenKind.leftBraceToken);
        while (s.curToken().kind != TokenKind.rightBraceToken) {
            dict_Display.strings.add(AspStringLiteral.parse(s));
            skip(s, TokenKind.colonToken);
            dict_Display.dict_List.add(AspExpr.parse(s));
            if (s.curToken().kind == TokenKind.rightBraceToken) {
                break;
            }
            skip(s, TokenKind.commaToken);
        }
        skip(s, TokenKind.rightBraceToken);
        leaveParser("dictionary");
        return dict_Display;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");
        for (int i = 0; i < dict_List.size(); i++) {
            strings.get(i).prettyPrint();
            prettyWrite(":");
            dict_List.get(i).prettyPrint();
            if (dict_List.size() > i + 1) {
                prettyWrite(", ");
            }
        }
        prettyWrite("}");

    }

    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        HashMap<RuntimeStringValue, RuntimeValue> hash = new HashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            RuntimeStringValue s = (RuntimeStringValue) strings.get(i).eval(curScope);
            RuntimeValue e = dict_List.get(i).eval(curScope);
            hash.put(s, e);

        }
        return new RuntimeDictValue(hash);
    }

}
