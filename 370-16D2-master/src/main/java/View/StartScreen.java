package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arianne on 2016-11-16.
 */
public class StartScreen extends JPanel {
    public JPanel start;
    private JButton InstructionsButton;
    private JButton StartButton;
    private JButton QuitButton;

    public StartScreen(JFrame f) {
        this.StartButton.addActionListener(new ActionListener() {
            /**
             * Open SetUpMenu screen on Start button pressed event
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the SetUpMenu screen
                f.setContentPane(new SetUpMenu(f).start);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.revalidate();
            }
        });
        this.QuitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when Quit button is pressed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Close the window
                System.exit(0);

            }
        });


    }
}
