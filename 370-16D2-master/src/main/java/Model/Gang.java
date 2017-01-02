package Model;

import java.security.InvalidParameterException;

import static Model.Colour.*;

/**
 * Created by Sam Horovatin 2016-11-25
 */


public class Gang {

    /**
     * The flag denoting if this gang is controlled by an AI or a player
     */
    private final boolean AI;

    /**
     * The array of robots on a team
     */
    private final Robot[] robotCollection;
    /**
     * The ID for the entire robot gang
     */
    private final int ID;

    /**
     * Initilizes the Gang
     *
     * @param robotCollection, An array containing the robots in the game
     * @param ID,              The teams ID (***is this the colour? - change ID to colour if so***)
     *                         ADDTO: CHANGES FROM DESIGN DOC
     * @param isAI,            If this gang is robot or player controlled
     */
    public Gang(Robot[] robotCollection, int ID, boolean isAI) {
        this.AI = isAI;
        this.robotCollection = robotCollection;
        this.ID = ID;
        if (this.ID > 5 || this.ID < 0) {
            throw new InvalidParameterException(ID + " is not a valid parameter");
        }
    }

    /**
     * Gets the gangs Robots and returns them in a array
     *
     * @return an array containing all robots in a gang
     */
    public Robot[] getRobots() {
        return this.robotCollection;
    }

    /**
     * Gets the gangs Colour and returns it
     * <p>
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
     *
     * @return the gangs Colour
     */
    public Colour getColour() {
        switch (this.getID()) {
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
     * Gets the gangs ID and returns it
     *
     * @return the gangs ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * TODO: ADD TO CHANGES DESIGN DOC
     * Gets if the gang is an AI or not
     *
     * @return if the Robot is an AI
     */
    public boolean isAI() {
        return this.AI;
    }
}
