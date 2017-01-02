package Model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;

import static Model.Colour.*;

public class Robot {

    /**
     * The ID of the gang that the validRobot is to be on.
     */
    private final int gangID;
    /**
     * Where all of the validRobot's messages are stored upon being received.
     */
    private final Stack<Pair<String, String>> inbox;

    // IDENTIFIERS:
    /**
     * The validRobot's JSON after it has been parsed by Gson.
     */
    public Map<String, Object> parsedJSON;
    /**
     * The class of the robot
     */
    private String robotType;
    /**
     * The ID of the team that the validRobot was made by.
     */
    private String teamID;

    // MESSAGING:
    /**
     * A unique identifier for each validRobot. It's based on the combination of: (robotName) +
     * (teamID) + (gangID).
     */
    private String robotID;

    // BATTLE STATE:
    /**
     * Dictates whether or not a validRobot can shoot again in that turn.
     */
    private boolean hasShot;

    /**
     * The moves that the validRobot has remaining.
     */
    private int movesRemaining;

    /**
     * The amount of damage that the validRobot does on each attack.
     */
    private int attack;

    /**
     * The current health of the validRobot.
     */
    private int health;
    /**
     * The maximum distance that the validRobot can move.
     */
    private int movement;
    /**
     * The range of the validRobot's attack.
     */
    private int range;
    /**
     * The direction that the robot is facing
     */
    private int direction;

    /**
     * The sprite of the robot
     */
    private BufferedImage sprite;

    /**
     * The constructor of a standard validRobot.
     *
     * @param json   A raw json string containing all of the necessary information to create a
     *               validRobot.
     * @param gangID The ID of the gang that the validRobot is to be in.
     */
    public Robot(String json, int gangID) {
        this.parseJSON(json);
        this.gangID = gangID;
        this.inbox = new Stack<>();

        boolean hasErrors = validJSON(parsedJSON) && makeRobotID() && initializeRobotAttributes();
        this.initializeSprite();
        if (!hasErrors) throw new IllegalArgumentException("The JSON is not valid.");

        this.initializeCoordinates();


    }

    /*
     * +--------+------+--------------------+------------+
     * | Color  | Gang | Starting Direction | Coordinate |
     * +--------+------+--------------------+------------+
     * | Red    |    0 |                  0 | (309, 500) |
     * | Orange |    1 |                  1 | (433, 284) |
     * | Yellow |    2 |                  2 | (683, 284) |
     * | Green  |    3 |                  3 | (807, 500) |
     * | Blue   |    4 |                  4 | (683, 716) |
     * | Purple |    5 |                  5 | (433, 716) |
     * +--------+------+--------------------+------------+
     */

    /**
     * Checks that the JSON that was downloaded contains all of the correct keys
     *
     * @param json a json that represents the robot's information.
     * @return true if the JSON is valid, false if not.
     */
    @SuppressWarnings("SimplifiableIfStatement")
    static boolean validJSON(Map<String, Object> json) {
        // Checking that the JSON contains all of the appropriate keys
        if (!json.containsKey("team")) return false;
        if (!json.containsKey("class")) return false;
        if (!json.containsKey("name")) return false;
        if (!json.containsKey("matches")) return false;
        if (!json.containsKey("wins")) return false;
        if (!json.containsKey("losses")) return false;
        if (!json.containsKey("executions")) return false;
        if (!json.containsKey("lived")) return false;
        if (!json.containsKey("died")) return false;
        if (!json.containsKey("absorbed")) return false;
        if (!json.containsKey("killed")) return false;
        if (!json.containsKey("moved")) return false;
        return json.containsKey("code");
    }

    /*
     * @return the maximum distance that the robot can move
     */
    public int getMovement() {
        return movement;
    }

