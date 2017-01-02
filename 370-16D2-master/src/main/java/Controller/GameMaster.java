package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The GameMaster is the main controller for the system. It is responsible for managing and calling
 * the necessary views required by the system. Those views, in turn, call their own controller
 * classes. The GameMaster is also responsible for keeping track of whose turn it is and for
 * detecting when the game is over
 */
public class GameMaster {

    /**
     * An array of the gangs participating in the game
     */
    public static Gang[] gangs;
    /**
     * A queue of robots for determining turn order within a gang.
     */
    private static Queue<Queue<Robot>> turnQ;
    /**
     * An int representing the current turn (not round!)
     */
    private static int turnNumber;

    /**
     * The constructor for the GameMaster class
     * Assuming that the robotCollection in gangs is already sorted by speed as.
     *
     * @param createGangs an array of robot gangs
     * @throws NullPointerException expection if gangs is null
     * @throws Exception            if size is less than or equal to 0
     */
    public GameMaster(Gang[] createGangs) throws Exception {
        Gang temp;
        int smallestIndex;
        Queue<Robot> robotTurnQ;

        if (createGangs == null) {
            throw new NullPointerException("gangs given is null");
        }

        /* Gangs are not guaranteed to be sorted coming from the startup menu so we have to sort them */
        /* chose a basic swap algorithm since this only gets done once with a small list of gangs */
        for (int i = 0; i < createGangs.length; i++) {
            smallestIndex = i;

            for (int x = i; x < createGangs.length; x++) {
                /* The colour represented by the smaller ID goes first */
                if (createGangs[x].getID() < createGangs[smallestIndex].getID()) {
                    smallestIndex = x;
                }
            }

            temp = createGangs[i];
            createGangs[i] = createGangs[smallestIndex];
            createGangs[smallestIndex] = temp;
        }
        gangs = createGangs;
        /* adds all the robots to a giant queue that by iterating through would get the proper turn order */

        turnQ = new LinkedList<>();


        for (Gang gang2 : gangs) {
            robotTurnQ = new LinkedList<>();
            for (int x = 0; x < gang2.getRobots().length; x++) {
                robotTurnQ.add(gang2.getRobots()[x]);
            }
            turnQ.add(robotTurnQ);
        }


        if (turnQ.size() <= 0) {
            throw new Exception("Gangs contain no robots");
        }

        /* Create StatsLogger, size of array is 3 x # of gangs since each gang has 3 robots */
        Robot[] forLogger = new Robot[gangs.length * 3];
        int index = 0;
        for (Gang gang1 : gangs) {
            for (int x = 0; x < gang1.getRobots().length; x++) {
                forLogger[index] = gang1.getRobots()[x];
                index++;
            }
        }
        StatsLogger gameLogger = new StatsLogger(forLogger);

        /* update the matches for each robot since they are about to play a game */
        for (Robot aForLogger : forLogger) {
            StatsLogger.updateMatches(aForLogger.getRobotID());
        }

        /* update executions for AI robots */
        for (Gang gang : gangs) {
            if (gang.isAI()) {
                for (Robot g : gang.getRobots()) {
                    StatsLogger.updateExecutions(g.getRobotID());
                }
            }
        }

        turnNumber = 0;
    }

    /**
     * Determines which robot on which team is up next in the turn order
     *
     * @return The next robot to take a turn
     * @throws NullPointerException if turnQ is null
     */
    public static Robot getNextRobot() throws NullPointerException {

        if (turnQ == null) {
            throw new NullPointerException("turnQ is null!");
        }

        Queue<Robot> currGang;

        if (turnQ.peek().peek().getGangID() == 0) {
            /* has a full round been completed?  If so all robots should have full moves and be able to shoot again */
            if (turnNumber == 3) {
                turnNumber = 0;
                for (Gang gang : gangs) {
                    for (int x = 0; x < gang.getRobots().length; x++) {
                        gang.getRobots()[x].setCanShoot();
                        gang.getRobots()[x].setMovesRemaining();
                    }
                }
            } else {
                turnNumber += 1;
            }
        }

        /* the gang at the head is the robot who's turn is next */
        currGang = turnQ.remove();
        /* the next robot that gets to make a move*/
        Robot curTurnRbt = currGang.remove();

        /* checks if the team has robots to move this turn */
        if (currGang.size() < turnNumber) {
            currGang.add(curTurnRbt);
            turnQ.add(currGang);
        }
        /* if not add the robots back on and get the next robots turn */
        else {
            currGang.add(curTurnRbt);
            turnQ.add(currGang);
            curTurnRbt = getNextRobot();
        }

        return curTurnRbt;
    }

