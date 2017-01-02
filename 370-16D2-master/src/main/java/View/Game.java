package View;

import Model.Coordinate;
import Model.Gameboard;
import Model.Gang;
import Model.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static Controller.GameMaster.gangs;
import static Model.Gameboard.getKey;
import static Model.Gameboard.getNeighbors;

public class Game extends JComponent {

    /**
     * The currently selected hexagon
     */
    private Coordinate selectedHex;
    /**
     * The Swing Graphics
     */
    private Graphics2D graphics;

    private int currentGang;

    private Robot currentRobot;

    public Game() {
        int WIDTH = 1200;
        int HEIGHT = 1000;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.addMouseListener(new Input());
    }

    @SuppressWarnings("EmptyMethod")
    public void displayGangVision() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = (Graphics2D) g;

        this.drawGrid();

        // Draw selection
        if (this.selectedHex != null) {
            this.highLight(this.selectedHex, Color.lightGray);
            this.highlightVisible(this.selectedHex);
        }
        //this.drawRobots();
    }

    /**
     * Draws the gameboard based off of the pieces stored in the gameboard coordinates
     */
    private void drawGrid() {
        this.graphics.setStroke(new BasicStroke(2f));
        // Draw all coordinates in the gameboard
        for (Coordinate coordinate : Gameboard.coordinates) {
            this.graphics.setColor(Color.gray);
            this.graphics.drawPolygon(coordinate.getHex());
            this.graphics.setColor(Color.lightGray);
            this.graphics.fill(coordinate.getHex());

            // Uncomment this line to see the bounding circle of the hexagon
            // this.drawCenteredCircle(this.graphics, coordinate, 80);
        }
    }

    /**
     * Highlights the hex at the given coordinate.
     *
     * @param coordinate The coordinate to be highlighted.
     * @param color      The color that the tile is to be coloured.
     */
    private void highLight(Coordinate coordinate, Color color) {
        this.graphics.setColor(color);
        if (Gameboard.coordinates.contains(coordinate)) {
            this.graphics.fillPolygon(coordinate.getHex());
        }
    }

    /**
     * Highlights all neighbors one tile away from the
     */
    private void highlightVisible(Coordinate coordinate) {
        if (Gameboard.coordinates.contains(coordinate)) {
            ArrayList<Coordinate[]> neighbors = getNeighbors(coordinate, 3);

            for (Coordinate[] l : neighbors) {
                for (Coordinate c : l) {
                    if (c != null) {
                        this.highLight(c, Color.white);
                    }
                }
            }
        }
    }


    private void drawRobot(Robot robot, Coordinate c) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        AffineTransform at = new AffineTransform();
        at.translate(c.x - robot.getSprite().getWidth() / 2, c.y - robot.getSprite().getHeight() / 2);
        at.rotate(robot.getCurrentRotation(), robot.getSprite().getWidth() / 2, robot.getSprite().getHeight() / 2);
        g2d.drawImage(robot.getSprite(), at, this);
        g2d.dispose();
    }

    /**
     * Draws the robot's coordinates tile in its respective colors.
     * This will only display things within the gangs vision.
     */
    private void drawRobots() {

        System.out.println("ROBOTS");
        System.out.println(Gameboard.robotCoordinates.size());

        Gang gang = gangs[getCurrentGang()];
        Robot robot;
        ArrayList<Coordinate[]> visibleTiles;
        Coordinate currentHex;
        ArrayList<Robot> robotsToDisplay;

        //for each robot in the gang
        for (int i = 0; i < gang.getRobots().length; i++) {
            robot = gang.getRobots()[i];
            visibleTiles = Gameboard.getNeighbors(Gameboard.robotCoordinates.get(robot), robot.getRange());

            //for each tile ring
            for (int j = 0; j < robot.getRange(); j++) {

                //for each tile in the current ring
                for (int k = 0; k < visibleTiles.get(j).length; k++) {
                    currentHex = visibleTiles.get(j)[k];
                    robotsToDisplay = Gameboard.robotsOnCoordinate.get(currentHex);

                    //for each robot on that tile
                    for (Robot robotToDisplay : robotsToDisplay) {
                        //TODO MIKE you had this function commented out in your test drawRobots(). Is it needed?
                        //robotsToDisplay.rotateSprite();
                        drawRobot(robotToDisplay, currentHex);

                    }

                }

            }

        }
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
     * Highlights all neighbors one tile away from the
     */
    private void highLightNeighbors(Coordinate coordinate, Color color) {
        if (Gameboard.coordinates.contains(coordinate)) {
            ArrayList<Coordinate[]> neighbors = getNeighbors(coordinate, 2);

            for (Coordinate[] l : neighbors) {
                for (Coordinate c : l) {
                    System.out.println(c);
                    if (c != null) {
                        this.highLight(c, color);
                    }
                }
            }


        }
    }

    /**
     * Draws a circle with the point of the circle being in the center of the circle
     *
     * @param c      the coordinate that the circle needs to be drawn at.
     * @param radius The radius of the circle
     */
    @SuppressWarnings("unused")
    private void drawCenteredCircle(Graphics2D graphics2D, Coordinate c, int radius) {
        int x = c.x - radius / 2;
        int y = c.y - radius / 2;
        graphics2D.drawOval(x, y, radius, radius);
    }

    public Robot getCurrentRobot() {
        return currentRobot;
    }

    public void setCurrentRobot(Robot currentRobot) {
        this.currentRobot = currentRobot;
    }

    private int getCurrentGang() {
        return this.currentRobot.getGangID();
    }


    /**
     * The input class is used to detect input to the gameboard
     */
    private class Input implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = MouseInfo.getPointerInfo().getLocation().x - Game.this.getLocationOnScreen().x;
            int y = MouseInfo.getPointerInfo().getLocation().y - Game.this.getLocationOnScreen().y;

            Coordinate position = getKey(x, y);
            if (Gameboard.robotsOnCoordinate.containsKey(position)) {
                // TODO REMOVE THIS SHIZ
                Gameboard.robotsOnCoordinate.get(position).get(0).turn();
                //Gameboard.robotsOnCoordinate.get(position).get(0).move();
            }
            if (position != null) {
                // Debugging information
                // System.out.println("Mouse position: (" + x + ", " + y + ")");
                System.out.println("Corresponding key: (" + position.x + ", " + position.y + ")");
                Game.this.selectedHex = position;
                Game.this.repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        /**
         * Invoked when a mouse button has been released on a component.
         */
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        /**
         * Invoked when the mouse enters a component.
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * Invoked when the mouse exits a component.
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}