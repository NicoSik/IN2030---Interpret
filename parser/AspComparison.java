package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> termer = new ArrayList<>();
    ArrayList<AspCompOpr> comp = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = termer.get(0).eval(curScope);// aner ikke om dette er riktig men men
        for (int i = 1; i < termer.size(); i++) {
            TokenKind k = comp.get(0).comp_opr;
            switch (k) {
                case lessToken:
                    v = v.evalLess(termer.get(i).eval(curScope), this);
                    if (v.getBoolValue(null, null) == true && i < termer.size() - 1) {
                        v = termer.get(i).eval(curScope);
                    }
                    break;
                case greaterToken:
                    v = v.evalGreater(termer.get(i).eval(curScope), this);
                    if (v.getBoolValue(null, null) == true && i < termer.size() - 1) {
                        v = termer.get(i).eval(curScope);
                    }
                    break;
                case doubleEqualToken:
                    v = v.evalEqual(termer.get(i).eval(curScope), this);
                    if (v.getBoolValue(null, null) == true && i < termer.size() - 1) {
                        v = termer.get(i).eval(curScope);
                    }
                    break;
                case greaterEqualToken:
                    v = v.evalGreaterEqual(termer.get(i).eval(curScope), this);
                    if (v.getBoolValue(null, null) == true && i < termer.size() - 1) {
                        v = termer.get(i).eval(curScope);
                    }
                    break;
                case lessEqualToken:
                    v = v.evalLessEqual(termer.get(i).eval(curScope), this);
                    if (v.getBoolValue(null, null) == true && i < termer.size() - 1) {
                        v = termer.get(i).eval(curScope);
                    }
                    break;
                case notEqualToken:
                    v = v.evalNotEqual(termer.get(i).eval(curScope), this);
                    if (v.getBoolValue(null, null) == true && i < termer.size() - 1) {
                        v = termer.get(i).eval(curScope);
                    }
                    break;
                default:
                    Main.panic("Illegal factor operator: " + k + "!");
            }
        }
        // trace(v.toString());
        return v;
    }

    public static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison aspComparison = new AspComparison(s.curLineNum());
        aspComparison.termer.add(AspTerm.parse(s));
        while (s.isCompOpr()) {
            aspComparison.comp.add(AspCompOpr.parse(s));
            aspComparison.termer.add(AspTerm.parse(s));

        }

        leaveParser("comparison");
        return aspComparison;

    }

    @Override
    void prettyPrint() {
        termer.get(0).prettyPrint();
        if (comp.size() > 0) {
            for (int i = 0; i < comp.size(); i++) {
                comp.get(i).prettyPrint();
                termer.get(i + 1).prettyPrint();
            }
        }

    }

}
