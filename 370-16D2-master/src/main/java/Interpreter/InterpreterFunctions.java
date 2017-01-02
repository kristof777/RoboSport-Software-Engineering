package Interpreter;

import Controller.GameMaster;
import Model.Coordinate;
import Model.Gameboard;
import Model.Robot;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;


/**
 * This function acts as a list of functions which will be called when parsing the Forth code.
 * Created by kristof on 15/11/16.
 */
public class InterpreterFunctions {
    private final Interpreter interpreter;
    public Stack<String> stack;
    private Stack<LoopStruct> loopStack;
    private ArrayList<Pair<ArrayList<Robot>, Integer>> inRangeRobots;
    private Robot robot;

    /**
     * Constructor which initializes the stacks.
     *
     * @param interpreter the reference to the interpreter which created this class.
     */
    public InterpreterFunctions(Interpreter interpreter) {
        this.interpreter = interpreter;
        loopStack = new Stack<LoopStruct>();
        stack = new Stack<>();


    }

    /**
     * Sets the robot which will be altered by the functions in this class
     *
     * @param robot the robot to be altered
     */
    public void setRobot(Robot robot) {
        inRangeRobots = new ArrayList<>();

        this.robot = robot;
        loopStack = new Stack<LoopStruct>();
        stack = new Stack<>();
    }

    /**
     * This function is called when the non-function words are found.
     * pop: the String element at the top of the stack and returns it
     *
     * @return the value from the stack
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public String pop() throws StackErrorException {
        if (stack.isEmpty()) {
            throw new StackErrorException("Stack is empty when trying to pop");
        }
        return stack.pop();
    }

    /**
     * Pushes a string to the stack.
     * pop
     *
     * @param arg the String argument which will be pushed to the stack
     */
    @SuppressWarnings("WeakerAccess")
    public void push(String arg) {

        stack.push(arg);
    }

    /**
     * Pops the element at the top of the stack and prints it to standard out.
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void popAndPrint() throws StackErrorException {

        System.out.println(pop());
    }

    /**
     * called when the "drop" word is found
     * Remove the value at the top of the stack
     * pop: the element at the top of the stack.
     * push: NA
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void drop() throws StackErrorException {
        pop();

    }

    /**
     * called when the "dup" word is found
     * Duplicate the value at the top of the stack
     * pop: the element at the top of the stack
     * push: two copies of the popped element
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void dup() throws StackErrorException {
        String dupElement = pop();
        stack.push(dupElement);
        stack.push(dupElement);

    }

    /**
     * called when the "swap" word is found
     * Swap the two values at the top of the stack
     * pop: the two top stack values
     * push: the swapped input values
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void swap() throws StackErrorException {
        String element1 = pop();
        String element2 = pop();
        stack.push(element1);
        stack.push(element2);

    }

    /**
     * called when the "rot" word is found
     * rotate the top three stack elements
     * pop: the top three elements
     * push: the rotated three input elements
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void rot() throws StackErrorException {
        String element1 = pop();
        String element2 = pop();
        String element3 = pop();
        stack.push(element2);
        stack.push(element1);
        stack.push(element3);

    }

    /**
     * Throws an exception if the input value is not a valid integer
     *
     * @param element A String element
     * @throws StackErrorException indicating that it was not an integer value
     */
    private void checkInt(String element) throws StackErrorException {
        if (!(Pattern.matches("[0-9]+", element))) {
            throw new StackErrorException("Stack popped an unexpected non-integer value at index " + interpreter.getCurrentElement());
        }
    }

    /**
     * Throws an exception if the input value is not a valid boolean
     *
     * @param element A String element
     * @throws StackErrorException indicating that it was not a boolean value
     */
    private void checkBool(String element) throws StackErrorException {
        if (!(Pattern.matches("(T|F)", element))) {
            throw new StackErrorException("Stack popped an unexpected non-boolean value at index " + interpreter.getCurrentElement());
        }
    }

