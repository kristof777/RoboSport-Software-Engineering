package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * Created by Michael on 2016-11-15.
 * <p>
 * Note: As I did not know how to implement a hexagon
 * coordinate system I VERY ROUGHLY followed the guide provided at:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
public class Gameboard {
    /**
     * The radius of the gameboard
     */
    private static final int radius = 40;
    /**
     * All coordinates within the gameboard
     */
    public static HashSet<Coordinate> coordinates;
    /**
     * a hashmap keyed by robotID which returns the coordinate of the current
     * robot’s location
     */
    public static HashMap<Robot, Coordinate> robotCoordinates;
    /**
     * a hashmap that is keyed by a coordinate and returns a list of robots that
     * are currently occupying that coordinate, if any
     */
    public static HashMap<Coordinate, ArrayList<Robot>> robotsOnCoordinate;
    /**
     * All valid coordinate boundingCircles
     */
    private static HashMap<CircleRange, Coordinate> boundingCircles;
    /**
     * The center including the offset
     */
    private static Coordinate center;
    /**
     * The max width & height of the hexagonal board
     */
    private static int size;
    /**
     * The padding between each hex
     */
    private final double padding = 8;
    /**
     * The x-offset of the cartesian coordinate
     */
    private final double xOff = Math.cos(Math.toRadians(30)) * (radius + this.padding);
    /**
     * The y-offset of the cartesian coordinate
     */
    private final double yOff = Math.sin(Math.toRadians(30)) * (radius + this.padding);
    /**
     * The point at which the gameboard starts at
     */
    private final Point origin;

    /**
     * Creates a new gameboard.
     *
     * @param size The size of the graph.
     */
    public Gameboard(int size) {
        coordinates = new HashSet<>();
        boundingCircles = new HashMap<>();
        robotCoordinates = new HashMap<>();
        robotsOnCoordinate = new HashMap<>();
        Gameboard.size = size;

        this.origin = new Point(1200 / 2, 1000 / 2);
        this.buildGrid();
    }

    /**
     * getter for size
     *
     * @return the size of the gameboard
     */
    public static int getSize() {
        return size;
    }

    /**
     * Gets a key based on an approximate location
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return null if the coordinate doesn't exist
     */
    public static Coordinate getKey(int x, int y) {
        Coordinate key = null;
        for (Map.Entry<CircleRange, Coordinate> entry : boundingCircles.entrySet()) {
            if (entry.getKey().inRange(new Coordinate(x, y))) {
                key = entry.getValue();
            }
        }
        return key;
    }

    /**
     * Gets the count of all visible robots... it may be beneficial to store this information somewhere.
     *
     * @param robot The robot that you are checking the visibility for.
     * @return The count of all visible robots.
     */
    public static int getVisibleRobotCount(Robot robot) {
        ArrayList<Coordinate[]> inRangeCoords;
        ArrayList<Robot> visibleRobots = new ArrayList<>();

        inRangeCoords = Gameboard.getNeighbors(Gameboard.robotCoordinates.get(robot), robot.getMovement());
        for (Coordinate[] coordArray : inRangeCoords) {
            for (Coordinate coordinate : coordArray) {
                if (coordinate != null) {
                    if (Gameboard.robotsOnCoordinate.containsKey(coordinate)) {
                        visibleRobots.addAll(Gameboard.robotsOnCoordinate.get(coordinate));
                    }
                }
            }
        }
        return visibleRobots.size();
    }

