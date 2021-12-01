package org.femelloffm.cloudnative.tema1.presentation;

import org.femelloffm.cloudnative.tema1.exceptions.IllegalOperationException;
import org.femelloffm.cloudnative.tema1.operations.CalculatorOperation;
import org.femelloffm.cloudnative.tema1.operations.OperationName;
import org.femelloffm.cloudnative.tema1.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class CalculatorMenu {
    private static final Scanner input = new Scanner(System.in);
    private CalculatorService calculatorService;

    @Autowired
    public CalculatorMenu(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public void start() {
        int option = 0;
        while(true) {
            try {
                System.out.println("---- CALCULATOR ----");
                System.out.println("[1] Sum");
                System.out.println("[2] Sub");
                System.out.println("[3] Multiply");
                System.out.println("[4] Divide");
                System.out.println("[5] Pow");
                System.out.println("[6] View calculator history");
                System.out.println("[7] Exit");
                option = input.nextInt();
                while (option < 1 || option > 7) {
                    System.out.println("ERROR: choose one option from 1 to 7");
                    option = input.nextInt();
                }

                switch(option) {
                    case 6:
                        showCalculatorHistory();
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        executeCalculator(getOperationByOption(option));
                }

            } catch(InputMismatchException ex) {
                System.out.println(ex.getMessage());
                System.out.println("ERROR: please type a numeric value\n");
                input.nextLine();
            } catch(IllegalArgumentException | IllegalOperationException ex) {
                System.out.printf("ERROR: %s\n\n", ex.getMessage());
            }
        }
    }

    private void executeCalculator(OperationName operation) throws IllegalOperationException {
        double value1 = 0, value2 = 0;
        if(operation == OperationName.POW) {
            System.out.print("Number: ");
            value1 = input.nextDouble();
            System.out.print("Exponent: ");
        }
        else {
            System.out.print("First number: ");
            value1 = input.nextDouble();
            System.out.print("Second number: ");
        }
        value2 = input.nextDouble();
        System.out.println("Result: "+calculatorService.executeCalculator(operation, value1, value2));
    }

    private void showCalculatorHistory() {
        List<CalculatorOperation> history = calculatorService.getCalculatorHistory();
        if(history.isEmpty()) {
            System.out.println("No operations were executed by the calculator yet");
            return;
        }

        System.out.println("Calculator history:");
        for (CalculatorOperation operation : history) {
            String[] operationData = operation.toString().split(";");
            System.out.println(operationData[0]+" ("+operationData[1]+", "+operationData[2]+") = "+operationData[3]);
        }
    }

    private OperationName getOperationByOption(int option) {
        switch(option) {
            case 1: return OperationName.SUM;
            case 2: return OperationName.SUB;
            case 3: return OperationName.MULTIPLY;
            case 4: return OperationName.DIVIDE;
            case 5: return OperationName.POW;
            default: throw new IllegalArgumentException("invalid operation number ["+option+"]");
        }
    }
}
