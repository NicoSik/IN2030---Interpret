package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspAssignment extends AspSmallStmt {
    AspName name;
    ArrayList<AspSubscription> sub = new ArrayList<>();
    AspExpr expr;

    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");
        AspAssignment aspAssStmt = new AspAssignment(s.curLineNum());
        aspAssStmt.name = AspName.parse(s);
        while (s.curToken().kind != TokenKind.equalToken) {
            aspAssStmt.sub.add(AspSubscription.parse(s));
        }

        skip(s, TokenKind.equalToken);
        aspAssStmt.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return aspAssStmt;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();
        if (sub.size() != 0) {
            for (AspSubscription s : sub) {
                s.prettyPrint();
            }
        }
        prettyWrite(" = ");
        expr.prettyPrint();

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue val = expr.eval(curScope);
        if (!sub.isEmpty()) {// has global
            RuntimeValue list = name.eval(curScope);
            String liste_e = name.name;
            for (int i = 0; i < sub.size() - 1; i++) {
                RuntimeValue indeks = sub.get(i).eval(curScope);
                list = list.evalSubscription(indeks, this);
                liste_e += "[" + indeks.showInfo() + "]";

            }

            RuntimeValue indeks = sub.get(sub.size() - 1).eval(curScope);
            list.evalAssignElem(indeks, val, this);
            trace(liste_e + "[" + indeks.showInfo() + "]" + " = " + val.showInfo());
            return null;
        } else {

        }

        curScope.assign(name.name, val);
        trace(name.name + " = " + val.showInfo());
        return null;
    }

}
