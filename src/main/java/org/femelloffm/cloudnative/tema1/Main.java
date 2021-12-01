package org.femelloffm.cloudnative.tema1;

import org.femelloffm.cloudnative.tema1.configurations.CalculatorConfig;
import org.femelloffm.cloudnative.tema1.presentation.CalculatorMenu;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CalculatorConfig.class);
            CalculatorMenu mainMenu = (CalculatorMenu) applicationContext.getBean("calculatorMenu");
            mainMenu.start();

        } catch(BeansException ex) {
            System.out.println("ERROR: could not start menu");
        }
    }
}
