package Model;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Michael on 2016-11-17.
 * Populated by Sam on 2016-11-26.
 */
@SuppressWarnings("ConstantConditions")
public class GameboardTest {
    private final Point testOrigin;

    public GameboardTest() {
        this.testOrigin = new Point(600, 500);
        new TestingSuite();
    }

    @Test
    public void getSize() throws Exception {
        new Gameboard(3);
        assert Gameboard.getSize() == 3;
        assert Gameboard.getSize() != 4;
    }

    @Test
    public void getKey1() throws Exception {
        new Gameboard(3);

        //checks entire range and ensures that all keys that definitively exist are actually returned by getKey
        for (Coordinate c : Gameboard.coordinates) {
            assert Gameboard.getKey(c.x, c.y).equals(c);
        }

        // Check out of bound keys
        assert Gameboard.getKey(0, 0) == null;
        assert Gameboard.getKey(-300, 250) == null;
        assert Gameboard.getKey(9000, 250) == null;
        assert Gameboard.getKey(300, -250) == null;
        assert Gameboard.getKey(300, -750) == null;
        assert Gameboard.getKey(-900, -750) == null;
        assert Gameboard.getKey(9000, 7500) == null;

        // Randomly check if a "valid" distance from a coordinate is actually valid
        // This more or less tests all valid ranges
        int iterations = 100;
        for (int i = 0; i < iterations; i++) {
            for (Coordinate c : Gameboard.coordinates) {
                int x, y, r;
                r = Gameboard.getRadius();
                x = ThreadLocalRandom.current().nextInt(-40, 39 + 1);

                if (Math.abs(x) != r) {
                    y = (int) Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));
                } else {
                    y = 0;
                }
                Coordinate coordinateInRange = new Coordinate(c.x + x, c.y + y);
                assert Gameboard.getKey(coordinateInRange).equals(c);
            }
        }
    }

    @Test
    public void getKey() throws Exception {
        Coordinate testCoordinate;
        new Gameboard( 3);

        testCoordinate = new Coordinate(0, 0);
        assert Gameboard.getKey(testCoordinate) == null;

        testCoordinate = new Coordinate(-300, 250);
        assert Gameboard.getKey(testCoordinate) == null;

        testCoordinate = new Coordinate(9000, 250);
        assert Gameboard.getKey(testCoordinate) == null;

        testCoordinate = new Coordinate(300, -250);
        assert Gameboard.getKey(testCoordinate) == null;

        testCoordinate = new Coordinate(300, -750);
        assert Gameboard.getKey(testCoordinate) == null;

        testCoordinate = new Coordinate(-900, -750);
        assert Gameboard.getKey(testCoordinate) == null;

        testCoordinate = new Coordinate(9000, 7500);
        assert Gameboard.getKey(testCoordinate) == null;

        //checks entire range and ensures that all keys that definitively exist are actually returned by getKey
        for (Coordinate c : Gameboard.coordinates) {
            assert Gameboard.getKey(c).equals(c);
        }

        // Randomly check if a "valid" distance from a coordinate is actually valid
        // This more or less tests all valid ranges
        int iterations = 100;
        for (int i = 0; i < iterations; i++) {
            for (Coordinate c : Gameboard.coordinates) {
                int x, y, r;
                r = Gameboard.getRadius();
                x = ThreadLocalRandom.current().nextInt(-40, 39 + 1);

                if (Math.abs(x) != r) {
                    y = (int) Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));
                } else {
                    y = 0;
                }
                assert Gameboard.getKey(c.x + x, c.y + y).equals(c);
            }
        }
    }


    @Test
    public void direction() throws Exception {
        new Gameboard(3);

        //testing from central hexagon
        //Dependant on getNeighbors()
        Coordinate coordinate;

        //testing from central hexagon
        // NOTE: All of these coordinates are physically known to be the EXACT location.
        // We already know that getKey works
        coordinate = Gameboard.getCenter();
        assert Gameboard.direction(coordinate, 0).equals(Gameboard.getKey(641, 500));
        assert Gameboard.direction(coordinate, 1).equals(Gameboard.getKey(600, 572));
        assert Gameboard.direction(coordinate, 2).equals(Gameboard.getKey(516, 572));
        assert Gameboard.direction(coordinate, 3).equals(Gameboard.getKey(475, 500));
        assert Gameboard.direction(coordinate, 4).equals(Gameboard.getKey(516, 428));
        assert Gameboard.direction(coordinate, 5).equals(Gameboard.getKey(600, 428));

        //testing from right most hexagon (we know getCoordinateInDirection works at this point...)
        Coordinate rightMostCoordinate = Gameboard.direction(Gameboard.getCenter(), 0);
        assert Gameboard.direction(rightMostCoordinate, 0) == null;
        assert Gameboard.direction(rightMostCoordinate, 1) == null;
        assert Gameboard.direction(rightMostCoordinate, 2) != null;
        assert Gameboard.direction(rightMostCoordinate, 3) != null;
        assert Gameboard.direction(rightMostCoordinate, 4) != null;
        assert Gameboard.direction(rightMostCoordinate, 5) == null;

        //testing from bottom left most hexagon
        Coordinate leftMostCoordinate = Gameboard.direction(Gameboard.getCenter(), 3);
        assert Gameboard.direction(leftMostCoordinate, 0) != null;
        assert Gameboard.direction(leftMostCoordinate, 1) != null;
        assert Gameboard.direction(leftMostCoordinate, 2) == null;
        assert Gameboard.direction(leftMostCoordinate, 3) == null;
        assert Gameboard.direction(leftMostCoordinate, 4) == null;
        assert Gameboard.direction(leftMostCoordinate, 5) != null;
    }

    @Test
    public void getCenter() throws Exception {
        // The origin should be approximately close to the actual center of the board.
        // ~ -40 away to be exact
        new Gameboard( 7);
        assert Gameboard.getCenter().equals(Gameboard.getKey(new Coordinate(this.testOrigin.x - 40, this.testOrigin.y)));
    }

    @Test
    public void getNeighbors() throws Exception {
        new Gameboard(7);
        Coordinate[] neighbors = Gameboard.getNeighbors(Gameboard.getCenter(), 3).get(1);

        // Testing the first ring
        Assert.assertEquals(neighbors[0], Gameboard.getKey(641, 500));
        Assert.assertEquals(neighbors[1], Gameboard.getKey(600, 572));
        Assert.assertEquals(neighbors[2], Gameboard.getKey(516, 572));
        Assert.assertEquals(neighbors[3], Gameboard.getKey(475, 500));
        Assert.assertEquals(neighbors[4], Gameboard.getKey(516, 428));
        Assert.assertEquals(neighbors[5], Gameboard.getKey(600, 428));

        // Testing the second ring
        // TODO Do this in the morning Michael

    }

    @Test
    public void getVisibleRobotCount() throws Exception {
        new Gameboard(7);
        new TestingSuite();

        // Some robots & coordinates.
        Robot[] robots = {new Robot(TestingSuite.getJSON("tankJson"), 1), new Robot(TestingSuite.getJSON("sniperJson"), 1), new Robot(TestingSuite.getJSON("scoutJson"), 1),};
        Coordinate[] coordinates = {Gameboard.direction(Gameboard.getCenter(), 0), Gameboard.direction(Gameboard.getCenter(), 2), Gameboard.direction(Gameboard.getCenter(), 4)};

        // Initializes coordinates
        for (int i = 0; i < 3; i++) {
            Gameboard.robotCoordinates.put(robots[i], coordinates[i]);
            Gameboard.robotsOnCoordinate.put(coordinates[i], new ArrayList<>());
            Gameboard.robotsOnCoordinate.get(coordinates[i]).add(robots[i]);
        }

        // Test coordinates
        Assert.assertEquals(Gameboard.getVisibleRobotCount(robots[0]), 3);

    }


    @Test
    public void getRadius() throws Exception {
        // The radius can only be 40.
        assert Gameboard.getRadius() == 40;
    }


}