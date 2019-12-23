package obiektowe.projekt.pierwszy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map {

    //brzegi mapy
    Vector2d lowerLeft;
    Vector2d upperRight;

//    //brzegi jungli
//    Vector2d jungleLowerLeft;
//    Vector2d jungleUpperRight;

    //Listy zwierząt i traw
    List<Animal> animals = new ArrayList<>();
    //    List<Grass> grasses = new ArrayList<>();
    java.util.Map<Vector2d, MapField> fields = new HashMap<>();
    //MapFieldSet emptyFields;

    Map() {
        Config config = new Config();
        int width = config.getIntConfig("width");
        int height = config.getIntConfig("height");
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields.put(new Vector2d(i, j), new MapField(new Vector2d(i, j)));
            }
        }

        int numberOfAnimals = config.getIntConfig("startAnimals");
        int startEnergy = config.getIntConfig("startEnergy");
        for (int i = 0; i < numberOfAnimals; i++) {
            try {
                Animal animal = new Animal(startEnergy, getRandomEmptyField(), this);

            } catch (Exception e) {
                //
            }
        }
    }

    public MapField getPositionForChild(Vector2d position) {
        Random random = new Random();
        int direction = random.nextInt(7);
        int newDirection = direction;
        MapField newField;
        do {
            newField = fields.get(this.borderTeleport(position.movedIntoDirection(newDirection)));
            newDirection++;
            if (newDirection > 7) {
                newDirection = 0;
            }
        } while (direction != newDirection && !newField.isEmpty());

        return newField;
    }


    public boolean isOccupied(Vector2d position) {
        return !fields.get(position).isEmpty();
    }

    public List<MapField> emptyFields() {
        return emptyFields(MapArea.MAP);
    }

    public List<MapField> emptyFields(MapArea area) {
        Stream<MapField> emptyFields = fields.values().stream().filter(MapField::isEmpty);
        ;
        if (area == MapArea.JUNGLE) {
            emptyFields = emptyFields.filter(x -> x.inJungle());
        }
        if (area == MapArea.SAVANNA) {
            emptyFields = emptyFields.filter(x -> !x.inJungle());
        }
        return emptyFields.collect(Collectors.toList());
    }

    public MapField getRandomEmptyField() throws Exception {
        return getRandomEmptyField(MapArea.MAP);
    }

    public MapField getRandomEmptyField(MapArea area) throws Exception {
        List<MapField> emptyFields = emptyFields(area);
        if (emptyFields.isEmpty())
            throw new Exception("nie ma miejsca");
        Random random = new Random();
        int index = random.nextInt(emptyFields.size())-1;
        MapField element = emptyFields.get(index);
        return element;
    }

    public JPanel getJPanel() {
        JPanel jPanel = new JPanel(new GridLayout(upperRight.x, upperRight.y, 1, 1));
        for (int i = 0; i < upperRight.x; i++) {
            for (int j = upperRight.y - 1; j >= 0; j--) {
                Vector2d pos = new Vector2d(i, j);
                MapField field = fields.get(pos);
                jPanel.add(field.getJPanel());
            }

        }

        return jPanel;
    }

    Vector2d borderTeleport(Vector2d position) {
        if (position.x < 0)
            position.x = upperRight.x + position.x;
        if (position.y < 0)
            position.y = upperRight.y + position.y;
        Vector2d vector2d = new Vector2d((position.x % upperRight.x), (position.y % upperRight.y));
        return vector2d;
    }


    void placeAnimal(Animal animal) {
        this.animals.add(animal);
    }

    void deadAnimal(Animal animal) {
        animal.field.removeAnimal(animal);
    }

    void removeDead() {
        for (Animal animal :
                animals.stream().filter(x->(x.alive && x.energy<1)).collect(Collectors.toList())) {
                deadAnimal(animal);
        }
    }

    void moveAnimals() {
        animals.forEach(Animal::move);
    }

    void eatGrasses() {
        fields.values().forEach(MapField::eat);
    }

    void sex() {
        fields.values().forEach(MapField::sex);
    }

    public void placeGrasses() {

        MapField randomEmptyField = null;
        try {
            randomEmptyField = this.getRandomEmptyField(MapArea.JUNGLE);
            randomEmptyField.grass = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            randomEmptyField = this.getRandomEmptyField(MapArea.SAVANNA);
            randomEmptyField.grass = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    void day() {
        removeDead();
        moveAnimals();
        eatGrasses();
        sex();
        placeGrasses();
        updateGUI();
    }

    private void updateGUI() {
        fields.values().forEach(MapField::updateField);
    }

}
