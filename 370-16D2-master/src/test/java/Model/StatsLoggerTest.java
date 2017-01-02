package Model;

import org.junit.Test;

/**
 * Created by Sam on 2016-11-27.
 *
 * Tests the StatsLogger class.
 */
@SuppressWarnings("ConstantConditions")
public class StatsLoggerTest {

    private StatsLogger testLogger;
    private Robot testRobots[];

    public StatsLoggerTest() {
        new TestingSuite();
        new Gameboard(3);
    }

    @Test
    public void updateMatches() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);

        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateMatches(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "matches").equals("1");

        StatsLogger.updateMatches(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "matches").equals("1");

        StatsLogger.updateMatches(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "matches").equals("1");

        StatsLogger.updateMatches(this.testRobots[2].getRobotID());
        StatsLogger.updateMatches(this.testRobots[2].getRobotID());
        StatsLogger.updateMatches(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "matches").equals("4");

    }

    @Test
    public void updateWins() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateWins(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "wins").equals("1");

        StatsLogger.updateWins(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "wins").equals("1");

        StatsLogger.updateWins(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "wins").equals("1");

        StatsLogger.updateWins(this.testRobots[2].getRobotID());
        StatsLogger.updateWins(this.testRobots[2].getRobotID());
        StatsLogger.updateWins(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "wins").equals("4");
    }

    @Test
    public void updateLosses() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateLosses(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "losses").equals("1");

        StatsLogger.updateLosses(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "losses").equals("1");

        StatsLogger.updateLosses(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "losses").equals("1");

        StatsLogger.updateLosses(this.testRobots[2].getRobotID());
        StatsLogger.updateLosses(this.testRobots[2].getRobotID());
        StatsLogger.updateLosses(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "losses").equals("4");
    }

    @Test
    public void updateKilled() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateKilled(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "killed").equals("1");

        StatsLogger.updateKilled(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "killed").equals("1");

        StatsLogger.updateKilled(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "killed").equals("1");

        StatsLogger.updateKilled(this.testRobots[2].getRobotID());
        StatsLogger.updateKilled(this.testRobots[2].getRobotID());
        StatsLogger.updateKilled(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "killed").equals("4");
    }

    @Test
    public void updateAbsorbed() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateAbsorbed(this.testRobots[0].getRobotID(), 1);
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "absorbed").equals("1");

        StatsLogger.updateAbsorbed(this.testRobots[1].getRobotID(), 1);
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "absorbed").equals("1");

        StatsLogger.updateAbsorbed(this.testRobots[2].getRobotID(), 1);
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "absorbed").equals("1");

        StatsLogger.updateAbsorbed(this.testRobots[0].getRobotID(), 10);
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "absorbed").equals("11");
        StatsLogger.updateAbsorbed(this.testRobots[1].getRobotID(), 20);
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "absorbed").equals("21");
        StatsLogger.updateAbsorbed(this.testRobots[2].getRobotID(), 30);
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "absorbed").equals("31");
    }

    @Test
    public void updateLived() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateLived(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "lived").equals("1");

        StatsLogger.updateLived(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "lived").equals("1");

        StatsLogger.updateLived(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "lived").equals("1");

        StatsLogger.updateLived(this.testRobots[2].getRobotID());
        StatsLogger.updateLived(this.testRobots[2].getRobotID());
        StatsLogger.updateLived(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "lived").equals("4");
    }

    @Test
    public void updateDied() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateDied(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "died").equals("1");

        StatsLogger.updateDied(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "died").equals("1");

        StatsLogger.updateDied(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "died").equals("1");

        StatsLogger.updateDied(this.testRobots[2].getRobotID());
        StatsLogger.updateDied(this.testRobots[2].getRobotID());
        StatsLogger.updateDied(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "died").equals("4");
    }

    @Test
    public void updateMoved() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateMoved(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "moved").equals("1");

        StatsLogger.updateMoved(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "moved").equals("1");

        StatsLogger.updateMoved(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "moved").equals("1");

        StatsLogger.updateMoved(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "moved").equals("11");
        StatsLogger.updateMoved(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "moved").equals("21");
        StatsLogger.updateMoved(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "moved").equals("31");
    }

    @Test
    public void updateExecutions() throws Exception {
        this.testRobots = new Robot[3];
        this.testRobots[0] = new Robot(TestingSuite.getJSON("scoutJson"), 1);
        this.testRobots[1] = new Robot(TestingSuite.getJSON("tankJson"), 1);
        this.testRobots[2] = new Robot(TestingSuite.getJSON("sniperJson"), 1);
        this.testLogger = new StatsLogger(this.testRobots);


        StatsLogger.updateExecutions(this.testRobots[0].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[0].getRobotID(), "executions").equals("1");

        StatsLogger.updateExecutions(this.testRobots[1].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[1].getRobotID(), "executions").equals("1");

        StatsLogger.updateExecutions(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "executions").equals("1");

        StatsLogger.updateExecutions(this.testRobots[2].getRobotID());
        StatsLogger.updateExecutions(this.testRobots[2].getRobotID());
        StatsLogger.updateExecutions(this.testRobots[2].getRobotID());
        assert StatsLogger.getJSONAspect(this.testRobots[2].getRobotID(), "executions").equals("4");
    }

}