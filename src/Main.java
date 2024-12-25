import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static HashMap<String, Integer> variableMap = new HashMap<>();
    public static void executor(String[] lines,int programCounter,int counter){
        //this will help loops and conditionals to run code and will be same as one in main
        int auxCounter=0;
        while(programCounter< lines.length){
            if(auxCounter>counter){
                break;
            }

            String currentInstruction = lines[programCounter];//this is to monitor on which line are u working on
            String[] currentInstructionObjects= currentInstruction.split(" ");// since in BASIC every object is
            // separated with space we can create a String array to store for later use

            String instructionType = currentInstructionObjects[0];
            //first object in BASIC always tells us what kind are we working on so if we assign it will help
            //System.out.println(Arrays.toString(currentInstructionObjects));

            if(instructionType.equals("Dim")){  //sends to handleAssignment
                handleAssignment(currentInstruction);
            }
            else if(instructionType.equals("print")){//sends to print
                print(currentInstruction);
            }
            else if(instructionType.equals("While")){//sends to while loop
                executeWhileLoop(lines, programCounter,counter);
                // Move the program counter to the line after the "Wend"
                while (!lines[programCounter].trim().equals("Wend")) {
                    programCounter++;
                }
            }
            else if (instructionType.equals("If")){//sends to if else
                executeIfElse(lines,programCounter,counter);
                while (!lines[programCounter].trim().equals("End If")) {
                    programCounter++;
                }
            }
            else if(currentInstruction == null){//skips the line
                programCounter++;
            }
            else {//sends to evaluate if instruction is not given
                evaluate(currentInstruction);
            }

            programCounter++;
        }
    }

    public static HashMap<String, Integer> handleAssignment(String instruction) {
        System.out.println("i entered in assignment");

        return variableMap;
    }
    public static void print(String instruction){
        System.out.println("i entered in print");

    }
    public static void executeWhileLoop(String[] lines ,int currentLine , int counter){
        System.out.println("i entered in loops");
        executor(lines,currentLine,counter);

    }
    public static void executeIfElse(String[] lines ,int currentLine,int counter){
        System.out.println("i entered in conditionals");
        executor(lines,currentLine,counter);

    }
    public static int evaluate(String instruction){
        System.out.println("i entered in evaluate ");
        int result = 0;

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String x = """
                Dim x as Integer = 5
                Dim y as integer
                print x
                print y
                
                While asd asd
                
                x=x+5
                y=Y-1
                Wend
                
                If asd asd
                x=x-2
                Else
                y=y-1
                End If
                
                x=5+7*2
                
                
                print x
                """;

        String[] lines = x.toString().split("\n");
        int programCounter=0;
        String currentInstruction="";
        while(programCounter< lines.length){

            currentInstruction=lines[programCounter];//this is to monitor on which line are u working on
            String[] currentInstructionObjects= currentInstruction.split(" ");// since in BASIC every object is
            // separated with space we can create a String array to store for later use

            String instructionType = currentInstructionObjects[0];
            //first object in BASIC always tells us what kind are we working on so if we assign it will help
            //System.out.println(Arrays.toString(currentInstructionObjects));

            if(instructionType.equals("Dim")){  //sends to handleAssignment
                handleAssignment(currentInstruction);
            }
            else if(instructionType.equals("print")){//sends to print
                print(currentInstruction);
            }
            else if(instructionType.equals("While")){//sends to while loop
                int counter =0;
                while (!lines[programCounter].trim().equals("Wend")) {
                    programCounter++;
                    counter++;
                }
                executeWhileLoop(lines, programCounter,counter);
                // Move the program counter to the line after the "Wend"

            }
            else if (instructionType.equals("If")){//sends to if else
                int counter = 0;
                while (!lines[programCounter].trim().equals("End If")) {
                    programCounter++;
                }
                executeIfElse(lines,programCounter,counter);
            }
            else if(currentInstruction == null){//skips the line
                programCounter++;
            }
            else {//sends to evaluate if instruction is not given
                evaluate(currentInstruction);
            }

            programCounter++;
        }
    }
}