    /**
     * Gets the ring of neighbors from a specific radius.
     *
     * @param coordinate The central coordinate
     * @param radius     The radius to be returned
     * @return An array of keyed 0 to ring size
     */
    public static ArrayList<Coordinate[]> getNeighbors(Coordinate coordinate, int radius) {
        ArrayList<Coordinate[]> l = new ArrayList<>();
        l.add(new Coordinate[1]);
        l.add(new Coordinate[6]);
        l.add(new Coordinate[18]);
        l.add(new Coordinate[36]);
        l.get(0)[0] = coordinate;
        if (radius > 0) {
            if (1 <= radius && radius <= 3) {
                l.get(1)[0] = direction(coordinate, 0);
                l.get(1)[1] = direction(coordinate, 1);
                l.get(1)[2] = direction(coordinate, 2);
                l.get(1)[3] = direction(coordinate, 3);
                l.get(1)[4] = direction(coordinate, 4);
                l.get(1)[5] = direction(coordinate, 5);
                if (radius >= 1) {
                    if (l.get(1)[0] != null) {
                        l.get(2)[0] = direction(l.get(1)[0], 0);

                    }
                    if (l.get(1)[1] != null) {
                        l.get(2)[1] = direction(l.get(1)[1], 0);
                        l.get(2)[2] = direction(l.get(1)[1], 1);

                    }
                    if (l.get(1)[2] != null) {
                        l.get(2)[3] = direction(l.get(1)[2], 1);
                        l.get(2)[4] = direction(l.get(1)[2], 2);
                        l.get(2)[5] = direction(l.get(1)[2], 3);

                    }
                    if (l.get(1)[3] != null) {
                        l.get(2)[6] = direction(l.get(1)[3], 3);

                    }
                    if (l.get(1)[4] != null) {
                        l.get(2)[7] = direction(l.get(1)[4], 3);
                        l.get(2)[8] = direction(l.get(1)[4], 4);
                        l.get(2)[9] = direction(l.get(1)[4], 5);

                    }
                    if (l.get(1)[5] != null) {
                        l.get(2)[11] = direction(l.get(1)[5], 0);
                        l.get(2)[10] = direction(l.get(1)[5], 5);

                    }
                    if (radius == 3) {
                        if (l.get(2)[0] != null) {
                            l.get(3)[0] = direction(l.get(2)[0], 0);
                        }
                        if (l.get(2)[1] != null) {
                            l.get(3)[1] = direction(l.get(2)[1], 0);
                        }
                        if (l.get(2)[2] != null) {
                            l.get(3)[2] = direction(l.get(2)[2], 0);
                            l.get(3)[3] = direction(l.get(2)[2], 1);
                        }
                        if (l.get(2)[3] != null) {
                            l.get(3)[4] = direction(l.get(2)[3], 1);
                        }
                        if (l.get(2)[4] != null) {
                            l.get(3)[5] = direction(l.get(2)[4], 1);
                            l.get(3)[6] = direction(l.get(2)[4], 2);
                            l.get(3)[7] = direction(l.get(2)[4], 3);
                        }
                        if (l.get(2)[5] != null) {
                            l.get(3)[8] = direction(l.get(2)[5], 3);
                        }
                        if (l.get(2)[6] != null) {
                            l.get(3)[9] = direction(l.get(2)[6], 3);
                        }
                        if (l.get(2)[7] != null) {
                            l.get(3)[10] = direction(l.get(2)[7], 3);
                        }
                        if (l.get(2)[8] != null) {
                            l.get(3)[11] = direction(l.get(2)[8], 3);
                            l.get(3)[12] = direction(l.get(2)[8], 4);
                            l.get(3)[13] = direction(l.get(2)[8], 5);
                        }
                        if (l.get(2)[9] != null) {
                            l.get(3)[14] = direction(l.get(2)[9], 5);
                        }
                        if (l.get(2)[10] != null) {
                            l.get(3)[15] = direction(l.get(2)[10], 5);
                            l.get(3)[16] = direction(l.get(2)[10], 0);
                        }
                        if (l.get(2)[11] != null) {
                            l.get(3)[17] = direction(l.get(2)[11], 0);
                        }
                    }
                }
            } else {
                System.err.print("ERROR: Radius " + radius + " is not valid. it must be between 1 & 3.");
            }
            if (l.size() <= 0) {
                System.err.println("The neighbors that you inquired on is empty. Did you want that?");
            }
        }
        return l;
    }

    /**
     * Gets the neighbor at getCoordinateInDirection's index
     *
     * @param coordinate The coordinate that the robot is on
     * @param direction  which getCoordinateInDirection the tank is facing
     * @return The corresponding coordinate to the getCoordinateInDirection
     */
    public static Coordinate direction(Coordinate coordinate, int direction) {
        switch (direction) {
            case 0:
                return getNeighborKey(coordinate, new Coordinate(80, 0));
            case 1:
                return getNeighborKey(coordinate, new Coordinate(40, 70));
            case 2:
                return getNeighborKey(coordinate, new Coordinate(-40, 70));
            case 3:
                return getNeighborKey(coordinate, new Coordinate(-80, 0));
            case 4:
                return getNeighborKey(coordinate, new Coordinate(-40, -70));
            case 5:
                return getNeighborKey(coordinate, new Coordinate(40, -70));
            default:
                String msg = "is not a valid direction. There are only six possible directions";
                System.err.println(msg);
                return null;
        }
    }

    /**
     * Adds two coordinates together, and returns the resultant KeyFromRange
     * <p>
     * ** This should not be used outside of the gameboard
     *
     * @param start the starting coordinate
     * @param end   the ending coordinate
     * @return the exact key of the  neighbor coordinate
     */
    private static Coordinate getNeighborKey(Coordinate start, Coordinate end) {
        return getKey(Coordinate.add(start, end));
    }

    /**
     * Gets a key based on an approximate location
     *
     * @param coordinate The approximate location
     * @return null if the coordinate doesn't exist
     */
    public static Coordinate getKey(Coordinate coordinate) {
        Coordinate key = null;
        for (Map.Entry<CircleRange, Coordinate> entry : boundingCircles.entrySet()) {
            if (entry.getKey().inRange(coordinate)) {
                key = entry.getValue();
            }
        }

        return key;
    }

