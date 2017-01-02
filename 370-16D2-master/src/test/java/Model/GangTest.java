package Model;

import org.junit.Test;

import static Model.Colour.BLUE;
import static Model.Colour.GREEN;
import static Model.Colour.ORANGE;
import static Model.Colour.PURPLE;
import static Model.Colour.RED;
import static Model.Colour.YELLOW;

/**
 * Created by Sam on 2016-11-25.
 *
 * Tests the Gang class
 */
@SuppressWarnings("ConstantConditions")
public class GangTest {

    /**
     * Robots for testing gang
     */
    private Robot[] testRobots;

    /**
     * Initializes
     */
    public GangTest() {
        new TestingSuite();
        new Gameboard( 7);
    }

    @Test
    public void getID() throws Exception {
        Gang testGang;


        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        testGang = new Gang(this.testRobots, 1, true);
        assert testGang.getID() == 1;

        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        testGang = new Gang(this.testRobots, 2, true);
        assert testGang.getID() == 2;

        //testing for AI false
        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        testGang = new Gang(this.testRobots, 1, false);
        assert testGang.getID() == 1;

        //testing on empty robot
        this.testRobots = new Robot[3];
        try {
            new Gang(this.testRobots, 2, true);
        } catch (Exception e) {
            assert true;
        }

        //testing large ID
        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        try {
            new Gang(this.testRobots, 20, true);
        } catch (Exception e) {
            assert true;
        }

        //testing negitive ID
        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        try {
            new Gang(this.testRobots, -2, true);
        } catch (Exception e) {
            assert true;
        }

        //testing 0 ID
        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        try {
            new Gang(this.testRobots, 0, true);
        } catch (Exception e) {
            assert true;
        }

    }

    /**
     * setTestRobots is a helper function that sets up all the testRobots
     *
     */
    private void setTestRobots() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 4);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("sniperJson"), 4);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("tankJson"), 4);
    }

    @Test
    public void getRobots() throws Exception {
        Gang testGang;

        try{
            setTestRobots();
        }catch(Exception e){
            assert (true);
        }
        testGang = new Gang(this.testRobots, 1, true);
        assert((testGang.getRobots()[0] == this.testRobots[0]));
        assert((testGang.getRobots()[1] == this.testRobots[1]));
        assert((testGang.getRobots()[2] == this.testRobots[2]));

        //getRobots on empty robots
        this.testRobots = null;
        try{
            testGang = new Gang(this.testRobots, 1, true);
            assert((testGang.getRobots()[0] == this.testRobots[0]));
            assert((testGang.getRobots()[1] == this.testRobots[1]));
            assert((testGang.getRobots()[2] == this.testRobots[2]));
            assert false;
        }catch(Exception e){
            assert (true);
        }


        //getRobots on a semi-full robot team
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 4);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("sniperJson"), 4);
        this.testRobots[2] = null;
        try{
            testGang = new Gang(this.testRobots, 2, true);
            assert((testGang.getRobots()[0] == this.testRobots[0]));
            assert((testGang.getRobots()[1] == this.testRobots[1]));
            assert((testGang.getRobots()[2] == this.testRobots[2]));
        }catch(Exception e){
            System.err.println("Failed on almost empty gang");
        }




    }

    @Test
    public void getColour() throws Exception {
        Gang testGang;

        try{
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        testGang = new Gang(this.testRobots, 0, true);
        assert testGang.getColour() == RED;

        try {
            this.setTestRobots();
        } catch (Exception e){
            assert true;
        }
        testGang = new Gang(this.testRobots, 4, true);
        assert testGang.getColour() == BLUE;

        try {
            this.setTestRobots();
        } catch (Exception e){
            assert true;
        }
        testGang = new Gang(this.testRobots, 3, true);
        assert testGang.getColour() == GREEN;

        try {
            this.setTestRobots();
        } catch (Exception e){
            assert true;
        }
        testGang = new Gang(this.testRobots, 1, true);
        assert testGang.getColour() == ORANGE;

        try {
            this.setTestRobots();
        } catch (Exception e){
            assert true;
        }
        testGang = new Gang(this.testRobots, 5, true);
        assert testGang.getColour() == PURPLE;

        try {
            this.setTestRobots();
        } catch (Exception e){
            assert true;
        }
        testGang = new Gang(this.testRobots, 2, true);
        assert testGang.getColour() == YELLOW;

        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        testGang = new Gang(this.testRobots, 3, true);
        assert testGang.getColour() == GREEN;

        try {
            this.setTestRobots();
        } catch (Exception e) {
            assert true;
        }
        try {
            new Gang(this.testRobots, 7, true);
        } catch (Exception e){
            assert e instanceof IllegalArgumentException;
        }
    }

}