    private void parseJSON(String json) {
        Gson gson = new Gson();
        this.parsedJSON = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    /**
     * Creates a unique validRobot identifier based on the validRobot's name, software team ID, and
     * gang ID.
     * <p>
     *
     * @return True if the robot's ID was successfully created
     */
    private boolean makeRobotID() {
        String name = this.parsedJSON.get("name").toString();
        this.teamID = this.parsedJSON.get("team").toString();
        if (name.equals("")) {
            System.err.println("Robot name: " + name + " is not valid");
            return false;
        } else if (this.teamID.equals("")) {
            System.err.println("Robot team: " + this.teamID + " is not valid");
            return false;
        } else {
            this.robotID = name + this.teamID + this.gangID;
        }

        return true;
    }

    /**
     * Assigns various validRobot attributes based on their class like:
     * - Maximum movement distance
     * - Maximum health
     * - Their damage
     * - Maximum range
     * <p>
     * ** Not to be used outside of the constructor
     */
    private boolean initializeRobotAttributes() {
        String type = this.parsedJSON.get("class").toString();
        switch (type) {
            case "Scout":
                this.health = 1;
                this.attack = 1;
                this.movement = 3;
                this.range = 2;
                break;
            case "Sniper":
                this.health = 2;
                this.attack = 2;
                this.movement = 2;
                this.range = 3;
                break;
            case "Tank":
                this.health = 3;
                this.attack = 3;
                this.movement = 1;
                this.range = 1;
                break;
            default:
                System.err.println(type + " is not a valid robot class");
                return false;
        }
        this.robotType = type;
        this.movesRemaining = this.movement;
        this.hasShot = true;
        return true;
    }

    /*
     * +--------+------+--------------------+------------+
     * | Color  | Gang.java | Starting Direction | Coordinate |
     * +--------+------+--------------------+------------+
     * | Red    |    0 |                  0 | (309, 500) |
     * | Orange |    1 |                  1 | (433, 284) |
     * | Yellow |    2 |                  2 | (683, 284) |
     * | Green  |    3 |                  3 | (807, 500) |
     * | Blue   |    4 |                  4 | (683, 716) |
     * | Purple |    5 |                  5 | (433, 716) |
     * +--------+------+--------------------+------------+
     */

    /**
     * Puts the robot in their initial position and sets their direction
     * <p>
     * ** Not to be used outside of the constructor
     */
    private void initializeCoordinates() {
        switch (this.getGangID()) {
            case 0: // RED
                this.addToHashMaps(getStartingCoordinate(3));
                this.direction = 0;
                break;
            case 1: // ORANGE
                this.addToHashMaps(getStartingCoordinate(4));
                this.direction = 1;
                break;
            case 2: // YELLOW
                this.addToHashMaps(getStartingCoordinate(5));
                this.direction = 2;
                break;
            case 3: // GREEN
                this.addToHashMaps(getStartingCoordinate(0));
                this.direction = 3;
                break;
            case 4: // BLUE
                this.addToHashMaps(getStartingCoordinate(1));
                this.direction = 4;
                break;
            case 5: // PURPLE
                this.addToHashMaps(getStartingCoordinate(2));
                this.direction = 5;
                break;
        }
    }

    /**
     * This function loads the corresponding image for each robot
     */
    private void initializeSprite() {
        String imageName = "";
        switch (gangID) {
            case 0:
                imageName += "red_";
                break;
            case 1:
                imageName += "orange_";
                break;
            case 2:
                imageName += "yellow_";
                break;
            case 3:
                imageName += "green_";
                break;
            case 4:
                imageName += "blue_";
                break;
            case 5:
                imageName += "purple_";
                break;
        }
        imageName += getType().toLowerCase() + ".png";
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A recursive helped function that gets the starting coordinate for a color that will be in a given direction.
     * <p>
     * The function starts at the center of the gameboard, and works its way outwards to the edges (specifically the corners).
     * <p>
     * This is for internal use only.
     *
     * @param direction The direction that the function is to go in.
     * @return The coordinate that is in the corner of the board in the direction from the center that was specified.
     */
    private Coordinate getStartingCoordinate(int direction) {
        Coordinate dest = Gameboard.direction(Gameboard.getCenter(), direction);
        if (dest != null) {
            return getStartingCoordinate(dest, direction);
        } else {
            return null;
        }
    }

    /**
     * A recursive helped function that gets the starting coordinate for a color that will be in a
     * given direction.
     * <p>
     * The function starts at the center of the gameboard, and works its way outwards to the edges
     * (specifically the corners).
     * <p>
     * This is for internal use only.
     *
     * @param direction The direction that the function is to go in.
     * @return The coordinate that is in the corner of the board in the direction from the center
     * that was specified.
     */
    private Coordinate getStartingCoordinate(Coordinate c, int direction) {
        Coordinate dest = Gameboard.direction(c, direction);
        if (dest != null) {
            return getStartingCoordinate(dest, direction);
        } else {
            return c;
        }

    }

    /**
     * Returns the type of the validRobot
     *
     * @return A string containing the type of the validRobot.
     */
    public String getType() {
        return this.robotType;
    }

    /**
     * Gets the colour of the robot using the gangID as a key.
     *
     * @return The colour of the robot
     */
    @SuppressWarnings("Duplicates")
    public Colour getColour() {
        switch (this.getGangID()) {
            case 0:
                return RED;
            case 1:
                return ORANGE;
            case 2:
                return YELLOW;
            case 3:
                return GREEN;
            case 4:
                return BLUE;
            case 5:
                return PURPLE;
        }
        return null;
    }

    /**
     * The ID of the gang that the validRobot is in.
     *
     * @return The ID of the gang that the validRobot is in.
     */
    public int getGangID() {
        return this.gangID;
    }

    /**
     * An internal function to aid in the setup of robotsOnCoordinate
     *
     * @param coordinate the coordinate that the robot is to start on
     */
    private void addToHashMaps(Coordinate coordinate) {
        System.out.println(coordinate + " added as a key");
        Gameboard.robotCoordinates.put(this, coordinate);
        System.out.println(Gameboard.robotCoordinates.size());
        if (!Gameboard.robotsOnCoordinate.containsKey(coordinate)) {
            Gameboard.robotsOnCoordinate.put(coordinate, new ArrayList<>(Collections.singletonList(this)));
        } else {
            Gameboard.robotsOnCoordinate.get(coordinate).add(this);
        }
    }

    /**
     * Removes the robots from the gameboard on their death.
     *
     * @param robot The robot to be removed.
     */
    private void removeFromHashMaps(Robot robot) {

        if (Gameboard.robotCoordinates.containsKey(robot)) {
            Coordinate c = Gameboard.robotCoordinates.get(robot);
            Gameboard.robotCoordinates.remove(robot);
            if (Gameboard.robotsOnCoordinate.containsKey(c)) {
                Gameboard.robotsOnCoordinate.get(c).remove(robot);
            }
        }
    }

    /**
     * The ID of the team that the validRobot is in.
     *
     * @return The ID of the team that the validRobot is in.
     */
    public String getTeamID() {
        return this.teamID;
    }

    /**
     * The ID of the validRobot.
     *
     * @return The ID of the validRobot.
     */
    public String getRobotID() {
        return this.robotID;
    }

    /**
     * Gets the health of the validRobot.
     *
     * @return an integer representing the validRobot's current health.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Reduces the health by the amount provided.
     *
     * @param amount The amount of health to be removed from the validRobot.
     */
    public void setHealth(int amount) {
        if (this.health - amount < 0) {
            this.health = 0;
            StatsLogger.updateAbsorbed(this.getRobotID(), this.health);
        } else {
            this.health -= amount;
            StatsLogger.updateAbsorbed(this.getRobotID(), this.health);
        }
    }

    /**
     * Gets the damage that the validRobot can do.
     *
     * @return an integer of the damage that the validRobot does when it attacks a tile.
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Returns the validRobot's current health.
     *
     * @return true if the validRobot's health is above 0, false if not.
     */
    public boolean isAlive() {
        if (this.health > 0) {
            return true;
        } else {
            removeFromHashMaps(this);
        }
        return this.health > 0;
    }

    /**
     * Returns the amount of moves that are remaining
     *
     * @return the amount of moves that are remaining
     */
    public int getMovesRemaining() {
        return this.movesRemaining;
    }

    /**
     * Returns the direction that the robot is currently facing
     *
     * @return the direction that the robot is currently facing
     */
    public int getDirection() {
        return this.direction % 6;
    }

    /**
     * Gets the range of the robot.  SCOUT = 2, SNIPER = 3, TANK = 1
     *
     * @return an int representing the range of the robot
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Refreshes the robots number of moves.
     */
    public void setMovesRemaining() {
        this.movesRemaining = this.movement;
    }

    /**
     * Refreshes the robots ability to shoot.
     */
    public void setCanShoot() {
        this.hasShot = true;
    }
    // MESSAGING:

    /**
     * Gets a message from the inbox
     *
     * @return the string that was on the top of the stack
     */
    public Pair<String, String> receiveMsg() {
        return this.inbox.pop();
    }

    /**
     * Gets a message from the inbox for testing
     * without poping from stack
     *
     * @return the string that was on the top of the stack
     */
    public String receiveMsgTest() {

        return this.inbox.elementAt(0).getValue();
    }

    /**
     * Adds a message to the inbox
     *
     * @param msg     is added to the bottom of the stack
     * @param robotID the respective robot's unique identifier
     */
    public void receiveMsg(String msg, String robotID) {
        this.inbox.push(new Pair<>(robotID, msg));
    }


    @SuppressWarnings("EmptyMethod")
    public void sendMsg(String msg) {
    }

    /**
     * Checks to see if the robots inbox is full and returns true if it is.
     *
     * @return a boolean representing if the inbox is full or not
     */
    public boolean mailFull() {
        return this.inbox.size() <= 6;
    }

    /**
     * Gets the inbox of the robot and returns it.
     *
     * @return a stack of string pairs representing msgs
     */
    public Stack<Pair<String, String>> getInbox() {
        return this.inbox;
    }

    /**
     * Determines if the validRobot can shoot in this current turn.
     *
     * @return true if the validRobot can shoot, false if not.
     */
    public boolean canShoot() {
        return this.hasShot;
    }

    // ACTIONS:

    /**
     * Moves the validRobot forward x tiles.
     *
     * @param times the amount of times that the validRobot is to be moved forward.
     */
    public void move(int times) {
        if (this.movesRemaining > 0) {
            for (int i = 0; i < times && i < this.movesRemaining; i++) {
                this.move();
            }
        } else {
            System.err.println("robot does not have enough moves to take an action");
        }

    }

    /**
     * Moves the validRobot forward one tile in the direction that it is facing
     */
    public void move() {
        if (this.movesRemaining > 0) {
            Coordinate oldCoordinate, newCoordinate;

            // Check if old coordinate exists in both hashmaps
            if (Gameboard.robotCoordinates.containsKey(this)) {
                oldCoordinate = Gameboard.robotCoordinates.get(this);
                if (Gameboard.robotsOnCoordinate.containsKey(oldCoordinate)) {

                    // Check if the new coordinate is out of bounds
                    newCoordinate = Gameboard.direction(oldCoordinate, this.direction % 6);
                    if (newCoordinate != null) {

                        // Remove oldCoordinates
                        Gameboard.robotCoordinates.remove(this);
                        Gameboard.robotsOnCoordinate.get(oldCoordinate).remove(this);

                        // Add newCoordinates
                        Gameboard.robotCoordinates.put(this, newCoordinate);
                        if (!Gameboard.robotsOnCoordinate.containsKey(newCoordinate)) {
                            Gameboard.robotsOnCoordinate.put(newCoordinate, new ArrayList<>(Collections.singletonList(this)));
                        } else {
                            Gameboard.robotsOnCoordinate.get(oldCoordinate).add(this);
                        }
                    } else {
                        System.err.println("new coordinate is null, returning without making changes");
                    }
                } else {
                    System.err.println("robotsOnCoordinates does not contain " + oldCoordinate);
                }
            } else {
                System.err.println("robotCoordinates does not contain " + this);
            }
            StatsLogger.updateMoved(this.getRobotID());
            this.movesRemaining--;
        } else {
            System.err.println("robot does not have enough moves to take an action");
        }
    }

    /**
     * Turns the validRobot one time.
     */
    public void turn() {
        this.direction++;
    }

    /**
     * Turns the validRobot x times.
     *
     * @param times the amount of times that the validRobot is to be turned.
     */
    public void turn(int times) {
        this.direction += times;
    }

    /**
     * Shoots all robots on a coordinate.
     * <p>
     * If the direction or the distance are invalid, the robot losses a turn and an error is printed to the console.
     *
     * @param distance  The distance that the robot is to shoot
     * @param direction The direction that the robot is to shoot in.
     */
    public void shoot(int distance, int direction) {
        if (distance >= 0 && distance <= this.getRange()) {
            Coordinate robotCoordinate = Gameboard.robotCoordinates.get(this);
            ArrayList<Robot> robotsToDamage;
            switch (distance) {
                case 0:
                    break;
                case 1:
                    if (0 <= direction && direction < 6) {
                        robotsToDamage = Gameboard.robotsOnCoordinate.get(robotCoordinate);
                        for (Robot robot : robotsToDamage) {
                            robot.setHealth(this.getAttack());
                        }
                    } else {
                        System.err.println("Direction is not valid for the range " + this.getRange() + ".");
                    }
                    break;
                case 2:
                    if (0 <= direction && direction < 12) {
                        robotsToDamage = Gameboard.robotsOnCoordinate.get(robotCoordinate);
                        for (Robot robot : robotsToDamage) {
                            robot.setHealth(this.getAttack());
                        }
                    } else {
                        System.err.println("Direction is not valid for the range " + this.getRange() + ".");
                    }
                    break;
                case 3:
                    if (0 <= direction && direction < 18) {
                        robotsToDamage = Gameboard.robotsOnCoordinate.get(robotCoordinate);
                        for (Robot robot : robotsToDamage) {
                            robot.setHealth(this.getAttack());
                        }
                    } else {
                        System.err.println("Direction is not valid for the range " + this.getRange() + ".");
                    }
                    break;
            }
        } else {
            System.err.println(distance + " is not a valid distance. The value must be between 0 and the robot's maximum range");
        }
        this.movesRemaining--;
    }

    /**
     * @return The sprite for that robot
     */
    public BufferedImage getSprite() {
        return this.sprite;
    }

    /**
     * @return the current rotation of the robot in radians
     */
    public double getCurrentRotation() {
        return Math.toRadians((this.getDirection() % 6 + 1) * 60 + 30);
    }
}