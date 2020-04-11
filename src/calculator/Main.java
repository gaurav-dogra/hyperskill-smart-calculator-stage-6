package calculator;

import java.util.Scanner;

/**
 * Project: Smart Calculator
 * Stage: 6/8
 * Difficulty: Medium
 * @author gaurav
 */
public class Main {

    public static void main(String[] args) {
        SmartCalculator calculator = new SmartCalculator();
        Scanner scanner = new Scanner(System.in);

        label:
        while(true) {
                String inputExpression = scanner.nextLine();

                switch (inputExpression) {
                    case "/exit":
                        System.out.println("Bye!");
                        break label;
                    case "/help":
                        System.out.println("this application supports variables, addition and subtraction");
                        break;
                    default:
                        if (inputExpression.matches("")) {
                            break;
                        } else if (inputExpression.matches("/.*")) {
                            System.out.println("Unknown command");
                            break;
                        } else {
                            String output = calculator.processExpression(inputExpression);
                            if(output != null) {
                                System.out.println(output);
                            }
                        }
                }
        }
    }
}