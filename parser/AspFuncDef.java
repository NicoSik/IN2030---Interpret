package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeFunc;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

// <-- tilsvarer til en funksjon i asp
// en def består av tre komponenter:
//name, kropp og en liste av names
// listen av navn tilsvarer til argumentene funksjonen håndterer
public class AspFuncDef extends AspCompoundStmt {
    AspName name;
    public AspSuite body;
    public ArrayList<AspName> names = new ArrayList<>();

    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef func = new AspFuncDef(s.curLineNum());

        skip(s, TokenKind.defToken);
        func.name = AspName.parse(s);
        skip(s, TokenKind.leftParToken);
        while (s.curToken().kind != TokenKind.rightParToken) {
            func.names.add(AspName.parse(s));
            if (s.curToken().kind == TokenKind.commaToken) {
                skip(s, TokenKind.commaToken);
            }

        }
        skip(s, TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);
        func.body = AspSuite.parse(s);
        leaveParser("func def");
        return func;

    }

    @Override
    public void prettyPrint() {
        prettyWrite("def ");
        name.prettyPrint();
        prettyWrite(" (");

        if (names.size() != 0) {
            names.get(0).prettyPrint();
            for (int i = 1; i < names.size(); i++) {
                prettyWrite(", ");
                names.get(i).prettyPrint();
            }
        }
        prettyWrite("):");
        body.prettyPrint();
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("def " + name.name);
        RuntimeFunc funk = new RuntimeFunc(this, curScope, name.name);
        curScope.assign(name.name, funk);

        return null;
    }

}
