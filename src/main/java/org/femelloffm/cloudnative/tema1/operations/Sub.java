package org.femelloffm.cloudnative.tema1.operations;

import java.util.Locale;
import java.util.Objects;

public class Sub implements CalculatorOperation {

    private double value1;
    private double value2;
    private Double result;

    public Sub(double value1, double value2) {
        this.value1 = value1;
        this.value2 = value2;
        this.result = null;
    }

    @Override
    public Double calculate() {
        result = value1 - value2;
        return result;
    }

    @Override
    public String toString() {
        if(Objects.isNull(result)) return String.format(Locale.ROOT, "SUB;%.2f;%.2f;", value1, value2);
        return String.format(Locale.ROOT, "SUB;%.2f;%.2f;%.2f", value1, value2, result);
    }
}