    /**
     * Compute the result of an arithmetic operation
     * pop: two integers from the stack
     * push: an integer which is the result of the operation
     * (two integers will be pushed for divide)
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void arithmetic() throws StackErrorException {
        String operator = interpreter.forthWords.get(interpreter.getCurrentElement());
        String element1 = pop();
        String element2 = pop();

        //Check to make sure Elemetns are ints
        //add any found integers to the code list
        this.checkInt(element1);
        this.checkInt(element2);


        switch (operator) {
            //add the top two integers and pop their result to the stack
            case "+":
                stack.push((Integer.parseInt(element1) + Integer.parseInt(element2)) + "");
                break;

            //subtract the top integer from the next, pushing their difference to the stack
            case "-":
                stack.push((Integer.parseInt(element2) - Integer.parseInt(element1)) + "");
                break;

            //multiply the two top integers, pushing their product on the stack.
            case "*":
                stack.push((Integer.parseInt(element1) * Integer.parseInt(element2)) + "");
                break;

            //divide the top integer into the next, pushing the remainder and quotient (in that order).
            case "/mod":
                String quotient = Integer.parseInt(element2) / Integer.parseInt(element1) + "";
                String remainder = Integer.parseInt(element2) % Integer.parseInt(element1) + "";
                stack.push(remainder);
                stack.push(quotient);
                break;
            default:
                throw new StackErrorException("Invalid operator");
        }

    }

    /**
     * This function is called when the comparator words are found.
     * Compares the top two values on the stack and pushes a boolean character T/F
     * pop: A String containing the operator as well as two int or String elements
     * push: the resulting boolean
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("WeakerAccess")
    public void comparison() throws StackErrorException {
        String operator = interpreter.forthWords.get(interpreter.getCurrentElement());
        String element1 = pop();
        String element2 = pop();

        switch (operator) {

            case "<":
                this.checkInt(element1);
                this.checkInt(element2);
                if ((Integer.parseInt(element2) < Integer.parseInt(element1))) {
                    stack.push("T");
                } else {
                    stack.push("F");
                }
                break;

            case "<=":
                this.checkInt(element1);
                this.checkInt(element2);
                if ((Integer.parseInt(element2) <= Integer.parseInt(element1))) {
                    stack.push("T");
                } else {
                    stack.push("F");
                }
                break;

            case "=":
                if (element1.equals(element2)) {
                    stack.push("T");
                } else {
                    stack.push("F");
                }
                break;

            case "<>":

                if (!(element1.equals(element2))) {
                    stack.push("T");
                } else {
                    stack.push("F");
                }
                break;

            case "=>":
                this.checkInt(element1);
                this.checkInt(element2);
                if ((Integer.parseInt(element2) >= Integer.parseInt(element1))) {
                    stack.push("T");
                } else {
                    stack.push("F");
                }
                break;

            case ">":
                this.checkInt(element1);
                this.checkInt(element2);
                if ((Integer.parseInt(element2) > Integer.parseInt(element1))) {
                    stack.push("T");
                } else {
                    stack.push("F");
                }
                break;

            default:
                throw new StackErrorException("Invalid comparator");
        }
    }

    /**
     * This function is called when the "leave" word is found.
     * This function will call seekTo to find the exit point of the loop
     * It will then set the currentElement to the word after the exit point.
     * pop: removes the top element of the loopStack
     * push: NA
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("unused")
    public void leave() throws StackErrorException {
        LoopStruct currentLoop = loopStack.pop();

        if (currentLoop.type == LoopType.GUARDED) {
            seekTo("until");

        } else {
            seekTo("loop");
        }
    }

    /**
     * This function is called when the "and" word is found.
     * Logical and: pushes "T" if both top values of the stack
     * are "T", "F" otherwise
     * pop: two booleans from the stack
     * push: a single boolean, the logical and of the input.
     *
     * @throws StackErrorException if the popped element is null.
     */
    public void and() throws StackErrorException {

        String element1 = pop();
        String element2 = pop();
        //check values
        this.checkBool(element1);
        this.checkBool(element2);

        if (element1.equals("T") && element2.equals("T")) {
            stack.push("T");
        } else {
            stack.push("F");
        }

    }

    /**
     * This function is called when the "or" word is found.
     * Logical or: pushes "F" if both top values of the stack
     * are F, "T" otherwise
     * pop: two booleans from the stack
     * push: a single boolean, the logical or of the input.
     *
     * @throws StackErrorException if the popped element is null.
     */
    public void or() throws StackErrorException {

        String element1 = pop();
        String element2 = pop();
        //check values
        this.checkBool(element1);
        this.checkBool(element2);

        if (element1.equals("F") && element2.equals("F")) {

            stack.push("F");
        } else {
            stack.push("T");
        }

    }

