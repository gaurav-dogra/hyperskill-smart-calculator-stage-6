package calculator;

import java.util.*;

public class SmartCalculator {

    private Map<String, Integer> variablesAndValues = new HashMap<>();
    private  static final String INVALID_IDENTIFIER = "Invalid identifier";
    private static final String INVALID_ASSIGNMENT = "Invalid assignment";
    private static final String UNKNOWN_VARIABLE = "Unknown variable";



    public String processExpression(String inputExpression) {
        // a + b - c becomes { a, +, b, -, c }
        String[] array = generateArrayFromExpression(inputExpression);

        /* If array length is 1, and it is a known variable then return its value
        otherwise return text "Unknown variable"
         */
        if (array.length == 1) {
            String value = getValue(array[0]);
            return Objects.requireNonNullElse(value, UNKNOWN_VARIABLE);
        }

        /* a + b - c; operators are in the position of 1, 3, .... so on
            the below loop iterates through its operators
         */
        String result = null;

        String left; // variable to the left of operator
        String right; // variable or value to the right of operator
        // In a + b; a is left, b is right

        for (int i = 1; i < array.length - 1; i = i + 2) {
            // when a + b - c; while iterating through '-'
            // then 'left' is 'a' + 'b' stored in 'result' in previous iteration

            if (i == 1) {
                left = getValue(array[i - 1]);
            } else {
                left = result;
            }
            right = getValue(array[i + 1]);
            switch (array[i]) {

                case "=":
                    // expected format a = 10
                    if (array.length != 3) {
                        return INVALID_ASSIGNMENT;
                    }

                    return assignmentOperation(array[i-1], array[i+1]);

                case "+":
                    if (left == null || right == null) {
                        return UNKNOWN_VARIABLE;
                    }
                    result = String.valueOf(Integer.parseInt(left) + Integer.parseInt(right));
                    break;

                case "-":
                    if (left == null || right == null) {
                        return UNKNOWN_VARIABLE;
                    }
                    result = String.valueOf(Integer.parseInt(left) - Integer.parseInt(right));
                    break;
            }
        }
        return result;
    }

    private String assignmentOperation(String variable, String value) {

        // expected format (left = right)

        // A variable name can only be in alphabets
        if (!variable.matches("[a-zA-Z]+")) {
           return INVALID_IDENTIFIER;
        }

        //if right(assigned value) is valid but it does not exist in our map, then output is "Unknown variable"
        if (getValue(value) == null && value.matches("[a-zA-Z]+") ) {
            return UNKNOWN_VARIABLE;
        }
        // if right(assigned Value) is a number then checking that it contains digits only
        // or only alphabets if it is a variable
        if (!value.matches("[a-zA-Z]+|\\d+")) {
            return INVALID_ASSIGNMENT;
        }

        int result;
        if (variablesAndValues.containsKey(value)) {
            result = variablesAndValues.get(value);
        } else {
            try {
                result = Integer.parseInt(value);
            } catch(NumberFormatException nfe) {
                return INVALID_ASSIGNMENT;
            }
        }

        variablesAndValues.put(variable, result);
        return null; // when assignment operation is successful

    }

    private String getValue(String s) {
        if (variablesAndValues.containsKey(s)) {
            return String.valueOf(variablesAndValues.get(s));
        } else {
            try { // checking if it is an Integer number
                Integer.parseInt(s);
                return s;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private String[] generateArrayFromExpression(String inputExpression) {
        /*
         * e.g. inputExpression = a + b - c
         * returnArray = { a, +, b, -, c }
         */
        inputExpression = inputExpression.replaceAll("\\+", "£+£");
        inputExpression = inputExpression.replaceAll("-", "£-£");
        inputExpression = inputExpression.replaceAll("=", "£=£");
        inputExpression = inputExpression.replaceAll("\\s+", "");
        return inputExpression.split("£");
    }
}

