package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspAtom extends AspSyntax {

    AspAtom(int n) {
        super(n);
    }

    // --------------------------------------------------- Kode hentet fra IN2030
    // forelesningen 14.09.2022 |

    static AspAtom parse(Scanner s) {
        enterParser("atom");
        AspAtom aa = null;
        while (true) {
            switch (s.curToken().kind) {
                case falseToken:
                case trueToken:
                    aa = AspBooleanLiteral.parse(s);
                    break;
                case floatToken:
                    aa = AspFloatLiteral.parse(s);
                    break;
                case integerToken:
                    aa = AspIntegerLiteral.parse(s);
                    break;
                case leftBracketToken:
                    aa = AspListDisplay.parse(s);
                    break;
                case leftBraceToken:
                    aa = AspDictDisplay.parse(s);
                    break;
                case leftParToken:
                    aa = AspInnerExpr.parse(s);
                    break;
                case nameToken:
                    aa = AspName.parse(s);
                    break;
                case noneToken:
                    aa = AspNoneLiteral.parse(s);
                    break;
                case stringToken:
                    aa = AspStringLiteral.parse(s);
                    break;
                default:
                    parserError("Expected an expression atom but found a " + s.curToken().kind + "!", s.curLineNum());
            }
            leaveParser("atom");
            return aa;
        }

    }

    // --------------------------------------------------- Kode hentet fra IN2030
    // forelesningen 14.09.2022 ^|

    @Override
    public void prettyPrint() {
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }

}
