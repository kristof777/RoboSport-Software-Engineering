package View;

import Controller.SetupMenuController;
import Model.Gang;
import Model.Robot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("unchecked")
class SetUpMenuPopUp extends JFrame {

    private final SetupMenuController setUpMenuController;
    public JPanel start;
    private String selectedRobot1;
    private String selectedRobot2;
    private String selectedRobot3;
    private String selectedTeamId1;
    private String selectedTeamId2;
    private String selectedTeamId3;
    private JComboBox<String> devTeamCombo1;
    private JComboBox<String> robotNamesCombo1;
    private JButton backButton;
    private JButton nextButton;
    private JComboBox<String> devTeamCombo2;
    private JComboBox robotNamesCombo2;
    private JComboBox<String> devTeamCombo3;
    private JComboBox robotNamesCombo3;
    private JTextArea scoutStatsArea;
    private JTextArea sniperStatsArea;
    private JTextArea tankStatsArea;

    // When a software team is selected, the robot combo box will become populated with individual AI's
    // Once an individual AI is selected, the jtextarea's will display that AI's wins, losses, damage done,
    // and damage taken

    // Colours: red = 0, orange = 1, yellow = 2, green = 3, blue = 4, purple = 5

    SetUpMenuPopUp(JFrame f, SetUpMenu s, int colour, boolean isAI, SetupMenuController controller, Gang g) {

        // Disable User Editing of Stats text areas
        scoutStatsArea.setEditable(false);
        sniperStatsArea.setEditable(false);
        tankStatsArea.setEditable(false);

        //nextButton.setEnabled(false);

        // Initialize the Controller (create setUpMenuController in this class and set it to param)
        this.setUpMenuController = controller;

        //Call getDevTeams() function in SetUpMenuController, returns string array
        String[] devTeams = this.setUpMenuController.getDevTeams();

        // POPULATE ALL 3 DEV TEAM COMBO BOXES ON CONSTRUCTION WITH STRING ARRAY OF DEV TEAMS
        for (int i = 0; i < devTeams.length; i++) {
            this.devTeamCombo1.insertItemAt(devTeams[i], i);
        }
        for (int i = 0; i < devTeams.length; i++) {
            this.devTeamCombo2.insertItemAt(devTeams[i], i);
        }
        for (int i = 0; i < devTeams.length; i++) {
            this.devTeamCombo3.insertItemAt(devTeams[i], i);
        }

        // If gang g is not null, there was a previous selection - re-select those items
        if (g != null) {
            // Loop through all 3 robots in gang
            for (Robot curRobot : g.getRobots()) {
                switch (curRobot.getType()) {
                    case "Scout": {
                        // Search for devTeam to re-select
                        for (int i = 0; i < devTeamCombo1.getItemCount(); i++) {
                            if (devTeamCombo1.getItemAt(i).equals(curRobot.getTeamID())) {
                                devTeamCombo1.setSelectedItem(i);
                            }
                        }
                        // Storing selected devTeam
                        this.selectedTeamId1 = curRobot.getTeamID();
                        populateRobotsCombo(SetUpMenuPopUp.this.selectedTeamId1, 1);

                        // Searching for the robot name to re-select
                        for (int i = 0; i < robotNamesCombo1.getItemCount(); i++) {
                            if (robotNamesCombo1.getItemAt(i).equals(curRobot.getRobotID())) {
                                robotNamesCombo1.setSelectedItem(i);
                            }
                        }
                        // Storing the selected robot name
                        this.selectedRobot1 = curRobot.getRobotID();
                    }
                    case "Sniper": {
                        for (int i = 0; i < devTeamCombo2.getItemCount(); i++) {
                            if (devTeamCombo2.getItemAt(i).equals(curRobot.getTeamID())) {
                                devTeamCombo2.setSelectedItem(i);
                            }
                        }
                        this.selectedTeamId2 = curRobot.getTeamID();
                        populateRobotsCombo(SetUpMenuPopUp.this.selectedTeamId2, 2);

                        for (int i = 0; i < robotNamesCombo2.getItemCount(); i++) {
                            if (robotNamesCombo2.getItemAt(i).equals(curRobot.getRobotID())) {
                                robotNamesCombo2.setSelectedItem(i);
                            }
                        }
                        this.selectedRobot2 = curRobot.getRobotID();
                    }
                    case "Tank": {
                        for (int i = 0; i < devTeamCombo3.getItemCount(); i++) {
                            if (devTeamCombo3.getItemAt(i).equals(curRobot.getTeamID())) {
                                devTeamCombo3.setSelectedItem(i);
                            }
                        }
                        this.selectedTeamId3 = curRobot.getTeamID();
                        populateRobotsCombo(SetUpMenuPopUp.this.selectedTeamId3, 3);

                        for (int i = 0; i < robotNamesCombo3.getItemCount(); i++) {
                            if (robotNamesCombo3.getItemAt(i).equals(curRobot.getRobotID())) {
                                robotNamesCombo3.setSelectedItem(i);
                            }
                        }
                        this.selectedRobot3 = curRobot.getRobotID();
                    }
                }
            }
        }

        // SOFTWARE DEV TEAM SELECTION EVENTS IN DEV TEAM COMBO BOXES
        this.devTeamCombo1.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects a software development team from the first Dev Team combo box
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                SetUpMenuPopUp.this.selectedTeamId1 = SetUpMenuPopUp.this.devTeamCombo1.getSelectedItem().toString();
                // Call function to populate the robot names combo box associated with second the selected team and type
                SetUpMenuPopUp.this.populateRobotsCombo(SetUpMenuPopUp.this.selectedTeamId1, 1);
            }
        });
        this.devTeamCombo2.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects a software development team from the second Dev Team combo box
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                SetUpMenuPopUp.this.selectedTeamId2 = SetUpMenuPopUp.this.devTeamCombo2.getSelectedItem().toString();
                // Call function to populate the robot names combo box associated with the selected team and type
                SetUpMenuPopUp.this.populateRobotsCombo(SetUpMenuPopUp.this.selectedTeamId2, 2);
            }
        });
        this.devTeamCombo3.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects a software development team from the third Dev Team combo box
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                SetUpMenuPopUp.this.selectedTeamId3 = SetUpMenuPopUp.this.devTeamCombo3.getSelectedItem().toString();
                // Call function to populate the robot names combo box associated with the selected team and type
                SetUpMenuPopUp.this.populateRobotsCombo(SetUpMenuPopUp.this.selectedTeamId3, 3);

            }
        });

        // On robot selection events 1, 2, and 3
        this.robotNamesCombo1.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects a robot name from the first Robot Names combo box
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SetUpMenuPopUp.this.robotNamesCombo1.getSelectedItem() != null) {
                    SetUpMenuPopUp.this.selectedRobot1 = SetUpMenuPopUp.this.robotNamesCombo1.getSelectedItem().toString();
                    // Call fnc to populate stats of selected robot
                    SetUpMenuPopUp.this.populateStatsArea(SetUpMenuPopUp.this.selectedRobot1, 1);
                }
            }
        });
        this.robotNamesCombo2.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects a robot name from the second Robot Names combo box
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SetUpMenuPopUp.this.robotNamesCombo2.getSelectedItem() != null) {
                    SetUpMenuPopUp.this.selectedRobot2 = SetUpMenuPopUp.this.robotNamesCombo2.getSelectedItem().toString();
                    // Call fnc to populate stats of selected robot
                    SetUpMenuPopUp.this.populateStatsArea(SetUpMenuPopUp.this.selectedRobot2, 2);
                }
            }
        });
        this.robotNamesCombo3.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects a robot name from the third Robot Names combo box
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SetUpMenuPopUp.this.robotNamesCombo3.getSelectedItem() != null) {
                    SetUpMenuPopUp.this.selectedRobot3 = SetUpMenuPopUp.this.robotNamesCombo3.getSelectedItem().toString();
                    // Call fnc to populate stats of selected robot
                    SetUpMenuPopUp.this.populateStatsArea(SetUpMenuPopUp.this.selectedRobot3, 3);
                }
            }
        });

        // BACK AND NEXT BUTTONS
        this.backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when back button is pressed, navigates back to SetUpMenu view
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setContentPane(s.start);
                f.setVisible(true);
                f.revalidate();
            }
        });

        this.nextButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Next button is pressed, navigates to GameScreen
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check that there has been a selection for scout, sniper, and tank
                if (robotNamesCombo1.getSelectedItem() != null && robotNamesCombo2.getSelectedItem() != null &&
                        robotNamesCombo3.getSelectedItem() != null) {

                    // Get 3 selected robots
                    String scout = robotNamesCombo1.getSelectedItem().toString();
                    String sniper = robotNamesCombo2.getSelectedItem().toString();
                    String tank = robotNamesCombo3.getSelectedItem().toString();

                    String[] bots = new String[]{scout, sniper, tank};

                    // Call function to set up individual gang by its colour
                    int numGangs = setUpMenuController.makeGangByColour(bots, colour, isAI);

                    // If desiredNumPlayers == gangs array length
                    if (controller.getDesiredNumberPlayers() == numGangs) {
                        s.startButton.setEnabled(true);
                    }

                    f.setContentPane(s.start);
                    f.setVisible(true);
                    f.revalidate();
                }

            }
        });
    }

    /**
     * Populate robots combo boxes with the robots made by the selected software dev team
     *
     * @param teamId: ID of selected software dev team
     * @param type:   robot type (scout, sniper, or tank)
     */
    private void populateRobotsCombo(String teamId, int type) {

        if (type == 1) {
            robotNamesCombo1.removeAllItems();
            String[] robots = this.setUpMenuController.robotsByDevTeam(this.selectedTeamId1, "Scout");
            for (int i = 0; i < robots.length; i++) {
                this.robotNamesCombo1.insertItemAt(robots[i], i);
            }
        } else if (type == 2) {
            robotNamesCombo2.removeAllItems();
            String[] robots = this.setUpMenuController.robotsByDevTeam(this.selectedTeamId2, "Sniper");
            for (int i = 0; i < robots.length; i++) {
                this.robotNamesCombo2.insertItemAt(robots[i], i);
            }
        } else {
            robotNamesCombo3.removeAllItems();
            String[] robots = this.setUpMenuController.robotsByDevTeam(this.selectedTeamId3, "Tank");
            for (int i = 0; i < robots.length; i++) {
                this.robotNamesCombo3.insertItemAt(robots[i], i);
            }
        }
    }

    /**
     * Populate text areas with stats on selected robots
     *
     * @param robot string robot name
     * @param type  string of robot type, scout, sniper, or tank
     */
    private void populateStatsArea(String robot, int type) {

        // String array to hold the fields of the stats area
        String[] fields = {"Wins: ", "Losses: ", "Damage Taken: ", "Damage Done: "};

        if (type == 1) {
            // Empty stats area each time function is called and populate anew
            this.scoutStatsArea.setText(null);
            // Get stats from the appropriate robot and populate text field
            String[] stats = this.setUpMenuController.getStats(robot, this.selectedTeamId1);
            for (int i = 0; i < stats.length; i++) {
                this.scoutStatsArea.append(fields[i] + stats[i] + "\n");
            }
        } else if (type == 2) {
            this.sniperStatsArea.setText(null);
            String[] stats = this.setUpMenuController.getStats(robot, this.selectedTeamId2);
            for (int i = 0; i < stats.length; i++) {
                this.sniperStatsArea.append(fields[i] + stats[i] + "\n");
            }
        } else {
            this.tankStatsArea.setText(null);
            String[] stats = this.setUpMenuController.getStats(robot, this.selectedTeamId3);
            for (int i = 0; i < stats.length; i++) {
                this.tankStatsArea.append(fields[i] + stats[i] + "\n");
            }
        }
    }
}
