package obiektowe.projekt.pierwszy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapField {
    Vector2d position;
    List<Animal> animals;
    boolean grass;
    JPanel jPanel;


    MapField(Vector2d position) {
        this.position = position;
        this.grass = false;
        this.animals = new ArrayList<>(); // Lista zwierząt na danym polu
    }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            MapField mapField = (MapField) o;
            result = position.equals(mapField.position);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }


    public boolean haveAnimals() {
        return !animals.isEmpty();
    }


    public boolean isEmpty() {
        return (!haveAnimals() && !grass);
    }

    public boolean inJungle() {
        Config config = new Config();
        return this.position.between(
                config.getLowerLeftOfJungle(),
                config.getUpperRightofJungle());
    }


    public void sex() {
        this.animals.sort(Animal::compareTo);
        if (this.animals.size() < 2)
            return;
        Config config = new Config();
        if (this.animals.get(1).energy > 0.5 * config.getIntConfig("startEnergy")) {
            this.animals.get(0).sex(this.animals.get(1));
        }
    }

    public void eat() {
        if (grass && !animals.isEmpty()) {
            this.animals.sort(Animal::compareTo);
            int maxEnergy = this.animals.get(0).energy;
            int count = 1;
            for (int i = 1; i < animals.size(); i++) {
                if (animals.get(i).energy == maxEnergy)
                    count++;
                else break;
            }
            splitGrass(count);
            this.grass = false;
            return;
        }
    }

    private void splitGrass(int countOfAnimals) {
        Config config = new Config();
        int grassEnergy = config.getIntConfig("plantEnergy");
        int splittedEnergy = (int) Math.floor((double) grassEnergy / (double) countOfAnimals);
        for (int i = 0; i < countOfAnimals; i++) {
            animals.get(i).energy += splittedEnergy;
            grassEnergy -= splittedEnergy;
        }
        int i = 0;
        while (grassEnergy > 0) {
            animals.get(i).energy += 1;
            grassEnergy--;
        }
    }

    public void placeAnimal(Animal animal) {
        this.animals.add(animal);
        this.jPanel.add(animal.getJButton());
    }

    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
        this.jPanel.remove(animal.jButton);

    }

    public JPanel getJPanel() {

        jPanel = new JPanel(new GridLayout(
                4,
                4,
                1, 1));

        updateField();
        return jPanel;
    }

    public void updateField() {
        jPanel.revalidate();
        jPanel.repaint();
        if (grass)
            jPanel.setBackground(Color.green);
        else
            jPanel.setBackground(Color.gray);

    }


}
