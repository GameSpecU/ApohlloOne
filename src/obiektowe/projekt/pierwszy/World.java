package obiektowe.projekt.pierwszy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class World  implements ActionListener {
    private JPanel mapPanel;
    private int status;//1=gra, 0=pauza
    Config config = new Config();

    World() {
        JFrame mainWindow = new JFrame("ApohlloOne");
        JFrame statWindow = new JFrame("statystyki");
        mainWindow.setSize(900, 900);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statWindow.setSize(300, 900);
        Map map = new Map();
//        JPanel topPanel = new JPanel(new BorderLayout());
        mapPanel = map.getJPanel();
//        mapPanel.setSize(900, 900);
//        mapPanel.setMaximumSize(new Dimension(900, 900));
        mainWindow.add(mapPanel);
        SidePanel sidePanel = new SidePanel(this);
        statWindow.add(sidePanel);
        statWindow.setVisible(true);
        mainWindow.setVisible(true);
        status = 1;
        while (true) {
            if (status == 1)
                frame(map);
            mainWindow.setVisible(true);
//            statWindow.setVisible(true);
        }

    }

    private void frame(Map map) {
        map.day();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new World();


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("start")) {
            status = 1;
        } else
        if (actionEvent.getActionCommand().equals("stop")) {
            status = 0;
        }
    }
}
