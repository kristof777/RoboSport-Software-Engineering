package View;

import Controller.SetupMenuController;
import Model.Gameboard;
import Model.Gang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SetUpMenu extends JPanel {
    private final SetupMenuController setUpMenuController;
    public JPanel start;
    public JButton startButton;
    private JButton redButton;
    private JButton orangeButton;
    private JButton yellowButton;
    private JButton greenButton;
    private JButton blueButton;
    private JButton purpleButton;
    private JRadioButton isAIorange;
    private JRadioButton isAIyellow;
    private JRadioButton isAIgreen;
    private JRadioButton isAIred;
    private JRadioButton isAIblue;
    private JRadioButton isAIpurple;
    private JButton backButton;
    private JRadioButton twoPlayers;
    private JRadioButton threePlayers;
    private JRadioButton sixPlayers;

    SetUpMenu(JFrame f) {

        f.setTitle("Game Set Up");
        SetUpMenu setUpMenu = this;

        int numGangs = 0;
        int numPlayers = 0;

        // Initialize the Controller
        this.setUpMenuController = new SetupMenuController();

        // NUMBER OF PLAYERS RADIO BUTTONS
        twoPlayers.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects two player game
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                setUpMenuController.setDesiredNumberPlayers(2);

                redButton.setEnabled(true);
                orangeButton.setEnabled(false);
                yellowButton.setEnabled(false);
                greenButton.setEnabled(true);
                blueButton.setEnabled(false);
                purpleButton.setEnabled(false);

                isAIred.setEnabled(true);
                isAIorange.setEnabled(false);
                isAIyellow.setEnabled(false);
                isAIgreen.setEnabled(true);
                isAIblue.setEnabled(false);
                isAIpurple.setEnabled(false);

                Gameboard gameboard = new Gameboard(5);
            }
        });

        threePlayers.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects three player game
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                setUpMenuController.setDesiredNumberPlayers(3);

                redButton.setEnabled(true);
                orangeButton.setEnabled(false);
                yellowButton.setEnabled(true);
                greenButton.setEnabled(false);
                blueButton.setEnabled(true);
                purpleButton.setEnabled(false);

                isAIred.setEnabled(true);
                isAIorange.setEnabled(false);
                isAIyellow.setEnabled(true);
                isAIgreen.setEnabled(false);
                isAIblue.setEnabled(true);
                isAIpurple.setEnabled(false);

                Gameboard gameboard = new Gameboard(7);
            }
        });

        sixPlayers.addActionListener(new ActionListener() {
            /**
             * Invoked when user selects six player game
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                setUpMenuController.setDesiredNumberPlayers(6);

                redButton.setEnabled(true);
                orangeButton.setEnabled(true);
                yellowButton.setEnabled(true);
                greenButton.setEnabled(true);
                blueButton.setEnabled(true);
                purpleButton.setEnabled(true);

                isAIred.setEnabled(true);
                isAIorange.setEnabled(true);
                isAIyellow.setEnabled(true);
                isAIgreen.setEnabled(true);
                isAIblue.setEnabled(true);
                isAIpurple.setEnabled(true);

                Gameboard gameboard = new Gameboard(7);
            }
        });


        // TEAM COLOUR BUTTONS
        this.redButton.addActionListener(new ActionListener() {
            /**
             * Open SetUpMenuPopUp
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Find out if gang is AI
                boolean isAI = false;
                if (isAIred.isSelected()) {
                    isAI = true;
                }

                // Open up SetUpMenuPopUp in new window and disable current window
                SetUpMenuPopUp s = new SetUpMenuPopUp(f, setUpMenu, 0, isAI, setUpMenuController, setUpMenuController.getGangByColour(0));
                s.setContentPane(s.start);
                s.setPreferredSize(new Dimension(600, 700));
                s.setVisible(true);
                s.revalidate();
                s.pack();
                f.invalidate();
            }
        });

        this.orangeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Purple button is pressed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Find out if gang is AI
                boolean isAI = false;
                if (isAIorange.isSelected()) {
                    isAI = true;
                }
                // Open up SetUpMenuPopUp in new window and disable current window
                SetUpMenuPopUp s = new SetUpMenuPopUp(f, setUpMenu, 1, isAI, setUpMenuController, setUpMenuController.getGangByColour(1));
                s.setContentPane(s.start);
                s.setPreferredSize(new Dimension(600, 700));
                s.setVisible(true);
                s.revalidate();
                s.pack();
                f.invalidate();

            }
        });
        this.yellowButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Blue button is pressed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Find out if gang is AI
                boolean isAI = false;
                if (isAIyellow.isSelected()) {
                    isAI = true;
                }

                // Open up SetUpMenuPopUp in new window and disable current window
                SetUpMenuPopUp s = new SetUpMenuPopUp(f, setUpMenu, 2, isAI, setUpMenuController, setUpMenuController.getGangByColour(2));
                s.setContentPane(s.start);
                s.setPreferredSize(new Dimension(600, 700));
                s.setVisible(true);
                s.revalidate();
                s.pack();
                f.invalidate();

            }
        });
        this.greenButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Green button is pressed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Find out if gang is AI
                boolean isAI = false;
                if (isAIgreen.isSelected()) {
                    isAI = true;
                }

                // Open up SetUpMenuPopUp in new window and disable current window
                SetUpMenuPopUp s = new SetUpMenuPopUp(f, setUpMenu, 3, isAI, setUpMenuController, setUpMenuController.getGangByColour(3));
                s.setContentPane(s.start);
                s.setPreferredSize(new Dimension(600, 700));
                s.setVisible(true);
                s.revalidate();
                s.pack();
                f.invalidate();

            }
        });
        this.blueButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Yellow button is pressed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Find out if gang is AI
                boolean isAI = false;
                if (isAIblue.isSelected()) {
                    isAI = true;
                }

                // Open up SetUpMenuPopUp in new window and disable current window
                SetUpMenuPopUp s = new SetUpMenuPopUp(f, setUpMenu, 4, isAI, setUpMenuController, setUpMenuController.getGangByColour(4));
                s.setContentPane(s.start);
                s.setPreferredSize(new Dimension(600, 700));
                s.setVisible(true);
                s.revalidate();
                s.pack();
                f.invalidate();

            }
        });
        this.purpleButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Orange button is pressed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Find out if gang is AI
                boolean isAI = false;
                if (isAIpurple.isSelected()) {
                    isAI = true;
                }

                // Open up SetUpMenuPopUp in new window and disable current window
                SetUpMenuPopUp s = new SetUpMenuPopUp(f, setUpMenu, 5, isAI, setUpMenuController, setUpMenuController.getGangByColour(5));
                s.setContentPane(s.start);
                s.setPreferredSize(new Dimension(600, 700));
                s.setVisible(true);
                s.revalidate();
                s.pack();
                f.invalidate();

            }
        });

        /* RADIO BUTTONS */
        this.isAIorange.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        this.isAIyellow.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        this.isAIgreen.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.isAIred.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.isAIblue.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.isAIpurple.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        /* START AND BACK BUTTONS */
        this.startButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Start button is pressed, navigates to SetUpMenuPopUp view
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // If there are a correct number of gangs selected, start the game
                try {
                    Gang[] gangs = setUpMenuController.makeAllGangs();
                    f.setContentPane(new GameScreen(f, gangs).start);
                    f.setVisible(true);
                    f.revalidate();
                } catch (Exception ex) {
                    // Print an error message to the user.. figure out the details
                }
            }
        });
        this.backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Back button is pressed, navigates back to StartScreen
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                f.setContentPane(new StartScreen(f).start);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.revalidate();
            }
        });
    }
}