    /**
     * This function is called when the "invert" word is found.
     * Invert the given boolean
     * eg. "T"/"F"
     * pop: a boolean
     * push: the inverted boolean
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("unused")
    public void invert() throws StackErrorException {

        String element1 = pop();
        //check value
        this.checkBool(element1);
        if (element1.equals("F")) {
            stack.push("T");
        } else {
            stack.push("F");
        }
    }

    /**
     * Seeks to the next equivalent word to the input.
     *
     * @param Dest The string which the search function wil stop after
     * @throws StackErrorException if the popped element is null.
     */
    private void seekTo(String Dest) throws StackErrorException {

        while (!interpreter.forthWords.get(interpreter.getCurrentElement()).equals(Dest)) {

            interpreter.setCurrentElement(interpreter.getCurrentElement() + 1);
            if (interpreter.getCurrentElement() >= interpreter.forthWords.size()) {
                throw new StackErrorException("Seek found the end of the ForthWords");
            }

        }

    }

    /**
     * This function is called when the "if" word is found.
     * Check if the if block of code should be run.  If not skip to the else block
     * pop: boolean for the if-statement
     * push: NA
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("unused")
    public void ifCondition() throws StackErrorException {

        String element = pop();
        this.checkBool(element);
        if (element.equals("F")) {
            seekTo("else");
        }

    }

    /**
     * This function is called when the "else" word is found.
     * Seek to then because this should never be seen unless the if block was executed.
     * pop: NA
     * push: NA
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("unused")
    public void elseCondition() throws StackErrorException {
        seekTo("then");
    }

    /**
     * This function is necessary because each of the found words must call a function.
     * The "then" word has no use other than a placeholder and so it calls this.
     */
    @SuppressWarnings({"unused", "EmptyMethod"})
    public void doNothing() {
        //lookPretty
    }

    /**
     * This function is called when the "begin" word is found.
     * It creates a new loopStuct to push to the loopStack.
     * pop: NA
     * push: a new loop to the loopStack
     */
    @SuppressWarnings("unused")
    public void guardedLoop() {
        LoopStruct loop = new LoopStruct();

        loop.bodyIndex = interpreter.getCurrentElement();
        loop.type = LoopType.GUARDED;
        loopStack.push(loop);


    }

    /**
     * called when the "do" word is found
     * creates a counted loop by adding a new LoopStruct to the loopStack.
     * pops: two integers for the start and end of the loop
     *
     * @throws StackErrorException if the stack was null
     */
    @SuppressWarnings("unused")
    public void countedLoop() throws StackErrorException {
        String start = pop();
        String end = pop();

        checkInt(start);
        checkInt(end);

        LoopStruct loop = new LoopStruct();
        loop.start = Integer.parseInt(start);
        loop.end = Integer.parseInt(end);
        loop.increment = loop.start;
        loop.bodyIndex = interpreter.getCurrentElement();
        loop.type = LoopType.COUNTED;
        loopStack.push(loop);


    }

    /**
     * called when the "I" word is found
     * Gets the current itteration of the most nested counted loop.
     * pops: NA
     * pushes: the value of the increment I
     *
     * @throws StackErrorException Thrown when the loopStack is empty or the current loop is of the wrong type
     */
    @SuppressWarnings("unused")
    public void iterator() throws StackErrorException {
        if (loopStack.isEmpty()) {
            throw new StackErrorException("Trying to access Itterator out of bounds");
        }
        if (loopStack.peek().type == LoopType.GUARDED) {
            throw new StackErrorException("until found when in a counted loop ");
        } else {

            int increment = loopStack.peek().increment;
            push(increment + "");
        }

    }

    /**
     * Called when the "until" word is found.
     * This will go to the beginning of the loop body if the current Stack element is "T"
     *
     * @throws StackErrorException Thrown when the loopStack is empty or the current loop is of the wrong type
     */
    @SuppressWarnings("unused")
    public void until() throws StackErrorException {
        String bool = pop();
        checkBool(bool);
        if (loopStack.isEmpty()) {
            throw new StackErrorException("until found when there are no open loops");
        }
        if (loopStack.peek().type == LoopType.COUNTED) {
            throw new StackErrorException("until found when in a counted loop ");
        }
        //move to top of loop body if stack contains "T"
        if (bool.equals("T")) {
            interpreter.setCurrentElement(loopStack.peek().bodyIndex);
        } else {

            loopStack.pop();
        }
    }

