import java.util.*;

public class Main {
    private static HashMap<String, Integer> variableMap = new HashMap<>();

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
    public static String[] trimStringArray(String[] inputArray) {
        // Create a new array to store the trimmed strings
        String[] trimmedArray = new String[inputArray.length];

        // Iterate through the input array and trim each string
        for (int i = 0; i < inputArray.length; i++) {
            trimmedArray[i] = inputArray[i].trim();
        }

        // Return the new array with trimmed strings
        return trimmedArray;
    }

    public static HashMap<String, Integer> handleAssignment(String instruction) {

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
    public static int goBackToPreviousWhile(String[]lines,int currentLine){
        String[] parts = lines[currentLine].split(" ");
        String instructionType = parts[0];


        while(!instructionType.equals("While")){
            parts = lines[currentLine].split(" ");
            instructionType = parts[0];
            currentLine--;
        }
        //System.out.println(currentLine);
        return currentLine;

    }


    public static int executeWhileLoop(String[] lines, int currentLine, int counter) {

        boolean testAnswer=testGivenCondition(lines[currentLine]);


        if(!testAnswer){
            return currentLine+counter;
        }
        if(testAnswer){
            return currentLine;
        }

        return -1;


    }

    public static int executeIfElse(String[] lines, int currentLine, int counter) {

        boolean testAnswer=testGivenCondition(lines[currentLine]);
        int doesElseExist=checkIfELSEExists(lines,currentLine);
        //System.out.println(testAnswer);

        //System.out.println(doesElseExist);


        if(doesElseExist<0&&testAnswer){

            //System.out.println(currentLine);

            return currentLine;
        }
        if(doesElseExist>0&&!testAnswer){
            //System.out.println(doesElseExist);

            //System.out.println(doesElseExist);
            return doesElseExist;
        }
        if(doesElseExist<0&&!testAnswer){
            return currentLine+counter;
        }
        if(doesElseExist>0&&testAnswer){
            return currentLine;
        }


        return -1;
    }
    public static int checkIfELSEExists(String[]lines,int whereIfStarted){



        while (!lines[whereIfStarted].trim().split(" ")[0].equals("End")) {
            String currentInstruction=lines[whereIfStarted];

            String[] currentInstructionObjecs = currentInstruction.trim().split(" ");

            if (currentInstructionObjecs[0].equals("else")){
                return whereIfStarted;
            }
            whereIfStarted++;

        }

        return -1;
    }
    public static boolean testGivenCondition(String conditionToTest){
        String[] conditionParts=conditionToTest.split(" ");
        for(int i = 0;i<conditionParts.length;i++){
            if (conditionParts[i].equals("mod")){
                conditionParts[i]="%";
            }

        }
        for (String key : variableMap.keySet()) {
            String value = String.valueOf(variableMap.get(key));
            for (int i = 0; i < conditionParts.length; i++) {
                if (key.equals(conditionParts[i])) {
                    conditionParts[i] = value;
                }
            }
            //System.out.println(Arrays.toString(conditionParts));
        }

        if(conditionParts[2].equals("<")){
            if(Integer.valueOf(conditionParts[1])<Integer.valueOf(conditionParts[3])){
                return true;
            }
        }
        if(conditionParts[2].equals(">")){
            if(Integer.valueOf(conditionParts[1])>Integer.valueOf(conditionParts[3])){
                return true;
            }

        }
        if(conditionParts[2].equals("<>")){
            if(Integer.valueOf(conditionParts[1])!=Integer.valueOf(conditionParts[3])){
                return true;
            }

        }
        if(conditionParts[2].equals("=")){
            if(Integer.valueOf(conditionParts[1])==Integer.valueOf(conditionParts[3])){
                return true;
            }

        }

        return false;
    }

    public static Integer evaluate(String instruction) {


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
            //System.out.println(Arrays.toString(expressionParts));
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
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%");
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
            case '%':
                return a % b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String x = """
                Dim num As Integer = 1
                Dim temp As integer
                
                
                While num < 11
                    temp = num 
                    temp = num % 2
                    If temp = 0
                        print num
                    End if
                    num = num + 1
                Wend
               
                """;

        String[] lines = x.toString().split("\n");
        lines = removeEmptyLines(lines);
        lines = trimStringArray(lines);
        int programCounter = 0;//program counter to track movement
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
                int programCounterBeforeloop = programCounter;
                while (!lines[programCounter].trim().equals("Wend")) {
                    programCounter++;
                    counter++;
                }

                programCounter=programCounterBeforeloop;
                programCounter=executeWhileLoop(lines, programCounter, counter);
                // Move the program counter to the line after the "Wend"

            } else if (instructionType.equals("Wend")) {

                programCounter=goBackToPreviousWhile(lines,programCounter);
            }
            else if (instructionType.equals("End")) {

                //should do nothing
            }
            else if (instructionType.equals("else")) {
                while (!lines[programCounter].trim().split(" ")[0].equals("End")) {
                    programCounter++;
                }


                //should do nothing
            }
            else if (instructionType.equals("If")) {//sends to if else

                int counter = 0;
                int programCounterBeforeIf = programCounter;

                while (!lines[programCounter].trim().split(" ")[0].equals("End")) {
                    programCounter++;
                    counter++;
                }
                programCounter=programCounterBeforeIf;

                programCounter=executeIfElse(lines, programCounter, counter);
            } else {//sends to evaluate if instruction is not given

                int evaluationResult = evaluate(currentInstruction);
                //System.out.println(evaluationResult);
            }

            programCounter++;
        }
    }
}