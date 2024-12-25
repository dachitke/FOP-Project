import java.util.*;

public class Main {
    private static HashMap<String, Integer> variableMap = new HashMap<>();

    public static void executor(String[] lines, int programCounter, int counter) {
        //this will help loops and conditionals to run code and will be same as one in main
        int auxCounter = 0;
        while (programCounter < lines.length) {
            if (auxCounter > counter) {
                break;
            }

            String currentInstruction = lines[programCounter];//this is to monitor on which line are u working on
            String[] currentInstructionObjects = currentInstruction.split(" ");// since in BASIC every object is
            // separated with space we can create a String array to store for later use

            String instructionType = currentInstructionObjects[0];
            //first object in BASIC always tells us what kind are we working on so if we assign it will help
            //System.out.println(Arrays.toString(currentInstructionObjects));

            if (instructionType.equals("Dim")) {  //sends to handleAssignment
                handleAssignment(currentInstruction);
            } else if (instructionType.equals("print")) {
                //sends to print
                print(currentInstruction);
            } else if (instructionType.equals("While")) {//sends to while loop
                executeWhileLoop(lines, programCounter, counter);
                // Move the program counter to the line after the "Wend"
                while (!lines[programCounter].trim().equals("Wend")) {
                    programCounter++;
                }
            } else if (instructionType.equals("If")) {//sends to if else
                executeIfElse(lines, programCounter, counter);
                while (!lines[programCounter].trim().equals("End If")) {
                    programCounter++;
                }
            } else if (currentInstruction == null) {//skips the line
                programCounter++;
            } else {//sends to evaluate if instruction is not given
                evaluate(currentInstruction);
            }

            programCounter++;
        }
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
            String[] parts = instruction.replace("Dim", "").trim().split("=");

            // Extract variable name
            String varName = parts[0].split("as")[0].trim(); // Get the variable name

            // Determine the variable value
            int varValue = 0; // Default value
            if (parts.length > 1) { // If a value is provided
                varValue = Integer.parseInt(parts[1].trim());
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
            String varName = instruction.replace("print", "").trim(); // Remove "print" and trim whitespace

            // Check if the variable exists in the HashMap
            if (variableMap.containsKey(varName)) {
                // Print the variable name and its value
                System.out.println(varName + " = " + variableMap.get(varName));
            } else {
                System.out.println("Variable " + varName + " not found in the HashMap.");
            }
        } catch (Exception e) {
            System.out.println("Error parsing instruction: " + e.getMessage());
        }
    }


    public static void executeWhileLoop(String[] lines, int currentLine, int counter) {
        System.out.println("i entered in loops");
        executor(lines, currentLine, counter);

    }

    public static void executeIfElse(String[] lines, int currentLine, int counter) {
        System.out.println("i entered in conditionals");
        executor(lines, currentLine, counter);

    }

    public static int evaluate(String instruction) {
        System.out.println("i entered in evaluate ");
        int result = 0;

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String x = """
                Dim num As Integer = 5
                Dim i As Integer = 1
                Dim result As Integer = 0
                
                While i <= 10
                    result = num * i
                    print result
                    i = i + 1
                Wend
                
                
                
                
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