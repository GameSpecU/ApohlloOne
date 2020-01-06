package obiektowe.projekt.pierwszy;

import javax.swing.*;

public class StatPanel extends JPanel {

    private Map map;
    private JLabel numberOfAnimals;
    private JLabel numberOfGrass;
    private JLabel avgEnergy;
    private JLabel avgChildren;
    private JLabel avgDeadAges;
    public StatPanel(Map map) {
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.map = map;
        numberOfAnimals = new JLabel();
        numberOfGrass= new JLabel();
        avgEnergy= new JLabel();
        avgChildren= new JLabel();
        avgDeadAges= new JLabel();
        this.add(numberOfAnimals);
        this.add(numberOfGrass);
        this.add(avgEnergy);
        this.add(avgChildren);
        this.add(avgDeadAges);
        this.updateStats();
    }

    public long numberOfAnimals() {
        return map.aliveAnimals().count();
    }
    public long numberOfGrass() {
        return this.map.fields.values().stream().filter(x -> x.grass).count();
    }

    public double avgEnergy() {
        return map.aliveAnimals().mapToInt(x->x.energy).summaryStatistics().getAverage();
    }

    public double avgDeadAges() {
        return map.deadAnimals().mapToInt(x -> x.age).summaryStatistics().getAverage();
    }

    public double avgChildren() {
        return map.aliveAnimals().mapToInt(x->x.children).summaryStatistics().getAverage();
    }

    public void updateStats() {
        this.avgChildren.setText("Średnia ilość dzieci: "+avgChildren());
        this.avgDeadAges.setText("Średnia długość życia: "+avgDeadAges());
        this.avgEnergy.setText("Średnia ilość energii: "+avgEnergy());
        this.numberOfGrass.setText("Ilość traw: "+numberOfGrass());
        this.numberOfAnimals.setText("Ilość żywych zwierząt: "+numberOfAnimals());
    }




}
