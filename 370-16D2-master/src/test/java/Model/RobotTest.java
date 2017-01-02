package Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static Model.Robot.validJSON;

/**
 * Created by mikeg on 2016-11-14.
 *
 * Tests the Robot class
 */
public class RobotTest {

    private Robot testRobots[];

    public RobotTest() throws Exception {
        new Gameboard( 3);
        new TestingSuite();
    }

    @Test
    public void testIsValidJSON() throws Exception {
        Gson gson = new Gson();
        Map<String, Object> parsedJSON;
        parsedJSON = gson.fromJson(TestingSuite.getJSON("json"), new TypeToken<Map<String, Object>>() {
        }.getType());
        // Test if valid JSON passes
        assert validJSON(parsedJSON);

        String[] badJSONS = new String[13];
        badJSONS[0] = TestingSuite.getJSON("badJSON2");
        badJSONS[1] = TestingSuite.getJSON("badJSON3");
        badJSONS[2] = TestingSuite.getJSON("badJSON4");
        badJSONS[3] = TestingSuite.getJSON("badJSON5");
        badJSONS[4] = TestingSuite.getJSON("badJSON6");
        badJSONS[5] = TestingSuite.getJSON("badJSON7");
        badJSONS[6] = TestingSuite.getJSON("badJSON8");
        badJSONS[7] = TestingSuite.getJSON("badJSON9");
        badJSONS[8] = TestingSuite.getJSON("badJSON10");
        badJSONS[9] = TestingSuite.getJSON("badJSON11");
        badJSONS[10] = TestingSuite.getJSON("badJSON12");
        badJSONS[11] = TestingSuite.getJSON("badJSON13");
        badJSONS[12] = TestingSuite.getJSON("badJSON14");

        // Test if key is missing.
        for (String badJSON : badJSONS) {
            parsedJSON = gson.fromJson(badJSON, new TypeToken<Map<String, Object>>() {
            }.getType());
            assert !validJSON(parsedJSON);
        }
    }

