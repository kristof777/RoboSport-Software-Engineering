package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arianne on 2016-11-16.
 */
class PassTheController {
    public JPanel start;
    private JButton okButton;

    public PassTheController(JFrame f, GameScreen g) {
        this.okButton.addActionListener(new ActionListener() {
            /**
             * Reopen the GameScreen
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                f.setContentPane(g.start);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.revalidate();
                //f.validate();

            }
        });
    }
}
