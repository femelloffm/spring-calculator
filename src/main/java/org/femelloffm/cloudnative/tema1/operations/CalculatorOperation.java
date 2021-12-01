package org.femelloffm.cloudnative.tema1.operations;

import org.femelloffm.cloudnative.tema1.exceptions.IllegalOperationException;

public interface CalculatorOperation {
    Double calculate() throws IllegalOperationException;
}
