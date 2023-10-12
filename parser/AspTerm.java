package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factors = new ArrayList<>();
    ArrayList<AspTermOpr> oprs = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        factors.get(0).prettyPrint();
        if (oprs.size() > 0) {
            for (int i = 0; i < oprs.size(); i++) {
                oprs.get(i).prettyPrint();
                factors.get(i + 1).prettyPrint();
            }
        }

    }

    // Tatt fra forelesningen 12.10.2022 | har litt anerledes implementasjon
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = factors.get(0).eval(curScope);
        for (int i = 1; i < factors.size(); i++) {
            TokenKind k = oprs.get(i - 1).term;
            switch (k) {
                case minusToken:
                    v = v.evalSubtract(factors.get(i).eval(curScope), this);
                    break;
                case plusToken:
                    v = v.evalAdd(factors.get(i).eval(curScope), this);
                    break;
                default:
                    Main.panic("Illegal term operator: " + k + "!");
            }
        }
        // trace(v.toString());
        return v;
    }

    public static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm term = new AspTerm(s.curLineNum());
        term.factors.add(AspFactor.parse(s));
        while (s.isTermOpr()) {
            term.oprs.add(AspTermOpr.parse(s));
            term.factors.add(AspFactor.parse(s));

        }

        leaveParser("term");
        return term;
    }

}
