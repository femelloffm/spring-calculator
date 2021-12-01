package org.femelloffm.cloudnative.tema1.configurations;

import org.femelloffm.cloudnative.tema1.operations.*;
import org.femelloffm.cloudnative.tema1.presentation.CalculatorMenu;
import org.femelloffm.cloudnative.tema1.services.CalculatorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "org.femelloffm.cloudnative.tema1")
public class CalculatorConfig {

    @Bean(name = "SUM")
    @Scope(value = "prototype")
    public CalculatorOperation sum(double value1, double value2) {
        return new Sum(value1, value2);
    }

    @Bean(name = "SUB")
    @Scope(value = "prototype")
    public CalculatorOperation sub(double value1, double value2) {
        return new Sub(value1, value2);
    }

    @Bean(name = "MULTIPLY")
    @Scope(value = "prototype")
    public CalculatorOperation multiply(double value1, double value2) {
        return new Multiply(value1, value2);
    }

    @Bean(name = "DIVIDE")
    @Scope(value = "prototype")
    public CalculatorOperation divide(double value1, double value2) {
        return new Divide(value1, value2);
    }

    @Bean(name = "POW")
    @Scope(value = "prototype")
    public CalculatorOperation pow(double value1, double value2) {
        return new Pow(value1, value2);
    }

    @Bean(name = "calculatorService")
    public CalculatorService calculatorService(ApplicationContext appContext) {
        return new CalculatorService(appContext);
    }

    @Bean(name = "calculatorMenu")
    public CalculatorMenu calculatorMenu(CalculatorService calculatorService) {
        return new CalculatorMenu(calculatorService);
    }
}
