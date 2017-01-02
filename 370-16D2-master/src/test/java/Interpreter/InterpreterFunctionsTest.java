package Interpreter;

import Model.Gameboard;
import Model.Robot;
import Model.TestingSuite;
import org.junit.Test;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InterpreterFunctionsTest {
    //NOTE FROM TESTER: Robot functions are pivotal for the testing of InterpreterFunctions
    //                  Please Ensure Robot tests are run and pass before testing Interpreter
    //                  Functions


    private Robot testRobots[];
    private final InterpreterFunctions functions;

    public InterpreterFunctionsTest() throws Exception {
        new Gameboard(3);
        new TestingSuite();
        Interpreter interpreter = new Interpreter();
        this.functions = new InterpreterFunctions(interpreter);
    }

    @Test
    public void basicInterpreterFunctionTest() {

        this.testRobots = new Robot[1];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }
        //for reading printed out pop
        String c;
        PipedInputStream pipeIn;
        Scanner sc;
        PipedOutputStream pipeOut = new PipedOutputStream();
        try{
            pipeIn = new PipedInputStream(pipeOut);
            System.setOut(new PrintStream(pipeOut));
            sc = new Scanner(pipeIn);
        }catch(Exception e){
            sc = null;
            System.err.println("Error Unable to create pipeline: " + e);
        }


        //testing basic fucntion
        this.functions.setRobot(this.testRobots[0]);                        //setRobot

        try{
            this.functions.health();                                        //health
            this.functions.popAndPrint();                                   //popAndPrint
            c = sc != null ? sc.nextLine() : null;
            assert c.equals("1");
        }catch(Exception e){
            System.err.println("Error in health function: " + e);
        }

        try{
            this.functions.healthLeft();                                    //healthLeft
            this.functions.popAndPrint();
            c = sc != null ? sc.nextLine() : null;
            assert c.equals("1");
        }catch(Exception e){
            System.err.println("Error in popAndPrint function: " + e);
        }

        try{
            this.functions.push("+");                                   //push
            this.functions.push("1");
            this.functions.push("1");

            this.functions.arithmetic();
            this.functions.popAndPrint();
            c = sc != null ? sc.nextLine() : null;
            assert c.equals("2");
        }catch(Exception e){
            System.err.println("Error in arithmetic function: " + e);
        }



    }



    @Test
    public void setRobot() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("");
        assert this.functions.pop().equals("");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.push("");
        assert this.functions.pop().equals("");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.push("");
        assert this.functions.pop().equals("");
    }

    @Test
    public void popAndPrint() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }
        //for reading printed out pop
        String c;
        PipedInputStream pipeIn;
        Scanner sc;
        PipedOutputStream pipeOut = new PipedOutputStream();
        try{
            pipeIn = new PipedInputStream(pipeOut);
            System.setOut(new PrintStream(pipeOut));
            sc = new Scanner(pipeIn);
        }catch(Exception e){
            sc = null;
            System.err.println("Error Unable to create pipeline: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        try{
            this.functions.push("1");
            this.functions.popAndPrint();
            c = sc != null ? sc.nextLine() : null;
            assert c.equals("1");
        }catch(Exception e){
            System.err.println("Error in popAndPrint function: " + e);
        }

        this.functions.setRobot(this.testRobots[1]);
        try{
            this.functions.push("2");
            this.functions.popAndPrint();
            c = sc != null ? sc.nextLine() : null;
            assert c.equals("2");
        }catch(Exception e){
            System.err.println("Error in popAndPrint function: " + e);
        }

        this.functions.setRobot(this.testRobots[2]);
        try{
            this.functions.push("3");
            this.functions.popAndPrint();
            c = sc != null ? sc.nextLine() : null;
            assert c.equals("3");
        }catch(Exception e){
            System.err.println("Error in popAndPrint function: " + e);
        }
    }

    @Test
    public void drop() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("1");
        this.functions.push("2");
        this.functions.drop();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.push("3");
        this.functions.push("4");
        this.functions.drop();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.push("5");
        this.functions.push("6");
        this.functions.drop();
        assert this.functions.pop().equals("5");

        //droping an empty stack
        try {
            this.functions.drop();
            this.functions.drop();
            this.functions.drop();
            assert this.functions.pop().equals("3");
            assert false;
        }catch(Exception e){
            assert true;
        }
    }

    @Test
    public void dup() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("1");
        this.functions.dup();
        this.functions.drop();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.push("2");
        this.functions.dup();
        this.functions.drop();
        assert this.functions.pop().equals("2");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.push("3");
        this.functions.dup();
        this.functions.drop();
        assert this.functions.pop().equals("3");


        //duping an empty stack
        try {
            this.functions.drop();
            this.functions.drop();
            this.functions.drop();
            this.functions.dup();
            assert this.functions.pop().equals("1");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

    @Test
    public void swap() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("1");
        this.functions.push("2");
        this.functions.swap();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.push("3");
        this.functions.push("4");
        this.functions.swap();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.push("5");
        this.functions.push("6");
        this.functions.swap();
        assert this.functions.pop().equals("5");

        //swaping an near empty stack
        try {
            this.functions.drop();
            this.functions.drop();
            this.functions.swap();
            assert this.functions.pop().equals("2");
            assert false;
        }catch(Exception e){
            assert true;
        }

        //swaping an empty stack
        try {
            this.functions.drop();
            this.functions.swap();
            assert this.functions.pop().equals("2");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

    @Test
    public void rot() throws Exception {

        this.testRobots = new Robot[1];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("3");
        this.functions.push("2");
        this.functions.push("1");

        this.functions.rot();
        assert this.functions.pop().equals("3");
        assert this.functions.pop().equals("1");
        assert this.functions.pop().equals("2");

        this.functions.push("2");
        this.functions.push("1");

        //rotating an near empty stack
        try {
            this.functions.rot();
            assert this.functions.pop().equals("2");
            assert this.functions.pop().equals("1");
            assert false;
        }catch(Exception e){
            assert true;
        }

        //rotating an empty stack
        try {
            this.functions.drop();
            assert this.functions.pop().equals("2");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

    @Test
    public void arithmetic() throws Exception {
        //CURRENTLY NOT WORKING DUE TO ARITHMETIC NOT WORKINGS
        this.testRobots = new Robot[1];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("1");
        this.functions.push("1");
        this.functions.push("+");

        this.functions.arithmetic();
        assert this.functions.pop().equals("2");

        this.functions.push("1");
        this.functions.push("1");
        this.functions.push("-");

        this.functions.arithmetic();
        assert this.functions.pop().equals("0");

        this.functions.push("2");
        this.functions.push("2");
        this.functions.push("*");

        this.functions.arithmetic();
        assert this.functions.pop().equals("4");

        this.functions.push("2");
        this.functions.push("3");
        this.functions.push("/mod");

        this.functions.arithmetic();
        assert this.functions.pop().equals("1");
        assert this.functions.pop().equals("1");

        //rotating an near empty stack
        try {
            this.functions.push("+");
            this.functions.arithmetic();
            assert this.functions.pop().equals("");
            assert false;
        }catch(Exception e){
            assert true;
        }

        //rotating an empty stack
        try {
            this.functions.arithmetic();
            assert this.functions.pop().equals("");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

    @Test
    public void comparison() throws Exception {
//CURRENTLY NOT WORKING DUE TO ARITHMETIC NOT WORKINGS
        this.testRobots = new Robot[1];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("1");
        this.functions.push("2");
        this.functions.push("<");

        this.functions.comparison();
        assert this.functions.pop().equals("T");

        this.functions.push("2");
        this.functions.push("1");
        this.functions.push("<");

        this.functions.comparison();
        assert this.functions.pop().equals("F");

        this.functions.push("1");
        this.functions.push("1");
        this.functions.push("<");

        this.functions.comparison();
        assert this.functions.pop().equals("F");



        this.functions.push("1");
        this.functions.push("2");
        this.functions.push(">");

        this.functions.comparison();
        assert this.functions.pop().equals("F");

        this.functions.push("2");
        this.functions.push("1");
        this.functions.push(">");

        this.functions.comparison();
        assert this.functions.pop().equals("T");

        this.functions.push("1");
        this.functions.push("1");
        this.functions.push(">");

        this.functions.comparison();
        assert this.functions.pop().equals("F");



        this.functions.push("1");
        this.functions.push("2");
        this.functions.push("<=");

        this.functions.comparison();
        assert this.functions.pop().equals("F");

        this.functions.push("2");
        this.functions.push("1");
        this.functions.push("<=");

        this.functions.comparison();
        assert this.functions.pop().equals("T");

        this.functions.push("2");
        this.functions.push("2");
        this.functions.push("<=");

        this.functions.comparison();
        assert this.functions.pop().equals("T");



        this.functions.push("1");
        this.functions.push("2");
        this.functions.push("=>");

        this.functions.comparison();
        assert this.functions.pop().equals("T");

        this.functions.push("2");
        this.functions.push("1");
        this.functions.push("=>");

        this.functions.comparison();
        assert this.functions.pop().equals("F");

        this.functions.push("2");
        this.functions.push("2");
        this.functions.push("=>");

        this.functions.comparison();
        assert this.functions.pop().equals("T");



        this.functions.push("2");
        this.functions.push("1");
        this.functions.push("=");

        this.functions.comparison();
        assert this.functions.pop().equals("F");

        this.functions.push("2");
        this.functions.push("2");
        this.functions.push("=");

        this.functions.comparison();
        assert this.functions.pop().equals("T");



        this.functions.push("2");
        this.functions.push("1");
        this.functions.push("<>");

        this.functions.comparison();
        assert this.functions.pop().equals("T");

        this.functions.push("2");
        this.functions.push("2");
        this.functions.push("<>");

        this.functions.comparison();
        assert this.functions.pop().equals("F");

        //arithmetic an near empty stack
        try {
            this.functions.push("+");
            this.functions.comparison();
            assert this.functions.pop().equals("");
            assert false;
        }catch(Exception e){
            assert true;
        }

        //arithmetic an empty stack
        try {
            this.functions.comparison();
            assert this.functions.pop().equals("");
            assert false;
        }catch(Exception e){
            assert true;
        }
    }

    @Test
    public void leave() throws Exception {

        this.testRobots = new Robot[1];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.push("3");
        this.functions.push("2");
        this.functions.push("1");

        this.functions.rot();
        assert this.functions.pop().equals("3");
        assert this.functions.pop().equals("1");
        assert this.functions.pop().equals("2");

        this.functions.push("2");
        this.functions.push("1");

        //rotating an near empty stack
        try {
            this.functions.rot();
            assert this.functions.pop().equals("2");
            assert this.functions.pop().equals("1");
            assert false;
        }catch(Exception e){
            assert true;
        }

        //rotating an empty stack
        try {
            this.functions.drop();
            assert this.functions.pop().equals("2");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

    @Test
    public void health() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.health();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.health();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.health();
        assert this.functions.pop().equals("2");

        this.testRobots = new Robot[1];
        //health on an empty robot
        try {
            this.functions.setRobot(this.testRobots[0]);
            this.functions.health();    //should fail by here
            assert this.functions.pop().equals("1");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

    @Test
    public void healthLeft() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.healthLeft();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.healthLeft();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.healthLeft() ;
        assert this.functions.pop().equals("2");

        this.testRobots = new Robot[1];
        //healthLeft on an empty robot
        try {
            this.functions.setRobot(this.testRobots[0]);
            this.functions.healthLeft();    //should fail by here
            assert this.functions.pop().equals("1");
            assert false;
        }catch(Exception e){
            assert true;
        }
    }

    @Test
    public void moves() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.moves();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.moves();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.moves();
        assert this.functions.pop().equals("2");

        this.testRobots = new Robot[1];
        //health on an empty robot
        try {
            this.functions.setRobot(this.testRobots[0]);
            this.functions.moves();    //should fail by here
            assert this.functions.pop().equals("3");
            assert false;
        }catch(Exception e){
            assert true;
        }
    }

    @Test
    public void movesLeft() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.movesLeft();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.movesLeft();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.movesLeft();
        assert this.functions.pop().equals("2");

        this.testRobots = new Robot[1];
        //healthLeft on an empty robot
        try {
            this.functions.setRobot(this.testRobots[0]);
            this.functions.movesLeft();    //should fail by here
            assert this.functions.pop().equals("3");
            assert false;
        }catch(Exception e){
            assert true;
        }
    }

    @Test
    public void attack() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.attack();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.attack();
        assert this.functions.pop().equals("3");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.attack();
        assert this.functions.pop().equals("2");

        this.testRobots = new Robot[1];
        //range on an empty robot
        try {
            this.functions.setRobot(this.testRobots[0]);
            this.functions.attack();   //should fail by here
            assert this.functions.pop().equals("3");
            assert false;
        }catch(Exception e){
            assert true;
        }
    }

    @Test
    public void range() throws Exception {

        this.testRobots = new Robot[3];
        try{
            this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
            this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
            this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        }catch(Exception e){
            System.err.println("Error in robot function: " + e);
        }

        this.functions.setRobot(this.testRobots[0]);
        this.functions.range();
        assert this.functions.pop().equals("2");

        this.functions.setRobot(this.testRobots[1]);
        this.functions.range();
        assert this.functions.pop().equals("1");

        this.functions.setRobot(this.testRobots[2]);
        this.functions.range();
        assert this.functions.pop().equals("3");

        this.testRobots = new Robot[1];
        //range on an empty robot
        try {
            this.functions.setRobot(this.testRobots[0]);
            this.functions.range();    //should fail by here
            assert this.functions.pop().equals("3");
            assert false;
        }catch(Exception e){
            assert true;
        }

    }

}