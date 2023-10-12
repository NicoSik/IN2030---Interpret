
//------------------------------------------------------- Kode hentet fra IN2030 kompendiumet i figur 3.12 på side 41.| 14.09.2022

package no.uio.ifi.asp.parser;

// public 3.3 DEL 2:PARSERING AspAndTest.java

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();

    AspAndTest(int n) {
        super(n);
    }

    static AspAndTest parse(Scanner s) {
        enterParser("and test");

        AspAndTest aat = new AspAndTest(s.curLineNum());
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken)
                break;
            skip(s, andToken);
        }

        leaveParser("and test");
        return aat;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant : notTests) {
            if (nPrinted > 0)
                prettyWrite(" and ");
            ant.prettyPrint();
            ++nPrinted;
        }
    }
    // --------------------------------------------------------- Kode hentet fra
    // IN2030 kompendiumet i figur 3.12 på side 41. ^^^| 14.09.2022

    // ---------------------------------------------------------- Kode henter fra
    // IN2030 kompendiumet i figut 3.16 på side 50.| 12.10.2022
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = notTests.get(0).eval(curScope);
        for (int i = 1; i < notTests.size(); i++) {
            if (!v.getBoolValue("and operand", this))
                return v;
            v = notTests.get(i).eval(curScope);
        }
        return v;

    }
    // ---------------------------------------------------------- Kode henter fra
    // IN2030 kompendiumet i figut 3.16 på side 50.^^^| 12.10.2022
}
