package obiektowe.projekt.pierwszy;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class World extends JFrame {
    Config config = new Config();

    public static void main(String[] args) {
        new World();


    }

    World() {
        super("ApohlloOne");
        setSize(1200, 1200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Map map = new Map();
        add(map.getJPanel());
        setVisible(true);

        while (true) {
            map.day();
//            this.pack();
            this.setVisible(true);
            try {
                TimeUnit.MILLISECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
