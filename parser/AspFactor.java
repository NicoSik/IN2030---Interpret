package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorOpr> factors_Oprs = new ArrayList<>();
    ArrayList<AspFactorPrefix> factor_Pre = new ArrayList<>();
    ArrayList<AspPrimary> primary = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        if (factors_Oprs.size() == 0) {
            if (factor_Pre.size() == 1) {
                factor_Pre.get(0).prettyPrint();
            }
            primary.get(0).prettyPrint();
        }

        else if (factors_Oprs.size() > 0) {

            for (int i = 0; i < factors_Oprs.size(); i++) {

                if (i < factor_Pre.size()) {
                    factor_Pre.get(i).prettyPrint();
                }
                primary.get(i).prettyPrint();
                factors_Oprs.get(i).prettyPrint();
            }
            primary.get(primary.size() - 1).prettyPrint();

        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = primary.get(0).eval(curScope);
        if (factors_Oprs.size() == 0) {
            if (factor_Pre.size() == 1) {
                TokenKind p = factor_Pre.get(0).opr;
                switch (p) {
                    case minusToken:
                        v = v.evalNegate(this);
                        break;
                    case plusToken:
                        v = v.evalPositive(this);
                        break;
                    default:
                        Main.panic("Illegal prefix operator: " + p + "!");
                }
            }
        } else {
            for (int i = 0; i < factors_Oprs.size(); i++) {
                if (i < factor_Pre.size()) {
                    TokenKind p = factor_Pre.get(i).opr;
                    switch (p) {
                        case minusToken:
                            v = v.evalNegate(this);
                            break;
                        case plusToken:
                            v = v.evalPositive(this);
                            break;
                        default:
                            Main.panic("Illegal prefix operator: " + p + "!");
                    }
                }
                TokenKind k = factors_Oprs.get(i).opr;
                switch (k) {
                    case astToken:
                        v = v.evalMultiply(primary.get(i + 1).eval(curScope), this);
                        break;
                    case slashToken:
                        v = v.evalDivide(primary.get(i + 1).eval(curScope), this);
                        break;
                    case percentToken:
                        v = v.evalModulo(primary.get(i + 1).eval(curScope), this);
                        break;
                    case doubleSlashToken:
                        v = v.evalIntDivide(primary.get(i + 1).eval(curScope), this);
                        break;
                    default:
                        Main.panic("Illegal factor operator: " + k + "!");

                }
            }
        }
        return v;
    }

    public static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor aspFactor = new AspFactor(s.curLineNum());
        while (true) {
            if (s.isTermOpr()) {
                aspFactor.factor_Pre.add(AspFactorPrefix.parse(s));

            }
            aspFactor.primary.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) {
                break;
            }
            aspFactor.factors_Oprs.add(AspFactorOpr.parse(s));
        }
        leaveParser("factor");
        return aspFactor;
    }

}