    /**
     * Called when the word "loop" is found. Moves currentElement to the top of the loop
     * body if the loop is not finished.
     *
     * @throws StackErrorException Thrown when the loopStack is empty or the current loop is of the wrong type
     */
    @SuppressWarnings("unused")
    public void loopEnd() throws StackErrorException {
        if (loopStack.isEmpty()) {
            throw new StackErrorException("loop found when there are no open loops");
        }
        if (loopStack.peek().type == LoopType.GUARDED) {
            throw new StackErrorException("counted loop end found when inside of a guarded loop");
        }
        //If loop is ended, move on
        if (loopStack.peek().increment >= loopStack.peek().end - 1) {
            loopStack.pop();
        }
        //move to loop body
        else {
            interpreter.setCurrentElement(loopStack.peek().bodyIndex);
            loopStack.peek().increment += 1;
        }

    }

    /**
     * Called when the "random" word is found
     * Generates and pushes a random integer to the stack between 0 and i
     * where i is the element which is popped from the stack.
     *
     * @throws StackErrorException Thrown when the popped element is null or a non-int
     */
    @SuppressWarnings("unused")
    public void random() throws StackErrorException {

        String element1 = pop();
        this.checkInt(element1);
        int max = (Integer.parseInt(element1));

        //push the random int between 0 and max inclusive
        push((ThreadLocalRandom.current().nextInt(0, max + 1)) + "");


    }

    /**
     * Called when the "?" word is found
     * Loads a variable from the Robot's JSON
     *
     * @throws StackErrorException Thrown when the popped element is null it was an incorrect variable.
     */
    @SuppressWarnings("unused")
    public void load() throws StackErrorException {
        String variable = pop();
        if (robot.parsedJSON.containsKey("var" + variable)) {
            push(robot.parsedJSON.get("var" + variable).toString());
        } else {
            throw new StackErrorException("Cannot load: undefined variable " + variable);
        }

    }

    /**
     * Called when the "!" word is found
     * Stores a variable from the Robot's JSON
     *
     * @throws StackErrorException Thrown when the popped element is null it was an incorrect variable.
     */
    public void store() throws StackErrorException {
        String variable = pop();
        String value = pop();
        if (robot.parsedJSON.containsKey("var" + variable)) {
            robot.parsedJSON.put("var" + variable, value);
        } else {
            throw new StackErrorException("Cannot load: undefined variable " + variable);
        }


    }

    /**
     * Called when the "health" word is found
     * push: a string representing the robot’s initial health (1–3).
     */
    void health() {

        switch (robot.getType()) {

            case "Scout":
                stack.push("1");
                break;
            case "Sniper":
                stack.push("2");
                break;
            case "Tank":
                stack.push("3");
                break;
        }
    }

    /**
     * Called when the "healthLeft" word is found
     * push:   a string representing the robot’s current health (1–3), health is consumed as damage is dealt.
     */

    void healthLeft() {
        stack.push(robot.getHealth() + "");
    }

    /**
     * Called when the "moves" word is found
     * push: the robot’s initial movement (1–3).
     */
    public void moves() {

        switch (robot.getType()) {

            case "Scout":
                stack.push("3");
                break;
            case "Sniper":
                stack.push("2");
                break;
            case "Tank":
                stack.push("1");
                break;
        }
    }

    /**
     * Called when the "movesLeft" word is found.
     * <p>
     * push: the robot’s available movement (0–3), movement
     * regenerates each turn and is consumed as moves are made.
     */
    void movesLeft() {
        stack.push(robot.getMovesRemaining() + "");
    }

    /**
     * Called when the "attack" word is found
     * Pushes the robot’s damage potential
     */
    public void attack() {
        stack.push(robot.getAttack() + "");
    }

    /**
     * Called when the "range" word is found
     * <p>
     * push: string the current robots attack range
     */
    public void range() {

        switch (robot.getType()) {

            case "Scout":
                stack.push("2");
                break;
            case "Sniper":
                stack.push("3");
                break;
            case "Tank":
                stack.push("1");
                break;
        }
    }

    /**
     * Pushes the robot's teamID.
     */
    public void team() {
        stack.push(robot.getTeamID());
    }

    /**
     * Pushes the robot's type
     */
    public void type() {
        stack.push(robot.getType());
    }

