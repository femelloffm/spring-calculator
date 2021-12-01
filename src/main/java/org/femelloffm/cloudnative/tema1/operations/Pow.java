package org.femelloffm.cloudnative.tema1.operations;

import java.util.Locale;
import java.util.Objects;

public class Pow implements CalculatorOperation {

    private double value;
    private double exponent;
    private Double result;

    public Pow(double value, double exponent) {
        this.value = value;
        this.exponent = exponent;
        this.result = null;
    }

    @Override
    public Double calculate() {
        result = Math.pow(value, exponent);
        return result;
    }

    @Override
    public String toString() {
        if(Objects.isNull(result)) return String.format(Locale.ROOT, "POW;%.2f;%.2f;", value, exponent);
        return String.format(Locale.ROOT, "POW;%.2f;%.2f;%.2f", value, exponent, result);
    }
}
