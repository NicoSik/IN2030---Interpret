// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        // ------------------------------------Kode hentet fra forelsening IN2030
        // forelesningen 02.11.2022
        // len
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }
        });
        // print
        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                for (int i = 0; i < actualParams.size(); ++i) {
                    if (i > 0) {
                        System.out.print(" ");
                    }
                    System.out.print(actualParams.get(i).toString());
                }
                System.out.println();
                return new RuntimeNoneValue();
            }
        });
        // --------------------------------------------------- Kode hentet fra IN2030
        // forelesningen 02.11.2022 ^

        // input
        assign("input", new RuntimeFunc("input") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "input", where);
                RuntimeValue val = actualParams.get(0);
                if (val instanceof RuntimeStringValue) {
                    System.out.println(val.toString());
                    String inp = keyboard.nextLine();
                    return new RuntimeStringValue(inp);
                }
                runtimeError("input can only be used with strings", where);
                return new RuntimeNoneValue();
            }
        });
        // float
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "float", where);
                return new RuntimeFloatValue(
                        (float) actualParams.get(0).getFloatValue(actualParams.get(0).toString(), where));
            }
        });
        // int
        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "int", where);
                return new RuntimeIntValue(
                        (int) actualParams.get(0).getIntValue(actualParams.get(0).toString(), where));
            }
        });

        // range
        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 2, "str", where);
                RuntimeValue nr1 = actualParams.get(0);
                RuntimeValue nr2 = actualParams.get(1);
                if (nr1 instanceof RuntimeIntValue && nr2 instanceof RuntimeIntValue) {
                    long start = nr1.getIntValue(nr1.toString(), where);
                    long slutt = nr2.getIntValue(nr2.toString(), where);
                    ArrayList<RuntimeValue> int_list = new ArrayList<>();
                    if (start > slutt) {
                        return new RuntimeListValue(int_list);
                    }
                    for (long i = start; i < slutt; i++) {
                        RuntimeIntValue v = new RuntimeIntValue(i);
                        int_list.add(v);
                    }
                    return new RuntimeListValue(int_list);
                }
                runtimeError("Range can only be used with int", where);
                return new RuntimeNoneValue();

            }
        });
        // str
        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "str", where);
                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });

    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect)
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
    }
}