    /**
     * Removes dead robots from the turn order list by iterating through it checking each robot to see if it's health
     * equals 0 and not re-adding the robot if it is.  Also removes gangs if they no longer have any living robots
     * left
     * <p>
     * precon turnQ must not be null
     *
     * @throws NullPointerException expection if turnQ is null
     */
    public static void updateTurnList() throws NullPointerException {

        if (turnQ == null) {
            throw new NullPointerException("turnQ is null!");
        }

        Robot checkDead;
        Queue<Robot> checkGang;

        for (int i = 0; i < turnQ.size(); i++) {
            checkGang = turnQ.remove();
            for (int x = 0; x < checkGang.size(); x++) {
                checkDead = checkGang.remove();
                /* if the bot is not dead add it back */
                if (!(checkDead.getHealth() == 0)) {
                    checkGang.add(checkDead);
                }
                /* else the robot is dead and need to update it's death */
                else {
                    StatsLogger.updateDied(checkDead.getRobotID());
                }
            }
            /* if after checking all the gangs robots there are still some left add the gang back to the turnQ */
            if (!(checkGang.size() == 0)) {
                turnQ.add(checkGang);
            }
        }

    }

    /**
     * Searches through the array of gangs and returns the index of the gang that contains the robot with robotID.
     *
     * @param robotID a string which is the the ID of a robot who's gang we wish to find
     * @return an int that is the index or -1 if the gang cannot be found.
     */
    public static int findGang(String robotID) {
        int id = Character.getNumericValue(robotID.charAt(robotID.length() - 1));

        for (int i = 0; i < gangs.length; i++) {
            if (gangs[i].getID() == id) {
                return i;
            }
        }
        return -1;

    }

    /**
     * Determines when the game has ended by checking to see if the robots remaining all belong to the same team.
     * <p>
     * precon: turnQ cannot be null
     *
     * @return the result of the test to see if the game has ended: true if over, false otherwise
     * @throws NullPointerException if turnQ is null
     */
    public boolean gameOver() throws NullPointerException {

        if (turnQ == null) {
            throw new NullPointerException("turnQ is null!");
        }

        /* if there is only one gang left then the game is over! */
        if (turnQ.size() == 1) {
            /* update the wins and losses for the winners and losers of the game! */
            for (Gang gang : gangs) {
                if (turnQ.peek().peek().getGangID() == gang.getID()) {
                    for (int x = 0; x < gang.getRobots().length; x++) {
                        StatsLogger.updateWins(gang.getRobots()[x].getRobotID());
                        if (gang.getRobots()[x].getHealth() > 0) {
                            StatsLogger.updateLived(gang.getRobots()[x].getRobotID());
                        }
                    }
                } else {
                    for (int x = 0; x < gang.getRobots().length; x++) {
                        StatsLogger.updateLosses(gang.getRobots()[x].getRobotID());
                    }
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Gets all information pertaining to that coordinate i.e. all the robots on the given coordinate.
     * <p>
     * precon the coordinate must be on the map
     *
     * @param coord a coordinate corresponding to the location of inquiry
     * @return the robots on the given coordinate or null if the coordinate is no on the map
     */
    public ArrayList<Robot> getRobotInfo(Coordinate coord) {

        if (Gameboard.robotsOnCoordinate.containsKey(coord)) {
            return Gameboard.robotsOnCoordinate.get(coord);
        }

        return null;
    }

    /**
     * Determines if a robot is AI controlled or not and returns true if it is AI controlled
     * The last char of the string is the gang that the robot is a part of. It is used as the index into
     * the gang array
     *
     * @param robotID The ID of the robot.
     * @return a boolean representing if the robot is controlled by AI or not
     */
    public boolean isAI(String robotID) {
        return gangs[findGang(robotID)].isAI();
    }

    /**
     * Calls the computer AI to make their move.
     *
     * @param robotID The ID of the robot.
     */
    @SuppressWarnings("EmptyMethod")
    public void moveAIRobot(String robotID) {
    }
}