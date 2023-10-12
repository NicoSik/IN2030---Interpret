package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspStringLiteral;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    private HashMap<RuntimeStringValue, RuntimeValue> hash;

    public RuntimeDictValue(HashMap<RuntimeStringValue, RuntimeValue> map) {
        hash = map;
    }

    @Override
    public String toString() {
        String s = "{";
        int teller = 0;
        for (RuntimeStringValue c : hash.keySet()) {
            s += c.showInfo() + ": " + hash.get(c).showInfo();
            if (teller < hash.size() - 1)
                s += ", ";
            teller++;
        }
        return s + "}";
    }

    @Override
    String typeName() {
        return "dictionary";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (hash.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        RuntimeValue val = null;
        for (RuntimeStringValue c : hash.keySet()) {
            if (c.getStringValue(null, where).equals(v.getStringValue(null, where))) {
                val = hash.get(c);
            }
        }
        if (val == null) {
            runtimeError("Type error for +, index out of range.", where);
            return null; // Required by the compiler
        }
        return val;
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        if (inx instanceof RuntimeStringValue)
            hash.put((RuntimeStringValue) inx, val);
        else {
            runtimeError("Assigning to an element not allowed for " + typeName() + "!", where);
        }
    }

    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(hash.size());
    }

}
