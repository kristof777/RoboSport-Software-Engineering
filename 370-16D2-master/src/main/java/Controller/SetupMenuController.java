/*
 * Team D2
 * Arianne Butler
 * Kristof Mercier
 * Michael Graham
 * Samuel Horvatin
 * Christopher Mykota-Reid
 */

package Controller;

import Model.Gang;
import Model.Robot;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static Model.StatsLogger.dummyJSON;

/**
 * The SetUpMenuController is responsible for all changes made within the SetUpMenu view. Its
 * primary purpose is to ensure that the gangs of robots are set up and instantiated correctly.
 * From the SetUpMenu view, the user is able to select from the group of computer AI’s designed
 * by the various student software teams, each team denoted by teamID.
 */

@SuppressWarnings("unchecked")
public class SetupMenuController {

    // The JSON file containing teams, robots, and stats
    // REPLACE WITH REAL JSON
    private final String datjson = dummyJSON();
    /**
     * Gang array list containing all initialized gangs
     */
    private final ArrayList<Gang> gangs = new ArrayList<>();
    /**
     * holds basic datjson of all robots in the Robot Librarian
     */
    Map<String, Object> basicStatsJSON;
    /**
     * The images of each tile
     */
    private Image images[];
    private int desiredNumberPlayers;


    /**
     * Constructor for SetupMenuController
     */
    public SetupMenuController() {
    }

    /**
     * returns an array of the robot names in the basicStatsJSON file that match the current
     * Created-by criteria selected in the SetUpMenu view
     *
     * @return the names of the robots in question.
     */
    public String[] getNamesInCriteria() {
        return new String[0];
    }

    /**
     * returns an array of robots sorted by wins
     *
     * @return An array of robots sorted by wins
     */
    public Robot[] sortWins() {
        return new Robot[0];
    }

