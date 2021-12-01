package org.femelloffm.cloudnative.tema1.servlets;

import org.femelloffm.cloudnative.tema1.configurations.CalculatorConfig;
import org.femelloffm.cloudnative.tema1.exceptions.IllegalOperationException;
import org.femelloffm.cloudnative.tema1.operations.CalculatorOperation;
import org.femelloffm.cloudnative.tema1.operations.OperationName;
import org.femelloffm.cloudnative.tema1.services.CalculatorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@WebServlet(name="calculator", urlPatterns={ "/calculate/*", "/history" })
public class CalculatorServlet extends HttpServlet {

    private CalculatorService calculatorService;

    @Override
    public void init() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(CalculatorConfig.class);
        this.calculatorService = (CalculatorService) appContext.getBean("calculatorService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter responseWriter = response.getWriter();

        if (request.getServletPath().equals("/history")) {
            processHistoryRequest(responseWriter);
            return;
        }

        String pathParameter = request.getPathInfo();
        if (pathParameter == null) {
            responseWriter.println("<h2>ERROR: path parameter with operation name must be declared</h2>");
            return;
        }
        Map<String, String[]> queryParamsMap = request.getParameterMap();
        if (!queryParamsMap.containsKey("value1") || !queryParamsMap.containsKey("value2")) {
            responseWriter.println("<h2>ERROR: missing query parameters value1 and value2</h2>");
            return;
        }

        processCalculateRequest(responseWriter, pathParameter, queryParamsMap);
    }

    private void processHistoryRequest(PrintWriter responseWriter) {
        List<CalculatorOperation> calculatorHistory = calculatorService.getCalculatorHistory();
        if (calculatorHistory.isEmpty()) {
            responseWriter.println("<h2>No operations were executed by the calculator yet</h2>");
            return;
        }
        responseWriter.println("<h2>Calculator history (from oldest to most recent):</h2>");
        responseWriter.println("<ol>");
        for (CalculatorOperation calculatorOperation : calculatorHistory) {
            String[] operationData = calculatorOperation.toString().split(";");
            responseWriter.println("<h3><li>" + operationData[0] + " (" + operationData[1] + ", " +
                    operationData[2] + ") = " + operationData[3] + "</li><h3>");
        }
        responseWriter.println("</ol>");
    }

    private void processCalculateRequest(PrintWriter responseWriter, String pathParameter,
                                         Map<String, String[]> queryParamsMap) {
        try {
            OperationName operation = OperationName.valueOf(pathParameter.substring(1).toUpperCase());
            double value1 = Double.parseDouble(queryParamsMap.get("value1")[0]);
            double value2 = Double.parseDouble(queryParamsMap.get("value2")[0]);
            double result = calculatorService.executeCalculator(operation, value1, value2);

            responseWriter.printf(Locale.ROOT, "<h2>%s (%.2f, %.2f) = %.2f</h2>",
                    operation.name(), value1, value2, result);

        } catch (IllegalOperationException | IllegalArgumentException e) {
            responseWriter.println("<h2>ERROR: "+e.getMessage()+"</h2>");
        }
    }
}
