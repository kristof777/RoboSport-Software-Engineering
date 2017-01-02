import Model.Gameboard;
import Model.Robot;
import Model.StatsLogger;
import View.Game;

import javax.swing.*;


/**
 * Created by Michael on 2016-12-05.
 */
class Test {

    private final String tankJson = "{ \"team\" : \"testTeam\"\n" +
            ", \"class\" : \"Tank\"\n" +
            ", \"name\" : \"test tank\"\n" +
            ", \"matches\" : 0\n" +
            ", \"wins\" : 0\n" +
            ", \"losses\" : 0\n" +
            ", \"executions\" : 0\n" +
            ", \"lived\" : 0\n" +
            ", \"died\" : 0\n" +
            ", \"absorbed\" : 0\n" +
            ", \"killed\" : 0\n" +
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
            "] }";
    private final String sniperJson = "{ \"team\" : \"testTeam\"\n" +
            ", \"class\" : \"Sniper\"\n" +
            ", \"name\" : \"test sniper\"\n" +
            ", \"matches\" : 0\n" +
            ", \"wins\" : 0\n" +
            ", \"losses\" : 0\n" +
            ", \"executions\" :0\n" +
            ", \"lived\" : 0\n" +
            ", \"died\" : 0\n" +
            ", \"absorbed\" : 0\n" +
            ", \"killed\" : 0\n" +
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
            "] }";
    private final String scoutJson = "{ \"team\" : \"testTeam\"\n" +
            ", \"class\" : \"Scout\"\n" +
            ", \"name\" : \"test scout\"\n" +
            ", \"matches\" : 0\n" +
            ", \"wins\" : 0\n" +
            ", \"losses\" : 0\n" +
            ", \"executions\" : 0\n" +
            ", \"lived\" : 0\n" +
            ", \"died\" : 0\n" +
            ", \"absorbed\" : 0\n" +
            ", \"killed\" : 0\n" +
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
            "] }";
    public Test() {
        new Gameboard(7);

        try {

            // initializing tanks
            Robot redTank = new Robot(this.tankJson, 0);
//            Robot redScout = new Robot(this.scoutJson, 0);
//            Robot redSniper = new Robot(this.sniperJson, 0);

            Robot orangeTank = new Robot(this.tankJson, 1);
//            Robot orangeScout = new Robot(this.scoutJson, 1);
//            Robot orangeSniper = new Robot(this.sniperJson, 1);

            Robot yellowTank = new Robot(this.tankJson, 2);
//            Robot yellowScout = new Robot(this.scoutJson, 2);
//            Robot yellowSniper = new Robot(this.sniperJson, 2);

            Robot greenTank = new Robot(this.tankJson, 3);
//            Robot greenScout = new Robot(this.scoutJson, 3);
//            Robot greenSniper = new Robot(this.sniperJson, 3);

            Robot blueTank = new Robot(this.tankJson, 4);
//            Robot blueScout = new Robot(this.scoutJson, 4);
//            Robot blueSniper = new Robot(this.sniperJson, 4);

            Robot purpleTank = new Robot(this.tankJson, 5);
//            Robot purpleScout = new Robot(this.scoutJson, 5);
//            Robot purpleSniper = new Robot(this.sniperJson, 5);
            Robot[] robots = new Robot[18];
            robots[0] = redTank;
            robots[1] = orangeTank;
            robots[2] = yellowTank;
            robots[3] = greenTank;
            robots[4] = blueTank;
            robots[5] = purpleTank;


            new StatsLogger(robots);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.createGUI();
        }

    }

    private void createGUI() {
        // Instantiate the start screen
        JFrame frame = new JFrame("Game View");
        frame.setContentPane(new Game());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
