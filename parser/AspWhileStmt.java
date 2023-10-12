package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

// <-- har to komponenter den ene er en expression og suite som etterfølger while
public class AspWhileStmt extends AspCompoundStmt {

    AspExpr ex;
    AspSuite suite;

    AspWhileStmt(int n) {
        super(n);
    }

    // --------------------------------------------------- Kode hentet fra IN2030
    // forelesningen 14.09.2022 |

    static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");
        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, TokenKind.whileToken);
        aws.ex = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        aws.suite = AspSuite.parse(s);
        leaveParser("while stmt");
        return aws;
    }

    // --------------------------------------------------- Kode hentet fra IN2030
    // forelesningen 14.09.2022 ^|

    // ------------------------------------Kode hentet fra forelsening IN2030
    // forelesningen 02.11.2022
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        while (true) {
            RuntimeValue t = ex.eval(curScope);
            if (!t.getBoolValue("while loop test", this))
                break;
            trace("while True: ...");
            suite.eval(curScope);
        }
        trace("while False:");
        return null;
    }
    // --------------------------------------------------- Kode hentet fra IN2030
    // forelesningen 02.11.2022 ^

    @Override
    public void prettyPrint() {
        prettyWrite("while ");
        ex.prettyPrint();
        prettyWrite(": ");
        suite.prettyPrint();
    }

}
