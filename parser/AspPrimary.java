package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeFunc;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspPrimary extends AspSyntax {
    ArrayList<AspPrimarySuffix> aspPrimarySuffixs = new ArrayList<>();
    AspAtom atom;

    AspPrimary(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        atom.prettyPrint();
        for (AspPrimarySuffix aspPrimarySuffix : aspPrimarySuffixs) {
            aspPrimarySuffix.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = atom.eval(curScope);
        for (AspPrimarySuffix x : aspPrimarySuffixs) {
            if (x instanceof AspSubscription) {
                v = v.evalSubscription(x.eval(curScope), this);
            } else if (x instanceof AspArguments) {
                if (atom instanceof AspName) {
                    AspName navn = (AspName) atom;
                    String n = navn.name;
                    RuntimeValue funk = curScope.find(n, this);
                    if (funk instanceof RuntimeFunc) {
                        RuntimeFunc ny_funk = (RuntimeFunc) funk;
                        AspArguments liste = (AspArguments) x;
                        ArrayList<RuntimeValue> parameter = new ArrayList<>();
                        String para = "[";
                        for (AspExpr arg : liste.exprs) {
                            /// v = arg.eval(curScope);
                            RuntimeValue dd = arg.eval(curScope);
                            parameter.add(dd);

                            para += dd.showInfo();
                            if (arg != liste.exprs.get(liste.exprs.size() - 1))
                                para += ", ";
                        }
                        // Call function print with params ['31. mars', 2024]
                        trace("Call function " + navn.name + " with params " + para + "]");
                        v = ny_funk.evalFuncCall(parameter, this);

                    }
                }

            }
        }
        return v;

    }

    public static AspPrimary parse(Scanner s) {
        enterParser("primary");
        AspPrimary aspPrimary = new AspPrimary(s.curLineNum());
        aspPrimary.atom = AspAtom.parse(s);
        while (s.curToken().kind == TokenKind.leftParToken || s.curToken().kind == TokenKind.leftBracketToken) {
            aspPrimary.aspPrimarySuffixs.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("primary");
        return aspPrimary;
    }

}