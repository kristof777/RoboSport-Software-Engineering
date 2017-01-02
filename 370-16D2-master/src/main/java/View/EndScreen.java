package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arianne on 2016-11-16.
 */
class EndScreen {
    public JPanel start;
    private JButton backButton;
    private JButton quitButton;
    private JButton saveStatsButton;

    public EndScreen(JFrame f) {
        this.backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                f.setContentPane(new StartScreen(f).start);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.revalidate();
                //f.validate();

            }
        });
        this.saveStatsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.quitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Close the window
                 */
                System.exit(0);
            }
        });
    }
}
