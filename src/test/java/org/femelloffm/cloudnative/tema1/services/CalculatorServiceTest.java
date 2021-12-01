package org.femelloffm.cloudnative.tema1.services;

import org.femelloffm.cloudnative.tema1.configurations.CalculatorConfig;
import org.femelloffm.cloudnative.tema1.exceptions.IllegalOperationException;
import org.femelloffm.cloudnative.tema1.operations.CalculatorOperation;
import org.femelloffm.cloudnative.tema1.operations.OperationName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorConfig.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class CalculatorServiceTest {
    @Autowired
    private CalculatorService calculatorService;

    @Test
    public void shouldCreateCalculatorService() {
        assertNotNull(calculatorService);
    }

    @Test
    public void shouldExecuteSum() throws IllegalOperationException {
        double value1 = 15.75, value2 = 0.40;
        double result = calculatorService.executeCalculator(OperationName.SUM, value1, value2);
        assertEquals(value1 + value2, result);
        assertEquals(1, calculatorService.getCalculatorHistory().size());
    }

    @Test
    public void shouldExecuteSub() throws IllegalOperationException {
        double value1 = 600, value2 = 355.67;
        double result = calculatorService.executeCalculator(OperationName.SUB, value1, value2);
        assertEquals(value1 - value2, result);
        assertEquals(1, calculatorService.getCalculatorHistory().size());
    }

    @Test
    public void shouldExecuteMultiply() throws IllegalOperationException {
        double value1 = 63, value2 = 14;
        double result = calculatorService.executeCalculator(OperationName.MULTIPLY, value1, value2);
        assertEquals(value1 * value2, result);
        assertEquals(1, calculatorService.getCalculatorHistory().size());
    }

    @Test
    public void shouldExecuteDivide() throws IllegalOperationException {
        double value1 = 1, value2 = 4;
        double result = calculatorService.executeCalculator(OperationName.DIVIDE, value1, value2);
        assertEquals(value1 / value2, result);
        assertEquals(1, calculatorService.getCalculatorHistory().size());
    }

    @Test
    public void shouldNotExecuteDivisionByZero() {
        double value1 = 10, value2 = 0;
        assertThrows(IllegalOperationException.class,
                () -> calculatorService.executeCalculator(OperationName.DIVIDE, value1, value2),
                "cannot divide by zero");
        assertEquals(0, calculatorService.getCalculatorHistory().size());
    }

    @Test
    public void shouldExecutePow() throws IllegalOperationException {
        double value1 = 2, value2 = -1;
        double result = calculatorService.executeCalculator(OperationName.POW, value1, value2);
        assertEquals(Math.pow(value1, value2), result);
        assertEquals(1, calculatorService.getCalculatorHistory().size());
    }

    @Test
    public void shouldGetCalculatorHistory() throws IllegalOperationException {
        double value1 = 15.75, value2 = 0.40;
        double result1 = calculatorService.executeCalculator(OperationName.SUM, value1, value2);
        double result2 = calculatorService.executeCalculator(OperationName.SUB, value1, value2);

        List<CalculatorOperation> calculatorHistory = calculatorService.getCalculatorHistory();
        assertEquals(2, calculatorHistory.size());
        assertEquals("SUM", calculatorHistory.get(0).toString().split(";")[0]);
        assertEquals(value1, Double.parseDouble(calculatorHistory.get(0).toString().split(";")[1]));
        assertEquals(value2, Double.parseDouble(calculatorHistory.get(0).toString().split(";")[2]));
        assertEquals(result1, Double.parseDouble(calculatorHistory.get(0).toString().split(";")[3]));
        assertEquals("SUB", calculatorHistory.get(1).toString().split(";")[0]);
        assertEquals(value1, Double.parseDouble(calculatorHistory.get(1).toString().split(";")[1]));
        assertEquals(value2, Double.parseDouble(calculatorHistory.get(1).toString().split(";")[2]));
        assertEquals(result2, Double.parseDouble(calculatorHistory.get(1).toString().split(";")[3]));
    }

    @Test
    public void shouldGetEmptyHistoryIfCalculatorServiceIsNew() {
        List<CalculatorOperation> calculatorHistory = calculatorService.getCalculatorHistory();
        assertTrue(calculatorHistory.isEmpty());
    }
}