    /**
     * Turn the robot then pushes the # of turns taken by the robot
     *
     * @throws StackErrorException If the element that is popped was null
     *                             precon the item at the top of the stack is an int
     *                             pushes the direction that the robot is facing onto the stack after turning
     */
    public void turn() throws StackErrorException {

        String element1 = pop();
        this.checkInt(element1);
        int turns = Integer.parseInt(element1);

        for (int i = 0; i < turns; i++) {
            robot.turn();
        }
        stack.push(robot.getDirection() + "");
    }

    /**
     * Moves the robot forward one block in the direction it is facing.
     */
    public void move() {
        robot.move();
    }

    /**
     * Shoots in the direction and distance that are popped off the stack.  If the hex is out of range the shot is
     * fired and misses all targets.
     * <p>
     * pops:        two ints, the first is the direction, the second is the distance
     *
     * @throws StackErrorException if there is an error on the stack.
     */
    public void shoot() throws StackErrorException {
        String element1 = stack.pop();
        String element2 = stack.pop();

        this.checkInt(element1);
        this.checkInt(element2);

        int direction = Integer.parseInt(element1);
        int distance = Integer.parseInt(element2);

        robot.shoot(distance, direction);
    }

    /**
     * Called when the "check" word is found
     * pops: int   the number of clockwise turns from the current position to search from
     * push: a string describing the adjacent space
     *
     * @throws StackErrorException if the popped item was null
     */
    public void check() throws StackErrorException {

        /* need to know direction robot is facing: add pushed value to it: check that tile */
        String element1 = pop();
        Coordinate checkCoord;

        this.checkInt(element1);
        int direction = robot.getDirection() + Integer.parseInt(element1);
        if (Gameboard.robotCoordinates.containsKey(robot)) {
            Gameboard.direction(Gameboard.robotCoordinates.get(robot), direction);
        } else {
            System.err.println("Robot is being played that doesn't exist");
        }

        /* if the coordinate is null it is out of range */
        checkCoord = Gameboard.direction(Gameboard.robotCoordinates.get(robot), direction);
        if (checkCoord == null) {
            stack.push("OUT OF BOUNDS");
        }

        /* robotsOnCoordinate returns an array so if the array's size is > 0 then it's occupied else it is empty */
        if (Gameboard.robotsOnCoordinate.get(checkCoord).size() > 0) {
            stack.push("OCCUPIED");
        } else {
            stack.push("EMPTY");
        }
    }

    /**
     * Called when the "scan!" word is found
     * Scans for all visible robots (based on the robot’s
     * range), and reports how many other robots are present. Then the robot
     * can identify! each of these targets
     * <p>
     * push: the number of robots within range of the robot to the stack
     */
    @SuppressWarnings("unused")
    public void scan() {
        ArrayList<Coordinate[]> inRangeCoords = new ArrayList<>();
        ArrayList<Robot> onCoord = new ArrayList<>();
        int size = 0;

        switch (robot.getType()) {
            case "TANK":
                inRangeCoords.addAll(Gameboard.getNeighbors(Gameboard.robotCoordinates.get(robot), 1));
                break;
            case "SCOUT":
                inRangeCoords.addAll(Gameboard.getNeighbors(Gameboard.robotCoordinates.get(robot), 2));
                break;
            case "SNIPER":
                inRangeCoords.addAll(Gameboard.getNeighbors(Gameboard.robotCoordinates.get(robot), 3));
                break;
        }

        /* Get all the robots on the coordinates within range of this robot */
        /* it's  <= because the 0 index of the array list is reserved for the robot itself to allow for self
        * targeting */
        for (int i = 0; i <= robot.getRange(); i++) {
            for (int x = 0; x < inRangeCoords.get(i).length; x++) {
                onCoord.addAll(Gameboard.robotsOnCoordinate.get(inRangeCoords.get(i)[x]));
                /* since our coordinates are absolute and the robots are relative we have to account for that
                * with adding the robots current direction to the direction then % by 6*/
                inRangeRobots.add(new Pair<>(onCoord, (x + robot.getDirection()) % 6));
                size += onCoord.size();
            }
        }

        /* iterate over the list of coordinates, collecting the robots that are in range  */
        stack.push(size + "");
    }

