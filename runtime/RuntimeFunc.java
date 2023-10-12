package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspName;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
    RuntimeScope scop;
    AspFuncDef func;
    String name;

    public RuntimeFunc(AspFuncDef def, RuntimeScope scope, String navn) {
        func = def;
        scop = scope;
        name = navn;
    }

    public RuntimeFunc(String navn) {
        name = navn;
    }

    @Override
    String typeName() {
        return "func";
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        if (actualParams.size() == func.names.size()) {
            RuntimeScope ny_scope = new RuntimeScope(scop);
            for (int i = 0; i < actualParams.size(); i++) {
                ny_scope.assign(func.names.get(i).name, actualParams.get(i));
            }
            try {
                func.body.eval(ny_scope);

            } catch (RuntimeReturnValue e) {
                return e.value;

            }
        } else {
            runtimeError("Feil nummer antall paramter" + actualParams + "!=" + func.names.size(), where);
        }

        return new RuntimeNoneValue();

    }
}
