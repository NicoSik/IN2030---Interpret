package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspBooleanLiteral extends AspAtom {
    boolean value;
    String tf;

    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral aspBooleanLiteral = new AspBooleanLiteral(s.curLineNum());
        aspBooleanLiteral.tf = s.curToken().name;
        switch (s.curToken().kind) {
            case trueToken:
                aspBooleanLiteral.value = true;
                break;
            case falseToken:
                aspBooleanLiteral.value = false;
            default:
                break;
        }
        skip(s, s.curToken().kind);
        leaveParser("boolean literal");
        return aspBooleanLiteral;

    }

    @Override
    public void prettyPrint() {
      if(value){
        prettyWrite("True");
      }
        else{
          prettyWrite("False");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeBoolValue(value);
    }

}
