package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    String typeName() {
        return "integer";
    }

    @Override
    public String toString() {
        return Long.toString(intValue);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double) intValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (intValue == 0) {
            return false;
        }
        return true;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeIntValue val = new RuntimeIntValue(intValue);
            val.intValue = intValue + v.getIntValue(null, where);
            return val;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = intValue + v.getFloatValue(null, where);
            return val;
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = intValue / v.getIntValue(null, where);
            return val;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = intValue / v.getFloatValue(null, where);
            return val;
        }
        runtimeError("Type error for /.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {

            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue == v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue == v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue > v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue > v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue >= v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue >= v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {

            RuntimeIntValue val = new RuntimeIntValue(intValue);
            val.intValue = (long) Math.floor(intValue / v.getIntValue(null, where));
            return val;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = Math.floor(intValue / v.getFloatValue(null, where));
            return val;
        }
        runtimeError("Type error for //.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue < v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue < v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {

            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue <= v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeFloatValue) {

            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.intValue <= v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeIntValue val = new RuntimeIntValue(intValue);
            val.intValue = Math.floorMod(intValue, v.getIntValue(null, where));
            return val;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = intValue
                    - v.getFloatValue(null, where) * Math.floor(intValue / v.getIntValue(null, where));
            return val;

        }
        runtimeError("Type error for %.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeIntValue val = new RuntimeIntValue(intValue);
            val.intValue = intValue * v.getIntValue(null, where);
            return val;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = intValue * v.getFloatValue(null, where);
            return val;
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        this.intValue = this.intValue * -1;
        return this;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (intValue != 0)
            return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeBoolValue run = new RuntimeBoolValue(false);
        if (this.intValue != v.getIntValue(null, where)) {
            run.boolValue = true;
        }
        return run;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        this.intValue = this.intValue * 1;
        return this;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeIntValue val = new RuntimeIntValue(intValue);
            val.intValue = intValue - v.getIntValue(null, where);
            return val;
        }
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(intValue);
            val.floatValue = intValue - v.getFloatValue(null, where);
            return val;
        }
        runtimeError("Type error for -.", where);
        return null; // Required by the compiler

    }

    // public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    // RuntimeIntValue val = new RuntimeIntValue(intValue + 1);
    // return val;

    // }
}
