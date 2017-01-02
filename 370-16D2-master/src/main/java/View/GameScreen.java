package View;

import Controller.GameMaster;
import Model.Gang;
import Model.Robot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Michael on 2016-11-15.
 * <p>
 * Note: As I did not know how to implement a hexagon
 * coordinate system I followed the guide provided at:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
class GameScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Game game;
    public JPanel start;
    private JButton moveButton;
    private JButton attackButton;
    private JButton forfeitButton;
    private JButton endTurn;
    private JButton turnButton;
    private GameMaster gameMaster;

    /**
     * Constructs a new GameScreen JPanel on the same JFrame used for the rest of the system
     */
    public GameScreen(JFrame f, Gang[] gangs) {
        game = new Game();

        try {
            gameMaster = new GameMaster(gangs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameScreen g = this;

        this.turnButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentRobot().turn();
            }
        });

        this.moveButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentRobot().move();
            }
        });

        this.forfeitButton.addActionListener(new ActionListener() {
            /**
             * TODO CHANGE THIS SHIT LATER
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setContentPane(new EndScreen(f).start);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.revalidate();
            }
        });

        this.endTurn.addActionListener(new ActionListener() {
            /**
             * Open PassTheControls Screen
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                f.setContentPane(new PassTheController(f, g).start);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.revalidate();
            }
        });
    }

    public void runGame() {
        while (!gameMaster.gameOver()) {
            Robot currentRobot = GameMaster.getNextRobot();
            game.setCurrentRobot(currentRobot);

        }
    }
}
