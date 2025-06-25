import java.util.Scanner;
import java.util.ArrayList;

public class Calculator
{    
    public static void main(String[] args)
    {
        System.out.println("Welcome to the calculator application.");
        Scanner scnr = new Scanner(System.in);
        ArrayList<String> history = new ArrayList<>();

        while(true)
        {
            System.out.println("Enter math expression or type exit: ");
            String userInput = scnr.nextLine();

            if(userInput.equalsIgnoreCase("exit"))
            {
                break;
            }

            if(userInput.equalsIgnoreCase("history"))
            {
                if(history.isEmpty())
                {
                    System.out.println("No calculations yet.");
                }
                else
                {
                    for(String record : history)
                    {
                        System.out.println(record);
                    }
                
                }

                continue;

            }

            try
            {
                double result = evaluate(userInput);
                String record = userInput + " = " + result;
                history.add(record);
                System.out.println("Result: " + result);

            }
            catch(NumberFormatException nfe)
            {
                System.out.println("Enter valid numbers.");
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(iae.getMessage());
            }

        }

        System.out.println("Goodbye!");
        scnr.close();

    }

    public static double evaluate(String expr)
    {
        String[] parts = expr.trim().split("\\s+");

        if(parts.length != 3)
        {
            throw new IllegalArgumentException("Enter: <number> <op> <number>");
        }

        double a = Double.parseDouble(parts[0]);
        double b = Double.parseDouble(parts[2]);
        String op = parts[1];

        switch(op)
        {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if(b == 0) throw new IllegalArgumentException("Cannot divide by zero.");
                return a / b;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);

        }

    }

}