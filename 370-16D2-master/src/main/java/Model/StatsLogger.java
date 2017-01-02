/*
  Team D2
  Arianne Butler
  Kristof Mercier
  Michael Graham
  Samuel Horvatin
  Christopher Mykota-Reid
 */

package Model;

import com.google.gson.Gson;

import java.util.HashMap;


public class StatsLogger {

    /**
     * string containing stats for the game
     */
    private static String statsJSON;
    /**
     * Hashmap used for creating and updating the JSON
     */
    private static HashMap<String, RobotStats> rawJSON;

    /**
     * Constructor for stats logger.  Creates a JSON with robots and fields initialized.
     * <p>
     * precon robots must not be null
     *
     * @param robots All robots currently in game
     */
    public StatsLogger(Robot[] robots) throws NullPointerException {

        if (robots == null) {
            throw new NullPointerException("robots is null!");
        }

        Gson gson = new Gson();
        rawJSON = new HashMap<>();

        for (Robot robot : robots) {
            rawJSON.put(robot.getRobotID(), new RobotStats(robot));
        }
        statsJSON = gson.toJson(rawJSON);

    }

    /**
     * Updates the matches a robot has played in
     *
     * @param robotID The unique robot ID.
     */
    public static void updateMatches(String robotID) {
        rawJSON.get(robotID).matches += 1;
    }

    /**
     * Updates the wins of a robot
     *
     * @param robotID The unique robot ID.
     */
    public static void updateWins(String robotID) {
        rawJSON.get(robotID).wins += 1;
    }

    /**
     * Updates the losses of a robot
     *
     * @param robotID The unique robot ID.
     */
    public static void updateLosses(String robotID) {
        rawJSON.get(robotID).losses += 1;
    }

    /**
     * Updates how many kills the robot has
     *
     * @param robotID The unique robot ID.
     */
    public static void updateKilled(String robotID) {
        rawJSON.get(robotID).killed += 1;
    }

    /**
     * Updates the damage the robot has taken
     *
     * @param robotID The unique robot ID.
     * @param amount  The amount to be increased by
     */
    public static void updateAbsorbed(String robotID, int amount) {
        rawJSON.get(robotID).absorbed += amount;
    }

    /**
     * Updates the number of the games the robot has lived through
     *
     * @param robotID The unique robot ID.
     */
    public static void updateLived(String robotID) {
        rawJSON.get(robotID).lived += 1;
    }

    /**
     * Updates the number of times the robot has died
     *
     * @param robotID The unique robot ID.
     */
    public static void updateDied(String robotID) {
        rawJSON.get(robotID).died += 1;
    }

    /**
     * Updates the number of tiles a robot has moved
     *
     * @param robotID The unique robot ID.
     */
    public static void updateMoved(String robotID) {
        rawJSON.get(robotID).moved += 1;
    }

    /**
     * Updates the number of executions of the code associated with the robot
     *
     * @param robotID The unique robot ID.
     */
    public static void updateExecutions(String robotID) {
        rawJSON.get(robotID).executions += 1;
    }

    /**
     * Gets the JSON
     *
     * @return the JSON containing the stats
     */
    public static String getJSON() {
        return statsJSON;
    }

    /**
     * Gets a particular aspect of the JSON
     *
     * @param robotID the JSON containing the stats
     * @param aspect  the aspect of the JSON caller is looking for
     * @return If found, string containing the respective aspect, null otherwise
     */
    public static String getJSONAspect(String robotID, String aspect) {
        switch (aspect) {
            case "team":
                return rawJSON.get(robotID).team;
            case "type":
                return rawJSON.get(robotID).type;
            case "name":
                return rawJSON.get(robotID).name;
            case "matches":
                return rawJSON.get(robotID).matches + "";
            case "wins":
                return rawJSON.get(robotID).wins + "";
            case "losses":
                return rawJSON.get(robotID).losses + "";
            case "executions":
                return rawJSON.get(robotID).executions + "";
            case "lived":
                return rawJSON.get(robotID).lived + "";
            case "died":
                return rawJSON.get(robotID).died + "";
            case "absorbed":
                return rawJSON.get(robotID).absorbed + "";
            case "killed":
                return rawJSON.get(robotID).killed + "";
            case "moved":
                return rawJSON.get(robotID).moved + "";
            case "code":
                return rawJSON.get(robotID).code;
            default:
                System.err.println("Unable to find JSON aspect of name:" + aspect);
                return null;
        }
    }