    /**
     * Creates a new gang based on color and returns the number of gangs made so far
     *
     * @param bots   a string of bot names
     * @param colour what color is it
     * @param isAI   is the gang controlled by an AI
     *               Colors: red = 0, orange = 1, yellow = 2, green = 3, blue = 4, purple = 5
     * @return the gangs that were created via their colour
     */
    public int makeGangByColour(String[] bots, int colour, boolean isAI) {

        Gson gson = new Gson();

        // Check if gang colour has already been added and remove original
        for (int i = 0; i < gangs.size(); i++) {
            if (colour == gangs.get(i).getID()) {
                gangs.remove(i);
            }
        }

        // Array of all robot names from datjson (a string array containing robots and their stats)
        Object[] robotsByName = gson.fromJson(datjson, new TypeToken<Object[]>() {
        }.getType());

        Robot[] robots = new Robot[3];

        // Loop over all robots in JSON
        for (Object aRobotsByName : robotsByName) {
            // Put current robot info in LinkedTreeMap
            LinkedTreeMap<String, Object> curr = (LinkedTreeMap<String, Object>) aRobotsByName;

            // Check if currRobot is the selected Scout, Sniper, or Tank
            for (int j = 0; j < bots.length; j++) {

                // If the current robot is the selected robot
                if (curr.get("name").equals(bots[j])) {
                    // Initialize the current robot and add it to the new gang
                    try {
                        robots[j] = new Robot(gson.toJson(curr), colour);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Make new Gang of robots
        Gang newGang = new Gang(robots, colour, isAI);
        gangs.add(newGang);

        return gangs.size();

    }


    /**
     * Return an array of 2, 3, or 6 gangs, each with 3 robots
     *
     * @return an array of all robot gangs
     * @throws Exception if the number of gangs is off
     */
    public Gang[] makeAllGangs() throws Exception {

        // If 1, 4 or 5 gangs are initialized, display "Number of players not allowed"
        if (gangs.size() != desiredNumberPlayers) {
            throw new Exception("Didn't pick enough gangs\n");
        } else {
            return gangs.toArray(new Gang[0]);
        }
    }

    /**
     * returns a list of all gangs
     *
     * @return an array of all gangs
     */
    public Gang[] getGangs() {
        return gangs.toArray(new Gang[0]);
    }

    /**
     * returns the gang with the specified colour, or null if there isn't one
     *
     * @param colour The colour of the gang that you are trying to get .
     * @return null if the gang doesn't exist with that colour, otherwise it returns the gang.
     */
    public Gang getGangByColour(int colour) {
        for (Gang currGang : gangs) {
            if (currGang.getID() == colour) {
                return currGang;
            }
        }
        return null;
    }

    /**
     * Gets all software dev teams from the JSON file
     *
     * @return a string array of software development teams from CMPT370 2k16
     */
    public String[] getDevTeams() {

        Gson gson = new Gson();

        // Array of all robot names from datjson (a string array containing robots and their stats)
        Object[] robotsByName = gson.fromJson(datjson, new TypeToken<Object[]>() {
        }.getType());

        // Holds dev teams and prevents repeats
        HashSet<String> devTeams = new HashSet<>();

        // For each robot in robotsByName array
        for (Object currentRobot : robotsByName) {
            // Put current robot info in LinkedTreeMap
            LinkedTreeMap<String, Object> curr = (LinkedTreeMap<String, Object>) currentRobot;
            // Add current team to devTeams Hashset (no repeats)
            devTeams.add((String) curr.get("team"));
        }

        return devTeams.toArray(new String[0]);
    }

    /**
     * returns  a string array of robots created by the software dev team identified by teamID
     *
     * @param teamID the selected software dev team
     * @param type   the current type of robot (scout, sniper, tank)
     * @return string array containing robots of given type made by given teamID
     */
    public String[] robotsByDevTeam(String teamID, String type) {

        Gson gson = new Gson();

        // Array of all robot names from datjson (a string array containing robots and their stats)
        Object[] robotsByName = gson.fromJson(datjson, new TypeToken<Object[]>() {
        }.getType());

        // Holds all robots of given type made by given teamID
        HashSet<String> robots = new HashSet<>();

        // For each robot in robotNames array
        for (Object currentRobot : robotsByName) {
            // Put current robot info in LinkedTreeMap
            LinkedTreeMap<String, Object> curr = (LinkedTreeMap<String, Object>) currentRobot;
            // If the current robot of given type was made by given teamID
            if (curr.get("team").equals(teamID) && curr.get("class").equals(type)) {
                // Add the current robot to the list of robots made by given teamID
                robots.add((String) curr.get("name"));
            }
        }
        return robots.toArray(new String[0]);
    }

    /**
     * returns  a string array of datjson stats pertaining to a given robot
     *
     * @param selectedRobot string name of robot
     * @param teamID        string name of software dev team
     * @return string array holding datjson and their fields
     */
    public String[] getStats(String selectedRobot, String teamID) {

        Gson gson = new Gson();

        // Array of all robot names from datjson (a string array containing robots and their stats)
        Object[] robotsByName = gson.fromJson(datjson, new TypeToken<Object[]>() {
        }.getType());

        // Array of stats to display for each robot on GameScreen
        String[] robotStats = new String[4];

        // For each robot, check if the robot was made by team matching teamID
        for (Object aRobotsByName : robotsByName) {

            // Put current robot info in LinkedTreeMap
            LinkedTreeMap<String, Object> curr = (LinkedTreeMap<String, Object>) aRobotsByName;

            // If the current robot is the selected robot
            if (curr.get("name").equals(selectedRobot) && curr.get("team").equals(teamID)) {
                // Add the current robot's stats to the array to be returned
                robotStats[0] = curr.get("wins").toString();
                robotStats[1] = curr.get("losses").toString();
                robotStats[2] = curr.get("absorbed").toString();
                robotStats[3] = curr.get("killed").toString();
            }
        }
        return robotStats;
    }


    /**
     * The number of players the user selected
     *
     * @return The desired number of players
     */
    public int getDesiredNumberPlayers() {
        return desiredNumberPlayers;
    }

    /**
     * Sets the chosen number of players
     *
     * @param desiredNumberPlayers chosen number of players from radio buttons
     */
    public void setDesiredNumberPlayers(int desiredNumberPlayers) {

        gangs.clear();
        this.desiredNumberPlayers = desiredNumberPlayers;
    }
}