package Interpreter;

import Model.Gameboard;
import Model.Robot;
import org.junit.Test;


/**
 * Created by kristof on 25/11/16.
 *
 * Tests the interpreter class
 */
public class InterpreterTest {


    public InterpreterTest() {
        new Gameboard(4);
    }

    @Test
    public void play() throws Exception {
        Robot testRobotLoop = null;
        Robot testRobotConditional = null;
        Robot testRobotCode = null;
        try {
            String loopTest = "{ \"team\" : \"testTeam\"\n" +
                    ", \"class\" : \"Tank\"\n" +
                    ", \"name\" : \"test tank\"\n" +
                    ", \"matches\" : 0\n" +
                    ", \"wins\" : 0\n" +
                    ", \"losses\" : 0\n" +
                    ", \"executions\" : 0\n" +
                    ", \"lived\" : 0\n" +
                    ", \"died\" : 0\n" +
                    ", \"absorbed\" : 0\n" +
                    ", \"killed\" : 0\n" +
                    ", \"moved\" : 0\n" +
                    ", \"code\" : [ \"variable lastShot ; \"\n" +
                    ", \"0 lastShot ! \"\n" +
                    ", \": empty? false .\\\"EMPTY\\\" = ; \"\n" +
                    ", \": play ( -- ) \"\n" +
                    ", \" 0 5 0 do I + loop 10 = \"\n" +     //test the counted loop, should push a boolean "T"
                    ", \" 0 begin 1 + dup 10 < until 10 = \"\n" +
                    ", \" ; \"\n" +
                    "] }";
            testRobotLoop = new Robot(loopTest, 1);
            String conditionTest = "{ \"team\" : \"testTeam\"\n" +
                    ", \"class\" : \"Tank\"\n" +
                    ", \"name\" : \"test tank\"\n" +
                    ", \"matches\" : 0\n" +
                    ", \"wins\" : 0\n" +
                    ", \"losses\" : 0\n" +
                    ", \"executions\" : 0\n" +
                    ", \"lived\" : 0\n" +
                    ", \"died\" : 0\n" +
                    ", \"absorbed\" : 0\n" +
                    ", \"killed\" : 0\n" +
                    ", \"moved\" : 0\n" +
                    ", \"code\" : [ \"variable lastShot ; \"\n" +
                    ", \"0 lastShot ! \"\n" +
                    ", \": empty? false .\\\"EMPTY\\\" = ; \"\n" +
                    ", \": play ( -- ) \"\n" +
                    ", \" true if true else false then \"\n" +
                    ", \" false if false else true then \"\n" +
                    ", \" ; \"\n" +
                    "] }";
            testRobotConditional = new Robot(conditionTest, 1);
            String codeTest = "{ \"team\" : \"testTeam\"\n" +
                    ", \"class\" : \"Tank\"\n" +
                    ", \"name\" : \"test tank\"\n" +
                    ", \"matches\" : 0\n" +
                    ", \"wins\" : 0\n" +
                    ", \"losses\" : 0\n" +
                    ", \"executions\" : 0\n" +
                    ", \"lived\" : 0\n" +
                    ", \"died\" : 0\n" +
                    ", \"absorbed\" : 0\n" +
                    ", \"killed\" : 0\n" +
                    ", \"moved\" : 0\n" +
                    ", \"code\" : [ \"variable lastShot ; \"\n" +
                    ", \"0 lastShot ! \"\n" +
                    ", \": empty? false .\\\"EMPTY\\\" = ; \"\n" +
                    ", \": play ( -- ) \"\n" +
                    ", \" 1 dup drop 2 swap 3 rot > swap 1 = and \"\n" +
                    ", \" 2 2 + 1 - 4 * 5 /mod = \"\n" +
                    ", \" 1 2 < \"\n" +
                    ", \" 2 2 < invert \"\n" +
                    ", \" 2 2 <=  \"\n" +
                    ", \" 3 2 <= invert \"\n" +
                    ", \" .\\\"hi\\\" .\\\"hi\\\" = \"\n" +
                    ", \" .\\\"hi\\\" .\\\"him\\\" = invert \"\n" +
                    ", \" .\\\"hi\\\" .\\\"hi\\\" <> invert \"\n" +
                    ", \" .\\\"hi\\\" .\\\"him\\\" <> \"\n" +
                    ", \" 2 1 > \"\n" +
                    ", \" 2 2 > invert \"\n" +
                    ", \" 2 2 => \"\n" +
                    ", \" 3 2 => ( hello ) ; \"\n" +
                    "] }";
            testRobotCode = new Robot(codeTest, 1);
            String json = "{ \"team\" : \"testTeam\"\n" +
                    ", \"class\" : \"Tank\"\n" +
                    ", \"name\" : \"test tank\"\n" +
                    ", \"matches\" : 0\n" +
                    ", \"wins\" : 0\n" +
                    ", \"losses\" : 0\n" +
                    ", \"executions\" : 0\n" +
                    ", \"lived\" : 0\n" +
                    ", \"died\" : 0\n" +
                    ", \"absorbed\" : 0\n" +
                    ", \"killed\" : 0\n" +
                    ", \"moved\" : 0\n" +
                    ", \"code\" : [ \"variable lastShot ; \"\n" +
                    ", \"0 lastShot ! \"\n" +
                    ", \": empty? false .\\\"EMPTY\\\" = ; \"\n" +
                    ", \": play ( -- ) \"\n" +
                    ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                    ", \" true if .\\\"no one there\\\" \"\n" +
                    ", \" else dup lastShot ! \"\n" +
                    ", \" dup 1 drop drop leave \"\n" +
                    ", \" then 1 1 + dup 5 > \"\n" +
                    ", \" until empty? . . . . . . ; \"\n" +
                    "] }";

        }catch(Exception e){
            System.out.println("ERROR: Robot not properly created");
            assert false;
        }


        Interpreter interpreter = new Interpreter();

        //call loopTest
        try {
            interpreter.play(testRobotLoop);
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            assert false;
        }
        //check stack state and print error messages.
        if(!(interpreter.functions.stack.size() == 2)){
            System.out.println("Loop test failed: Final stack should have been size 2 but was size " + interpreter.functions.stack.size());
           assert false;
        }
        for(int i = 0; i<2; i++) {
            String item = interpreter.functions.stack.pop();
            if (!(item.equals("T"))) {
                System.out.println("Loop test failed: Line " + (2-(i+1)) + " should have contained T but instead contained " + item);
              assert false;
            }
        }


        //call conditionalTest
        try {
            interpreter.play(testRobotConditional);
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            assert false;
        }
        //check stack state and print error messages.
        if(!(interpreter.functions.stack.size() == 2)){
            System.out.println("Conditional test failed: final stack should have been size 2 but was size " + interpreter.functions.stack.size());
            assert false;
        }
        for(int i = 0; i<2; i++) {
            String item = interpreter.functions.stack.pop();
            if (!(item.equals("T"))) {
                System.out.println("Conditional test failed: Line " + (2-(i+1)) + " should have contained T but instead contained " + item);
                assert false;
            }
        }



        //call codeTest
        try {
            interpreter.play(testRobotCode);
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            assert false;
        }
        //check stack state and print error messages.
        if(!(interpreter.functions.stack.size() == 14)){
            System.out.println("Code test failed: final stack should have been size 15 but was size " + interpreter.functions.stack.size());
            //assert false;
        }
        for(int i = 0; i<14; i++) {
            String item = interpreter.functions.stack.pop();
            if (!(item.equals("T"))) {
                System.out.println("Code test failed: Line " + (14-(i+1)) + " should have contained T but instead contained " + item);
                assert false;
            }
        }
    }




}