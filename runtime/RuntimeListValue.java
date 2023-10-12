package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSpinnerUI;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    public ArrayList<RuntimeValue> a;

    public RuntimeListValue(ArrayList<RuntimeValue> x) {
        a = x;
    }

    @Override
    String typeName() {
        return "list";
    }

    @Override
    public String toString() {
        String m = "[";
        for (int i = 0; i < a.size(); i++) {
            m += a.get(i).toString();
            if (a.size() > i + 1) {
                m += ", ";
            }
        }
        return m + "]";

    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (a.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        RuntimeListValue vl = new RuntimeListValue(a);
        int s = a.size();
        for (int i = 1; i < v.getIntValue(null, where); i++) {
            for (int x = 0; x < s; x++) {
                vl.a.add(vl.a.get(x));
            }
        }
        return vl;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v.getIntValue(null, where) > a.size() - 1) {
            runtimeError("Type error for +, index out of range.", where);
            return null; // Required by the compiler
        }
        RuntimeValue h = a.get((int) v.getIntValue(null, where));
        return h;
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        if (inx instanceof RuntimeIntValue)
            a.set((int) inx.getIntValue(null, where), val);
        else {
            runtimeError("Assigning to an element not allowed for " + typeName() + "!", where);
        }
    }

    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(a.size());
    }

}