    /**
     * Creates a dummy JSON formatted as the JSON from the Robot Librarian would be (hopefully) for testing UI
     *
     * @return A JSON with teams, robots under those teams and stats under those robots
     */
    public static String dummyJSON() {

        String json = "[{ \"team\" : \"John's Junkers\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"Wow\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] \n" +
                " },\n";
        json += "{ \"team\" : \"John's Junkers\"\n" +
                ", \"class\" : \"Scout\"\n" +
                ", \"name\" : \"dat boi\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 78\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 17\n" +
                ", \"died\" : 55\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"John's Junkers\"\n" +
                ", \"class\" : \"Tank\"\n" +
                ", \"name\" : \"Aaron Carter\"\n" +
                ", \"matches\" : 93\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 13\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 22\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"Dat Boi's big boi robois\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"pepe le pew\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 42\n" +
                ", \"died\" : 0\n" +
                ", \"absorbed\" : 22\n" +
                ", \"killed\" : 14\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"Dat Boi's big boi robois\"\n" +
                ", \"class\" : \"Scout\"\n" +
                ", \"name\" : \"MY WIFE\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"Dat Boi's big boi robois\"\n" +
                ", \"class\" : \"Tank\"\n" +
                ", \"name\" : \"Frank Ocean\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"Skynet3.0\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"xX_trollsurmom_Xx\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 64\n" +
                ", \"lived\" : 43\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 10\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"Skynet3.0\"\n" +
                ", \"class\" : \"Tank\"\n" +
                ", \"name\" : \"Da Terminatah\"\n" +
                ", \"matches\" : 1337\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 100000\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 420\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"Skynet3.0\"\n" +
                ", \"class\" : \"Scout\"\n" +
                ", \"name\" : \"Fast Footed Fred\"\n" +
                ", \"matches\" : 1337\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 100\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 420\n" +
                ", \"moved\" : 88\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] },\n";
        json += "{ \"team\" : \"gr8 b8 m8\"\n" +
                ", \"class\" : \"Tank\"\n" +
                ", \"name\" : \"i r8 it 8/8\"\n" +
                ", \"matches\" : 88\n" +
                ", \"wins\" : 8\n" +
                ", \"losses\" : 8\n" +
                ", \"executions\" : 888\n" +
                ", \"lived\" : 88\n" +
                ", \"died\" : 8\n" +
                ", \"absorbed\" : 8\n" +
                ", \"killed\" : 88\n" +
                ", \"moved\" : 8\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }]\n";
        return json;
    }

    /**
     * A helper class for serializing our JSON file.  A class that holds all the stats of a robot that need to
     * be recorded for the StatsLogger.
     */
    private class RobotStats {

        final String team;
        final String type;
        final String name;
        final String code;
        int matches;
        int wins;
        int losses;
        int executions;
        int lived;
        int died;
        int absorbed;
        int killed;
        int moved;

        /**
         * The constructor for RobotStats.
         *
         * @param robot the robot to construct stats for
         */
        RobotStats(Robot robot) {
            this.team = robot.getTeamID();
            this.type = robot.getType();
            this.name = robot.getRobotID();
            this.matches = 0;
            this.wins = 0;
            this.losses = 0;
            this.executions = 0;
            this.lived = 0;
            this.died = 0;
            this.absorbed = 0;
            this.killed = 0;
            this.moved = 0;
            this.code = "";
        }
    }
}