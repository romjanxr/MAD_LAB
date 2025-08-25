package com.example.calculatorapp; // Replace with your package name

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calculatorapp.databinding.ActivityMainBinding;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Declare the binding variable for the layout
    private ActivityMainBinding binding;
    
    // State variables for the calculator logic
    private String currentResult = "";
    private String currentOperation = "";
    private boolean lastNumeric = false;
    private boolean lastDot = false;
    
    // Decimal format to control the output
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        
        // Set the content view to the root of the binding
        setContentView(binding.getRoot());

        // Set up click listeners for all the buttons
        setupNumberButtonListeners();
        setupOperatorButtonListeners();
        setupFunctionButtonListeners();
    }

    /**
     * Sets up click listeners for the number buttons (0-9) and the decimal point.
     */
    private void setupNumberButtonListeners() {
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                currentResult += button.getText().toString();
                binding.resultTextView.setText(currentResult);
                lastNumeric = true;
            }
        };

        binding.button0.setOnClickListener(numberListener);
        binding.button1.setOnClickListener(numberListener);
        binding.button2.setOnClickListener(numberListener);
        binding.button3.setOnClickListener(numberListener);
        binding.button4.setOnClickListener(numberListener);
        binding.button5.setOnClickListener(numberListener);
        binding.button6.setOnClickListener(numberListener);
        binding.button7.setOnClickListener(numberListener);
        binding.button8.setOnClickListener(numberListener);
        binding.button9.setOnClickListener(numberListener);
        
        // Special handler for the dot button
        binding.buttonDot.setOnClickListener(v -> {
            if (lastNumeric && !lastDot) {
                currentResult += ".";
                binding.resultTextView.setText(currentResult);
                lastDot = true;
                lastNumeric = false;
            }
        });
    }

    /**
     * Sets up click listeners for the operator buttons (+, -, *, /).
     */
    private void setupOperatorButtonListeners() {
        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric || (currentOperation.length() > 0 && isOperator(currentOperation.charAt(currentOperation.length() - 1)))) {
                    Button button = (Button) v;
                    
                    // If the last character in the operation is an operator, replace it
                    if (isOperator(currentOperation.charAt(currentOperation.length() - 1))) {
                         currentOperation = currentOperation.substring(0, currentOperation.length() - 1);
                    }
                    
                    // Append the current result and the new operator to the operation string
                    currentOperation += binding.resultTextView.getText().toString() + button.getText().toString();
                    
                    binding.operationTextView.setText(currentOperation);
                    currentResult = "";
                    binding.resultTextView.setText("0");
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };

        binding.buttonPlus.setOnClickListener(operatorListener);
        binding.buttonMinus.setOnClickListener(operatorListener);
        binding.buttonMultiply.setOnClickListener(operatorListener);
        binding.buttonDivide.setOnClickListener(operatorListener);
    }
    
    /**
     * Sets up click listeners for the function buttons (C, +/-, %, =).
     */
    private void setupFunctionButtonListeners() {
        
        // Clear (C) button
        binding.buttonC.setOnClickListener(v -> {
            currentOperation = "";
            currentResult = "";
            lastNumeric = false;
            lastDot = false;
            binding.operationTextView.setText("");
            binding.resultTextView.setText("0");
        });
        
        // Plus/Minus (+/-) button
        binding.buttonPlusMinus.setOnClickListener(v -> {
            if (currentResult.length() > 0 && !currentResult.equals("0")) {
                BigDecimal number = new BigDecimal(currentResult);
                number = number.negate();
                currentResult = decimalFormat.format(number);
                binding.resultTextView.setText(currentResult);
            }
        });
        
        // Percent (%) button
        binding.buttonPercent.setOnClickListener(v -> {
            if (lastNumeric) {
                BigDecimal number = new BigDecimal(currentResult);
                number = number.divide(new BigDecimal(100));
                currentResult = decimalFormat.format(number);
                binding.resultTextView.setText(currentResult);
                lastDot = false; // Reset dot since we now have a new number
            }
        });
        
        // Equals (=) button
        binding.buttonEquals.setOnClickListener(v -> {
            if (lastNumeric) {
                String expression = currentOperation + currentResult;
                try {
                    // Use a simple evaluation logic (can be replaced by a more robust one)
                    BigDecimal result = evaluateExpression(expression);
                    binding.resultTextView.setText(decimalFormat.format(result));
                    currentOperation = "";
                    currentResult = result.toString();
                    lastDot = currentResult.contains(".");
                    lastNumeric = true;
                } catch (Exception e) {
                    binding.resultTextView.setText("Error");
                    currentResult = "";
                    currentOperation = "";
                }
            }
        });
    }

    /**
     * A simple expression evaluator for demonstration purposes.
     * NOTE: This is a very basic implementation and should be replaced with a more robust
     * algorithm (like Shunting-yard) for a full-featured calculator.
     */
    private BigDecimal evaluateExpression(String expression) {
        // Simple logic for evaluation, assumes a single operator
        // and two numbers. A real calculator needs a proper parser.
        
        String[] parts = expression.split("(?<=[-+*/])|(?=[-+*/])");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid expression format");
        }
        
        BigDecimal operand1 = new BigDecimal(parts[0]);
        String operator = parts[1];
        BigDecimal operand2 = new BigDecimal(parts[2]);
        
        switch (operator) {
            case "+":
                return operand1.add(operand2);
            case "-":
                return operand1.subtract(operand2);
            case "*":
                return operand1.multiply(operand2);
            case "/":
                if (operand2.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1.divide(operand2, 10, BigDecimal.ROUND_HALF_UP);
            default:
                throw new UnsupportedOperationException("Unknown operator");
        }
    }
    
    /**
     * Helper method to check if a character is an operator.
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
