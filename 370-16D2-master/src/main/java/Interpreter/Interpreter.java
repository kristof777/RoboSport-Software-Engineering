package Interpreter;

import Model.Robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.regex.Pattern;


/**
 * The Interpreter class is responsible for parsing and handling all of the Forth Code.
 * It holds an instance of InterpreterFunctions which will be used to interface with the system.
 * Created by kristof on 15/11/16.
 */
class Interpreter {

    public final InterpreterFunctions functions;
    LinkedList<String> forthWords;
    private int currentElement;
    private Robot robot;
    private Dictionary<String, String> acceptedWords;

    public Interpreter() {
        this.functions = new InterpreterFunctions(this);
        this.loadDict();
        //

    }

    /**
     * Gets the currentElement index
     *
     * @return the index of the current element
     */

    int getCurrentElement() {
        return currentElement;
    }

    /**
     * Setter for currentElement index
     *
     * @param currentElement the index of the desired location in the ForthWords List to
     */
    void setCurrentElement(int currentElement) {
        this.currentElement = currentElement;
    }


    /**
     * Populates the acceptedwords dictionary with accepted words
     * from the file words.txt
     */
    private void loadDict() {


        BufferedReader br;

        try {
            this.acceptedWords = new Hashtable<>();
            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/java/Interpreter/words.txt"));
            for (String line; (line = br.readLine()) != null; ) {
                try {


                    String[] dictelements = line.split(": ");
                    dictelements[0] = dictelements[0].replaceAll("\\s+", "");
                    dictelements[1] = dictelements[1].replaceAll("\\s+", "");

                    //Make a dictionary key with the the looked up elements
                    this.acceptedWords.put(dictelements[0], dictelements[1]);

                } catch (SecurityException e) {
                    System.err.println("Caught SecurityException: " + e.getMessage());
                }

            }

        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    /**
     * Creates labels in the robot JSON file to keep functions and variables
     *
     * @param robot The robot whose JSON will be populated with the required functions and variables.
     */
    private void loadVariables(Robot robot) throws ForthCodeException {

        String ForthCode = robot.parsedJSON.get("code").toString();

        //Split into declaration and code portions
        String split[] = ForthCode.split(" , : play \\( -- \\) ,  ");

        //remove the 1st character
        split[0] = split[0].substring(1);
        //remove the last 4 characters
        split[1] = split[1].substring(0, split[1].length());

        //add the isolated play function to the JSON
        robot.parsedJSON.put("funcplay", split[1]);

        String variables[] = split[0].split(" , ");


        //loop through each line
        for (String variable : variables) {
            String words[] = variable.split(" ");
            //LinkedList<String> codeList = new LinkedList<String>();  MAY NEED THIS LATER
            switch (words[0]) {
                case "variable":
                    robot.parsedJSON.put("var" + words[1], "0");
                    break;
                case ":":
                    //shave off the start and end of the string so that only the code remains.
                    String[] functionBody = variable.split(" ", 2);
                    functionBody = functionBody[1].split(" ", 2);
                    functionBody[1] = functionBody[1].substring(0, functionBody[1].length());

                    //store the cleaned code indide of the JSON with the appropriate tag.
                    robot.parsedJSON.put("func" + words[1], functionBody[1]);
                    break;
                default:
                    //We found some code that needs to be executed so do that
                    LinkedList<String> wordsList = new LinkedList<>(Arrays.asList(words));
                    wordsList.add(";");
                    runCodeBody(wordsList);


                    break;
            }
        }
    }

    /**
     * Loads the String into a LinkedList with a delimiter of any white space. Any found
     * function calls will cause a recursive call to fill in the needed function code.
     *
     * @param unparsedCode A String value to be parsed
     * @return A LinkedList of parsed, valid ForthWords that is ready to be run through the InterpreterFunctions.
     */
    private LinkedList<String> lookUp(String unparsedCode) throws ForthCodeException {

        unparsedCode = unparsedCode.replaceAll("\\([^\\)]*\\)", " ");
        String parsedArr[] = unparsedCode.split(" |\n");
        LinkedList<String> codeList = new LinkedList<>();
        for (int i = 0; i < parsedArr.length; i++) {
            if (parsedArr[i] == null) {
                throw new ForthCodeException("the parsed Forth has a null character.");
            }

            //add any found integers to the code list
            if (Pattern.matches("[0-9]+", parsedArr[i])) {
                codeList.add(parsedArr[i]);
            }

            //add any found string to the code list
            else if (parsedArr[i].length() > 2 && parsedArr[i].charAt(0) == '.' && parsedArr[i].charAt(1) == '"') {
                String forthString = "";

                while (parsedArr[i].charAt(parsedArr[i].length() - 1) != '"') {

                    if (i >= parsedArr.length) {
                        throw new ForthCodeException("Malformed String");
                    }
                    forthString += parsedArr[i] + " ";
                    i++;
                }
                forthString += parsedArr[i];
                codeList.add(forthString);
            }

            //for boolean true
            else if (parsedArr[i].equals("true")) {
                codeList.add("T");
            }
            //for boolean false
            else if (parsedArr[i].equals("false")) {
                codeList.add("F");
            } else if (parsedArr[i].equals(";")) {
                codeList.add(";");
                break;
            }

            //add any found functions to the code list by recursively calling this function
            else if (this.robot.parsedJSON.containsKey("func" + parsedArr[i])) {
                LinkedList<String> addList = this.lookUp(this.robot.parsedJSON.get("func" + parsedArr[i]).toString());
                addList.remove(";");
                codeList.addAll(addList);

            }

            //add any found variables to the list along with the "var" tag
            else if (this.robot.parsedJSON.containsKey("var" + parsedArr[i])) {
                codeList.add(parsedArr[i]);
            }
            //If this is part of the Forth language, add it to the list
            else if (acceptedWords.get(parsedArr[i]) != null) {
                codeList.add(parsedArr[i]);
            }
            //throw away commas, semicolons, empty strings, and newline characters as they are in JSON files.
            //throw an error if the word has not been dealth with.
            else if (!parsedArr[i].equals(",") && !parsedArr[i].equals("\n") && !parsedArr[i].equals("")
                    && !parsedArr[i].equals("]") && !parsedArr[i].equals("[")) {
                throw new ForthCodeException("Invalid Forth Word found: " + parsedArr[i]);
            }

        }
        return codeList;


    }

    /**
     * This function acts as an interface between the external system and the interpreter.
     * It will run the Forth code within the given Robot's json file through the interpreter.
     *
     * @param robot the robot whose code should be run.
     * @throws ForthCodeException For any errors in the stack exceptions.
     */
    void play(Robot robot) throws ForthCodeException {
        this.robot = robot;
        this.functions.setRobot(robot);
        this.loadVariables(robot);


        if (robot.parsedJSON.containsKey("funcplay")) {
            this.forthWords = this.lookUp(robot.parsedJSON.get("funcplay").toString());
        } else {
            throw new ForthCodeException("Undefined Play function");
        }

        //start running the forthCode at play
        runCodeBody(forthWords);


    }

    /**
     * runs a body of Forth code after it has been parsed into a Linked list.
     *
     * @param codeList a Linked list of Forth code that will be run.
     * @throws ForthCodeException if there is an error with the Forth Code.
     */
    private void runCodeBody(LinkedList<String> codeList) throws ForthCodeException {
        functions.setRobot(robot);
        Method method;
        currentElement = 0;
        String currentWord;
        this.functions.setRobot(robot);

        //Infinite loop to allow for proper error messages
        while (true) {
            //Throw an exception if the code has no terminating ";"
            if (currentElement >= codeList.size()) {
                throw new ForthCodeException("Function ended without finding the terminating semi-colon.");
            }
            //Leave the code if ";" is found.
            if (codeList.get(currentElement).equals(";")) {
                break;
            }

            currentWord = codeList.get(currentElement);
            if (acceptedWords.get(currentWord) != null) {
                currentWord = acceptedWords.get(currentWord);
                System.out.println(currentWord);
                try {
                    method = this.functions.getClass().getMethod(currentWord);
                } catch (SecurityException e) {
                    throw new ForthCodeException("ERROR: Security error when accessing the method  " + currentWord);
                } catch (NoSuchMethodException e) {
                    throw new ForthCodeException("ERROR: no method by the name " + currentWord);
                }
                try {
                    if (method != null) {

                        method.invoke(this.functions);
                    }
                } catch (IllegalArgumentException e) {
                    throw new ForthCodeException("Error: Illegal Argument for method.invoke: " + method);
                } catch (IllegalAccessException e) {
                    throw new ForthCodeException("Error: Illegal Access for method.invoke:" + method);
                } catch (InvocationTargetException e) {
                    throw new ForthCodeException("Error: Illegal Target for method.invoke:" + method);
                }
            } else if (currentWord.equals(",") && currentWord.equals("\n") && currentWord.equals("")
                    && currentWord.equals("]") && currentWord.equals("[")) {
                throw new ForthCodeException("Error Illegal argument on the stack");
            } else {
                functions.push(currentWord);
            }
            currentElement += 1;

        }
        System.out.println("Forth finished execution.");

    }

    private class ForthCodeException extends Exception {

        ForthCodeException(String message) {
            super(message);
        }
    }


}
