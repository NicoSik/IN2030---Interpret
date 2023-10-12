package no.uio.ifi.asp.runtime;

import javax.management.StringValueExp;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String str;

    public RuntimeStringValue(String n) {
        str = n;
    }

    @Override
    String typeName() {
        return "String";
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public String showInfo() {
        if (str.indexOf('\'') >= 0)
            return '"' + str + '"';
        else
            return "'" + str + "'";
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            RuntimeBoolValue b = new RuntimeBoolValue(str.equals(str));
            return b;
        }
        runtimeError("Type error for =.", where);
        return null; // Required by the compiler

    }

    public long getIntValue(String what, AspSyntax where) {
        try {
            long tall = (long) Integer.parseInt(str);
            return tall;
        } catch (NumberFormatException e) {
            runtimeError("Type error: " + what + " is not an integer!", where);
        }
        return 0; // Required by the compiler!
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        try {
            double tall = Double.parseDouble(str);
            return tall;
        } catch (NumberFormatException e) {
            runtimeError("Type error: " + what + " is not a Float!", where);
        }
        return 0;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return str;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (str == "") {
            return false;
        }
        return true;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            // System.out.println(str);
            String ny = "";
            ny = str + v.getStringValue(v.toString(), where);
            RuntimeStringValue val = new RuntimeStringValue(ny);
            return val;
            // RuntimeStringValue val = new RuntimeStringValue(str);
            // StringBuilder sb = new StringBuilder(val.str);
            // sb.append(v.getStringValue(str, where));
            // for (int i = 1; i < sb.toString().length(); i++) {
            // if (sb.charAt(i) == '\"' || sb.charAt(i) == '\'') {
            // sb.deleteCharAt(i);
            // i--;
            // }
            // }
            // sb.append('\'');
            // sb.replace(0, 1, "'");
            // val.str = sb.toString();

            // return val;
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            RuntimeStringValue val = new RuntimeStringValue(str);
            StringBuilder sb = new StringBuilder(val.str);
            for (int i = 1; i < v.getIntValue(v.toString(), where); i++) {
                sb.append(str);
                for (int j = 1; j < sb.toString().length(); j++) {
                    // if (sb.charAt(j) == '\"') {
                    // // sb.deleteCharAt(j);
                    // j--;
                    // }
                }
            }
            val.str = sb.toString();

            return val;
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            long k = v.getIntValue(str, where);
            if (k >= str.length() || k < 0) {
                runtimeError("Index out of range.", where);

            }
            char h = str.charAt((int) k);
            String s = String.valueOf(h);
            RuntimeStringValue val = new RuntimeStringValue(s);
            return val;
        }
        runtimeError("Type error for subscription.", where);
        return null;
    }

    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(str.length());
    }

    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!v.toString().equals(str));

        }
        runtimeError("'!=' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }
}