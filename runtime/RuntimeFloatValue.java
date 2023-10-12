package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    String typeName() {
        return "float";
    }

    @Override
    public String toString() {
        return floatValue + "";
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long) floatValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (floatValue == 0.0) {
            return false;
        }
        return true;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue + v.getFloatValue(null, where);
            return val;
        } else if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue + v.getIntValue(null, where);
            return val;
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue / v.getFloatValue(null, where);
            return val;
        } else if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue / v.getIntValue(null, where);
            return val;
        }
        runtimeError("Type error for /.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue == v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue == v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue > v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue > v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue >= v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue >= v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = Math.floor(floatValue / v.getFloatValue(null, where));
            return val;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = Math.floor(floatValue / v.getFloatValue(null, where));
            return val;
        }
        runtimeError("Type error for //.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue < v.getFloatValue(null, where)) {
                run.boolValue = true;
            } else run.boolValue = false;
            return run;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue < v.getIntValue(null, where)) {
                run.boolValue = true;
            } else run.boolValue = false;
            return run;
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {

            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue <= v.getFloatValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeBoolValue run = new RuntimeBoolValue(false);
            if (this.floatValue <= v.getIntValue(null, where)) {
                run.boolValue = true;
            }
            return run;
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue
                    - v.getFloatValue(null, where) * Math.floor(floatValue / v.getFloatValue(null, where));
            return val;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue
                    - v.getIntValue(null, where) * Math.floor(floatValue / v.getIntValue(null, where));
            return val;
        }
        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue * v.getFloatValue(null, where);
            return val;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue * v.getIntValue(null, where);
            return val;
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler

    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        this.floatValue = this.floatValue * -1;
        return this;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (floatValue != 0.0)
            return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);

    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        RuntimeBoolValue run = new RuntimeBoolValue(false);
        if (this.floatValue != v.getFloatValue(null, where)) {
            run.boolValue = true;
        }
        return run;
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        this.floatValue = this.floatValue * 1;
        return this;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue - v.getFloatValue(null, where);
            return val;
        }
        if (v instanceof RuntimeIntValue) {
            RuntimeFloatValue val = new RuntimeFloatValue(floatValue);
            val.floatValue = floatValue - v.getFloatValue(null, where);
            return val;
        }
        runtimeError("Type error for -.", where);
        return null; // Required by the compiler
    }

}
