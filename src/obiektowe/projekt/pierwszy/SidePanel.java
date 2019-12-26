package obiektowe.projekt.pierwszy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel implements ActionListener {
    JButton pauseButton;
    World world;


    public SidePanel(World world) {
        super();
        this.world = world;
        this.setPreferredSize(new Dimension(200, 1000));
        this.add(getPauseButton());

    }


    private JButton getPauseButton() {
        pauseButton = new JButton("stop");
        pauseButton.addActionListener(this);
        pauseButton.addActionListener(world);
        return pauseButton;
    }

    public void pauseButtonPressed() {
        if (pauseButton.getText().equals("stop")) {
            pauseButton.setText("start");
        } else if (pauseButton.getText().equals("start")) {
            pauseButton.setText("stop");
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(pauseButton)) {
            pauseButtonPressed();
        }

        this.setVisible(true);
    }
}