    @Test
    public void getGangID() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].getGangID() == 1;
        assert this.testRobots[1].getGangID() == 1;
        assert this.testRobots[2].getGangID() == 1;

        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 7);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 922);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 48);

        assert this.testRobots[0].getGangID() == 7;
        assert this.testRobots[1].getGangID() == 922;
        assert this.testRobots[2].getGangID() == 48;

        //testing getGangID on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].getGangID() == 0;
            assert false;
        } catch(Exception e){
            assert true;
        }



    }

    @Test
    public void getTeamID() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].getTeamID().equals("testTeam");
        assert this.testRobots[1].getTeamID().equals("testTeam");
        assert this.testRobots[2].getTeamID().equals("testTeam");

        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("json"), 2);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("json"), 2);

        assert this.testRobots[0].getTeamID().equals("testTeam");
        assert this.testRobots[1].getTeamID().equals("A1");
        assert this.testRobots[2].getTeamID().equals("A1");

        //testing getTeamID on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].getTeamID().equals("testTeam");
            assert false;
        } catch(Exception e){
            assert true;
        }

    }

    @Test
    public void getRobotID() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].getRobotID().equals("test scout" + "testTeam" + 1);
        assert this.testRobots[1].getRobotID().equals("test tank" + "testTeam" + 1);
        assert this.testRobots[2].getRobotID().equals("test sniper" + "testTeam" + 1);

        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("json"), 2);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("json"), 2);

        assert this.testRobots[0].getRobotID().equals("test scout" + "testTeam" + 1);
        assert this.testRobots[1].getRobotID().equals("a sniper" + "A1" + 2);
        assert this.testRobots[2].getRobotID().equals("a sniper" + "A1" + 2);

        //testing getRobotID on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].getRobotID().equals("test scout" + "testTeam" + 1);
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void getHealth() throws Exception {


        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].getHealth() == 1;
        assert this.testRobots[1].getHealth() == 3;
        assert this.testRobots[2].getHealth() == 2;

        //testing getHealth on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].getHealth() == 1;
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void setHealth() throws Exception {


        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        this.testRobots[0].setHealth(1);
        assert this.testRobots[0].getHealth() == 0;
        this.testRobots[1].setHealth(3);
        assert this.testRobots[1].getHealth() == 0;
        this.testRobots[2].setHealth(2);
        assert this.testRobots[2].getHealth() == 0;

        //testing setHealth on empty robot
        this.testRobots = new Robot[1];
        try{
            this.testRobots[0].setHealth(1);
            assert this.testRobots[0].getHealth() == 0;
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void getType() throws Exception {


        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].getType().equals("Scout");
        assert this.testRobots[1].getType().equals("Tank");
        assert this.testRobots[2].getType().equals("Sniper");

        //testing getType on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].getType().equals("Scout");
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void getAttack() throws Exception {


        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].getAttack() == 1;
        assert this.testRobots[1].getAttack() == 3;
        assert this.testRobots[2].getAttack() == 2;

        //testing getAttack on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].getAttack() == 1;
            assert false;
        } catch(Exception e){
            assert true;
        }


    }

    @Test
    public void isAlive() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].isAlive();
        assert this.testRobots[1].isAlive();
        assert this.testRobots[2].isAlive();


        //testing getAttack on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].isAlive();
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    //test for getMsg and recieveMsg in one as they are closely connected
    public void msgSystemTest() throws Exception {

        //AS OF 2016-11-28 Inbox system not implemented
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        //testing for sendMsg
        this.testRobots[0].sendMsg("test1");
        this.testRobots[0].sendMsg("test2");
        this.testRobots[0].sendMsg("test3");

        this.testRobots[1].sendMsg("test1");
        this.testRobots[1].sendMsg("test2");
        this.testRobots[1].sendMsg("test3");

        this.testRobots[2].sendMsg("test1");
        this.testRobots[2].sendMsg("test2");
        this.testRobots[2].sendMsg("test3");


        assert this.testRobots[0].receiveMsg().getValue().equals("test3");
        assert this.testRobots[0].receiveMsg().getValue().equals("test2");
        assert this.testRobots[0].receiveMsg().getValue().equals("test1");

        assert this.testRobots[1].receiveMsg().getValue().equals("test3");
        assert this.testRobots[1].receiveMsg().getValue().equals("test2");
        assert this.testRobots[1].receiveMsg().getValue().equals("test1");

        assert this.testRobots[2].receiveMsg().getValue().equals("test3");
        assert this.testRobots[2].receiveMsg().getValue().equals("test2");
        assert this.testRobots[2].receiveMsg().getValue().equals("test1");


        //testing getMsg and recieveMsg on empty robot
        this.testRobots = new Robot[1];
        try{
            this.testRobots[0].sendMsg("nullTest");
            assert this.testRobots[0].receiveMsgTest().equals("test1");
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void canShoot() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        assert this.testRobots[0].canShoot();
        assert this.testRobots[1].canShoot();
        assert this.testRobots[2].canShoot();


        //testing getAttack on empty robot
        this.testRobots = new Robot[1];
        try{
            assert this.testRobots[0].canShoot();
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void move() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);


        //dependent on getMovesRemaining
        int moveInit = this.testRobots[0].getMovesRemaining();
        this.testRobots[0].move();
        assert this.testRobots[0].getMovesRemaining() < moveInit;
        assert this.testRobots[0].getMovesRemaining() == 2;

        moveInit = this.testRobots[1].getMovesRemaining();
        this.testRobots[1].move();
        assert this.testRobots[1].getMovesRemaining() < moveInit;
        assert this.testRobots[1].getMovesRemaining() == 0;

        moveInit = this.testRobots[2].getMovesRemaining();
        this.testRobots[2].move();
        assert this.testRobots[2].getMovesRemaining() < moveInit;
        assert this.testRobots[2].getMovesRemaining() == 1;

        // an attempt to move with no movement left
        this.testRobots[1].move();
        assert this.testRobots[1].getMovesRemaining() == 0;

        // to test trying to move to a null location.
        // depends on turn() functionality
        this.testRobots[0].turn();
        this.testRobots[0].turn();
        this.testRobots[0].turn();
        this.testRobots[0].move();
        try {
            Assert.assertEquals(this.testRobots[0].getMovesRemaining(), 1);
            assert false;
        }catch(Exception e){
            assert true;
        }

        //testing move on empty robot
        this.testRobots = new Robot[1];
        try{
            this.testRobots[0].move();
            assert this.testRobots[0].getMovesRemaining() == 1;
            assert false;
        } catch(Exception e){
            assert true;
        }
    }

    @Test
    public void turn() throws Exception {

        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        int orginalDirection = this.testRobots[0].getDirection();
        this.testRobots[0].turn();
        assert orginalDirection != this.testRobots[0].getDirection();
        assert this.testRobots[0].getDirection() == 2;
        orginalDirection = this.testRobots[0].getDirection();
        this.testRobots[0].turn();
        assert orginalDirection != this.testRobots[0].getDirection();
        assert this.testRobots[0].getDirection() == 3;


        orginalDirection = this.testRobots[1].getDirection();
        this.testRobots[1].turn();
        assert orginalDirection != this.testRobots[1].getDirection();
        assert this.testRobots[1].getDirection() == 2;
        orginalDirection = this.testRobots[1].getDirection();
        this.testRobots[1].turn();
        assert orginalDirection != this.testRobots[1].getDirection();
        assert this.testRobots[1].getDirection() == 3;


        orginalDirection = this.testRobots[2].getDirection();
        this.testRobots[2].turn();
        assert orginalDirection != this.testRobots[2].getDirection();
        assert this.testRobots[2].getDirection() == 2;
        orginalDirection = this.testRobots[2].getDirection();
        this.testRobots[2].turn();
        assert orginalDirection != this.testRobots[2].getDirection();
        assert this.testRobots[2].getDirection() == 3;


        //testing turn empty robot
        this.testRobots = new Robot[1];
        try{
            this.testRobots[0].turn();
            assert this.testRobots[0].getDirection() != this.testRobots[0].getDirection();
            assert false;
        } catch(Exception e){
            assert true;
        }
    }
}