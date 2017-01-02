/*
 * Team D2
 * Arianne Butler
 * Kristof Mercier
 * Michael Graham
 * Samuel Horvatin
 * Christopher Mykota-Reid
 */

package Model;

import java.awt.*;

public class Coordinate extends Point {

    /**
     * A hexagon that can be drawn onto the screen.
     */
    private Polygon hex;

    /**
     * Creates a new coordinate on a cartesian coordinate plane
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(int x, int y) {
        super(x, y);
        try {
            this.hex = this.makeHex(x, y, Gameboard.getRadius());
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: a coordinate (" + x + ", " + y + ")is being created before a Gameboard has been defined.");
        }
    }

    /**
     * Adds two coordinates together and returns their resultant coordinate
     *
     * @param a A coordinate
     * @param b A coordinate
     * @return The result of adding the two coordinates
     */
    public static Coordinate add(Coordinate a, Coordinate b) {
        return new Coordinate(a.x + b.x, a.y + b.y);
    }

    /**
     * Initializes all of the points of a hexagon and returns it.
     * <p>
     * Algorithm based off of:
     * http://stackoverflow.com/questions/13256359/trying-to-draw-a-hexagon-shape-in
     *
     * @param x      coordinate for hex to be centered on on the jpanel
     * @param y      coordinate for hex to be centered on on the jpanel
     * @param radius determines how large a hex will be
     * @return a drawn hexagon with inputted attributes
     */
    private Polygon makeHex(int x, int y, int radius) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            hex.addPoint((int) (x + radius * Math.cos((1 + i * 2) * Math.PI / 6)), (int) (y + radius * Math.sin((1 + i * 2) * Math.PI / 6)));
        }
        return hex;
    }

    /**
     * gets the hexagon for a respective coordinate
     *
     * @return Returns the hexagon for that respective coordinate
     */
    public Polygon getHex() {
        return this.hex;
    }
}