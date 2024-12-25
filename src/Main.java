import java.util.*;

public class Main {
    private static HashMap<String, Integer> variableMap = new HashMap<>();

    public static void executor(String[] lines, int programCounter, int counter) {

    }


    public static String[] removeEmptyLines(String[] lines) {
        // Use a dynamic list to collect non-empty lines
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) { // Check if the line is not empty after trimming
                result.add(line);
            }
        }
        // Convert the list back to a String array
        return result.toArray(new String[0]);
    }

    public static HashMap<String, Integer> handleAssignment(String instruction) {
        System.out.println("i entered in assignment");
        // "Dim varName as integer = varValue"
        // "Dim varName as integer"
        try {
            // Remove "Dim" and split the instruction into parts
            String[] parts = instruction.replace("Dim", "").trim().split(" ");

            // Extract variable name
            String varName = parts[0].split("as")[0].trim(); // Get the variable name

            // Determine the variable value
            int varValue = 0; // Default value
            if (parts.length == 5) { // If a value is provided
                varValue = Integer.parseInt(parts[4].trim());
            }
            if (parts.length == 3) { // If a value is provided
                varValue = 0;
            }

            // Assign the variable name to the value in the HashMap
            variableMap.put(varName, varValue);

        } catch (Exception e) {
            System.out.println("Error parsing instruction: " + e.getMessage());
        }

        // Return the updated HashMap

        return variableMap;
    }

    public static void print(String instruction) {
        System.out.println("i entered in print");
        try {
            // Extract the variable name from the instruction

            String[] parts = instruction.split(" ");
            String varName = parts[1];

            if (variableMap.containsKey(varName)) {
                // Print the variable name and its value
                System.out.println(variableMap.get(varName));
            } else {
                System.out.println("Variable " + varName + " not found in the HashMap.");
            }
        } catch (Exception e) {
            System.out.println("Error parsing instruction: " + e.getMessage());
        }
    }


    public static void executeWhileLoop(String[] lines, int currentLine, int counter) {
        System.out.println("i entered in loops");
        Main.executor(lines, currentLine, counter);

    }

    public static void executeIfElse(String[] lines, int currentLine, int counter) {
        System.out.println("i entered in conditionals");
        Main.executor(lines, currentLine, counter);

    }

    public static Integer evaluate(String instruction) {
        System.out.println("i entered in evaluate ");

        if (instruction.equals(null)) {
            return null;
        }
        String[] parts = instruction.split("=");


        String key2 = parts[0].trim(); // Left-hand side (variable name)
        String expression = parts[1].trim(); // Right-hand side (arithmetic expression)
        String[] expressionParts = expression.split(" ");
        boolean containsKey = false;
        for (String key : variableMap.keySet()) {
            String value = String.valueOf(variableMap.get(key));
            for (int i = 0; i < expressionParts.length; i++) {
                if (key.equals(expressionParts[i])) {
                    expressionParts[i] = value;
                }
            }
            System.out.println(Arrays.toString(expressionParts));
        }
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (String token : expressionParts) {
            if (token.equals("(")) {
                // Ignore opening parenthesis
            } else if (token.equals(")")) {
                // When encountering a closing parenthesis, evaluate the expression inside
                while (!operators.isEmpty() && operators.peek() != '(') {
                    int b = numbers.pop();
                    int a = numbers.pop();
                    char op = operators.pop();
                    numbers.push(applyOperation(a, b, op));
                }
                operators.pop(); // Remove the '(' from the stack
            } else if (isOperator(token)) {
                // Push the operator onto the stack
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    int b = numbers.pop();
                    int a = numbers.pop();
                    char op = operators.pop();
                    numbers.push(applyOperation(a, b, op));
                }
                operators.push(token.charAt(0));
            } else {
                // It's a number, push it onto the numbers stack
                numbers.push(Integer.parseInt(token));
            }
        }

        // Evaluate the remaining operators in the stack
        while (!operators.isEmpty()) {
            int b = numbers.pop();
            int a = numbers.pop();
            char op = operators.pop();
            numbers.push(applyOperation(a, b, op));
        }

        // The result is the last number in the stack

        variableMap.put(key2, numbers.pop());
        return 0;
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    private static int applyOperation(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String x = """
                Dim num As Integer = 5
                Dim n As Integer 
                
                num = 5 + num * 3 
                
                
                print num
                print n
                
                """;

        String[] lines = x.toString().split("\n");
        lines = removeEmptyLines(lines);
        int programCounter = 0;
        String currentInstruction = "";
        while (programCounter < lines.length) {

            currentInstruction = lines[programCounter];//this is to monitor on which line are u working on
            String[] currentInstructionObjects = currentInstruction.split(" ");// since in BASIC every object is
            // separated with space we can create a String array to store for later use

            String instructionType = currentInstructionObjects[0];
            //first object in BASIC always tells us what kind are we working on so if we assign it will help
            //System.out.println(Arrays.toString(currentInstructionObjects));

            if (instructionType.equals("Dim")) {  //sends to handleAssignment
                handleAssignment(currentInstruction);
            } else if (instructionType.equals("print")) {//sends to print
                print(currentInstruction);
            } else if (instructionType.equals("While")) {//sends to while loop
                int counter = 0;
                while (!lines[programCounter].trim().equals("Wend")) {
                    programCounter++;
                    counter++;
                }
                executeWhileLoop(lines, programCounter, counter);
                // Move the program counter to the line after the "Wend"

            } else if (instructionType.equals("If")) {//sends to if else
                int counter = 0;
                while (!lines[programCounter].trim().equals("End If")) {
                    programCounter++;
                    counter++;
                }
                executeIfElse(lines, programCounter, counter);
            } else {//sends to evaluate if instruction is not given
                int evaluationResult = evaluate(currentInstruction);
                System.out.println(evaluationResult);
            }

            programCounter++;
        }
    }
}