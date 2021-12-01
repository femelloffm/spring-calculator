package org.femelloffm.cloudnative.tema1.services;

import org.femelloffm.cloudnative.tema1.exceptions.IllegalOperationException;
import org.femelloffm.cloudnative.tema1.operations.CalculatorOperation;
import org.femelloffm.cloudnative.tema1.operations.OperationName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {
    private ApplicationContext appContext;
    private List<CalculatorOperation> calculatorHistory;

    @Autowired
    public CalculatorService(ApplicationContext appContext) {
        this.appContext = appContext;
        this.calculatorHistory = new ArrayList<>();
    }

    public double executeCalculator(OperationName operationName, double value1, double value2) throws IllegalOperationException {
        CalculatorOperation operation = (CalculatorOperation) appContext.getBean(operationName.name(), value1, value2);
        double result = operation.calculate();
        calculatorHistory.add(operation);
        return result;
    }

    public List<CalculatorOperation> getCalculatorHistory() {
        return new ArrayList<>(calculatorHistory);
    }
}