    /**
     * Gets the center.
     *
     * @return Returns the center that includes the offset.
     */
    static Coordinate getCenter() {
        return center;
    }

    /**
     * Returns The radius of the gameboard.
     *
     * @return The radius of the gameboard.
     */
    static int getRadius() {
        return radius;
    }

    /**
     * Builds a gameboard based off of the specifications.
     * <p>
     * Algorithm based off of:
     * http://stackoverflow.com/questions/20734438/algorithm-to-generate-a-hexagonal-grid-with-coordinate-system
     */
    private void buildGrid() {
        float half = size / 2;
        this.initializeCenter();

        for (int row = 0; row < size; row++) {
            int columns = size - Math.abs((int) (row - half));

            for (int column = 0; column < columns; column++) {

                int x = (int) (this.origin.x + this.xOff * (column * 2 - columns));
                int y = (int) (this.origin.y + this.yOff * (row - half) * 3);

                boundingCircles.put(new CircleRange(new Point(x, y)), new Coordinate(x, y));
                coordinates.add(new Coordinate(x, y));
            }
        }
    }

    /**
     * An internal function to get the center coordinate including all offsets.
     */
    private void initializeCenter() {
        int x = (int) (this.origin.x - this.xOff);
        int y = this.origin.y;
        center = new Coordinate(x, y);
    }

    /**
     * This function is used to make a given robot shoot a coordinate IF it is in range and valid...
     * If it is not valid it does nothing and prints out an error message. (ie out of bounds)
     *
     * @param robot     The robot that is shooting.
     * @param direction The direction that the robot is facing in.
     * @param range     The range that the robot is trying to shoot.
     */
    public void shoot(Robot robot, int direction, int range) {
        if (Gameboard.robotCoordinates.containsKey(robot)) {
            if (range < robot.getRange()) {
                ArrayList<Coordinate[]> neighbors = getNeighbors(Gameboard.robotCoordinates.get(robot), 3);
                switch (range) {
                    case 1:
                        shootHelper(6, direction, 0, robot, neighbors);
                        break;
                    case 2:
                        shootHelper(12, direction, 1, robot, neighbors);
                        break;
                    case 3:
                        shootHelper(18, direction, 2, robot, neighbors);
                        break;
                    default:
                        System.err.println("Range " + range + " is not a valid range. Range can only be between 1 and 3 (inclusive)");
                }
            } else {
                System.err.println("Range " + range + " is not a valid range for the robot " + robot + ".");
            }
        } else {
            System.err.println("Robot: " + robot + " is not a valid robot. It does not exist on the Gameboard");
        }
    }


    /**
     * This function is used to actually shoot a robot... It was created to reduce the cluster****
     * that shoot() was becoming.
     *
     * @param max       The maximum amount of directions that a given range can have. IE. a ring of
     *                  neighbors with range 1 can only have 6 directions
     * @param direction The direction that the neighbor is in.
     * @param range     The ring of neighbors that the robot is shooting at.
     * @param robot     The robot that is shooting
     * @param neighbors The neighbors surrounding that robot.
     */
    private void shootHelper(int max, int direction, int range, Robot robot, ArrayList<Coordinate[]> neighbors) {
        if (0 < direction && direction < max) {
            Coordinate coordinateToShoot = neighbors.get(range)[direction];
            if (coordinateToShoot != null) {
                ArrayList<Robot> robotsOnCoordinate = Gameboard.robotsOnCoordinate.get(coordinateToShoot);
                for (Robot robotBeingShot : robotsOnCoordinate) {
                    robotBeingShot.setHealth(robot.getAttack());
                }
            } else {
                System.err.println("Coordinate does not exist... doing nothing... You should see this error if the code sucks");
            }
        } else {
            System.err.println("Direction: " + direction + " is not valid for this range (" + range + ").");
        }
    }

    /**
     * A circular range is a range of points within a circle.
     */
    private class CircleRange {

        final Point center;
        final int radius;

        /**
         * Creates a new circular range.
         *
         * @param origin The center of the circle.
         */
        CircleRange(Point origin) {
            this.center = origin;
            this.radius = Gameboard.getRadius();
        }

        /**
         * Checks if a given point is within the circle.
         *
         * @param point The point to be tested if it falls within the circle.
         * @return True if the point falls within the circle ; false otherwise.
         */
        boolean inRange(Point point) {
            double distance = Math.sqrt(Math.pow(point.x - this.center.x, 2) + Math.pow(point.y - this.center.y, 2));
            return distance <= this.radius;
        }
    }


}