    /**
     * Called when the "identify!" word is found.
     * identifies the given target (an index
     * in the range 0 through scan! 1 -, giving its team colour, range
     * , direction , and remaining health
     * pops: int an index for the array of robots
     * push: the information related to the robot onto the stack
     *
     * @throws StackErrorException if the popped element is null.
     */
    @SuppressWarnings("unused")
    public void identify() throws StackErrorException {
        String element1 = pop();
        int index;
        int direction = 0;
        /* assigned to null to stop warnings */
        Robot identified = null;

        this.checkInt(element1);
        index = Integer.parseInt(element1);

        for (int i = 0; i < robot.getRange(); i++) {
            for (int x = 0; x < inRangeRobots.get(i).getKey().size(); x++) {
                if (index == 0) {
                    identified = inRangeRobots.get(i).getKey().get(i);
                    direction = inRangeRobots.get(i).getValue();
                    break;
                }
                index--;
            }
        }

        stack.push((identified != null ? identified.getHealth() : 0) + "");
        stack.push(direction + "");
        stack.push(robot.getRange() + "");
        switch (robot.getColour()) {
            case RED:
                stack.push("RED");
                break;
            case ORANGE:
                stack.push("ORANGE");
                break;
            case YELLOW:
                stack.push("YELLOW");
                break;
            case GREEN:
                stack.push("GREEN");
                break;
            case BLUE:
                stack.push("BLUE");
                break;
            case PURPLE:
                stack.push("PURPLE");
                break;
            default:
                break;
        }
    }

    /**
     * Send a string to a team member specified by it's robot type.  Failure can occure if the
     * robot is dead or if it's mailbox is full.
     * <p>
     * pop:     the msg and the ID of the robot to send the msg to
     */
    @SuppressWarnings("unused")
    public void send() {
        String msg = stack.pop();
        String robotID = stack.pop();
        int id = typeToInt(robotID);


        if (GameMaster.gangs[GameMaster.findGang(robot.getRobotID())].getRobots()[id].getHealth() == 0 ||
                GameMaster.gangs[GameMaster.findGang(robot.getRobotID())].getRobots()[id].mailFull()) {
            /* do something bad here, or not??*/
            System.out.println("You try jamming the mail in but it just falls out.");
            stack.push("F");
            return;
        }

        GameMaster.gangs[GameMaster.findGang(robot.getRobotID())].getRobots()[id].receiveMsg(msg, robot.getRobotID());
        stack.push("T");
    }

    /**
     * Checks to see if a robot has a message waiting from a team member popped off the stack.
     * <p>
     * pops:      a robot type
     * pushes:     T if that team memember has a message in this robots box, F otherwise
     */
    @SuppressWarnings("unused")
    public void mesg() {
        String robotType;
        String robotID;
        robotType = stack.pop();
        int id = typeToInt(robotType);

        robotID = GameMaster.gangs[GameMaster.findGang(robot.getRobotID())].getRobots()[id].getRobotID();

        for (Pair<String, String> msg : robot.getInbox()) {
            if (msg.getKey().equals(robotID)) {
                stack.push("T");
                return;
            }
        }
        stack.push("F");
    }

    /**
     * Gets a message from the robot specified by what's popped off the stack.
     * <p>
     * precon the robot has a message from that robot in it's inbox
     * postcon the message is no longer in the inbox
     * <p>
     * pop:     the id of the robot to get the message of
     * push:    the message itself
     */
    public void recv() {
        String robotID;
        int id = typeToInt(stack.pop());

        robotID = GameMaster.gangs[GameMaster.findGang(robot.getRobotID())].getRobots()[id].getRobotID();

        for (Pair<String, String> msg : robot.getInbox()) {
            if (msg.getKey().equals(robotID)) {
                stack.push(msg.getValue());
                robot.getInbox().remove(msg);
                return;
            }
        }
    }

    /**
     * A helper function that gets an integer representation of the robots type for looking up in the gang array
     *
     * @param type string type representing the type of robot you have
     * @return an int that is the corresponding array index in the gangs array of robots for that type of robot
     */
    private int typeToInt(String type) {
        switch (type) {
            case "SCOUT":
                return 0;
            case "SNIPER":
                return 1;
            case "TANK":
                return 2;
            default:
                return -1;
        }
    }

    private enum LoopType {
        COUNTED, GUARDED
    }

    private class LoopStruct {
        int start;
        int increment;
        int end;
        int bodyIndex;
        LoopType type;


        LoopStruct() {
            start = -1;
            end = -1;
            increment = 0;
        }


    }

    private class StackErrorException extends Exception {
        StackErrorException(String message) {
            super(message);
        }
    }

}
