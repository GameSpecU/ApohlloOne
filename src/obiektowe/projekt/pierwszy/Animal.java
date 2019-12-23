package obiektowe.projekt.pierwszy;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Animal implements IWorldMapElement, Comparable {
    //todo:map

//    private ArrayList<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();

    //pozycja
    public int id;
    public boolean alive;

    public Vector2d getPosition() {
        return position;
    }

    private Map map;
    private Vector2d position;
    private int direction;
    MapField field;
    JButton jButton;

    //geny
    private int[] genes;

    //energia
    public int energy;

    //Konstruktor
    Animal(int startEnergy, MapField field, Map map) {
        this(map);
        this.position = field.position;
        this.field = field;
        field.placeAnimal(this);
        this.energy = startEnergy;
        this.position = position;
    }

    Animal(Map map) {
        Random random = new Random();
        this.genes = new int[32];
        this.randomGenes();
        Config config = new Config();
        this.energy = config.getIntConfig("startEnergy");
        this.direction = random.nextInt(7);
        this.map = map;
        map.placeAnimal(this);
        this.alive = true;
        this.jButton = new JButton();

    }

    public void sex(Animal partner) {
        Animal baby = new Animal(map);//Robi się dziecko
        Random random = new Random();

        //podział genów rodziców
        int firstCut = random.nextInt(31);
        int secondCut = (random.nextInt(31 - firstCut) + 1) % 31;

        //Przybranie genów
        baby.genes = Arrays.copyOf(this.genes, 32);//Od ojca
        System.arraycopy(partner.genes, firstCut + 1, baby.genes, firstCut + 1, secondCut);//Od matki
        baby.fixGenes();

        //pozycja na mapie i umieszczenie
        baby.direction = random.nextInt(7);
        MapField fieldForBaby = map.getPositionForChild(this.position);
        fieldForBaby.placeAnimal(baby);
        baby.position = fieldForBaby.position;
        baby.field = fieldForBaby;

        //przydział energii
        baby.energy = (int) (this.energy * 0.25);
        this.energy -= (int) (this.energy * 0.25);
        baby.energy += (int) (partner.energy * 0.25);
        partner.energy -= (int) (partner.energy * 0.25);


    }

    void randomGenes() {
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            genes[i] = random.nextInt(7);
        }
        fixGenes();
    }

    void fixGenes() {
        if (areGenesFixed())
            return;
        int[] numberOfGenomes = numberOfGenomes();
        for (int i = 0; i < 8; i++) {
            if (numberOfGenomes[i] > 0)
                continue;
            fixGene(i);
        }

        Arrays.sort(this.genes);
    }

    void fixGene(int genome) {
        int[] numberOfGenomes = numberOfGenomes();
        Random random = new Random();
        int key;
        do {
            key = random.nextInt(31);
        } while (numberOfGenomes[this.genes[key]] < 2);
        this.genes[key] = genome;
    }

    private int[] numberOfGenomes() {
        int[] genomes = new int[8];
        Arrays.fill(genomes, 0);
        for (int gene : this.genes) {
            genomes[gene]++;
        }
        return genomes;
    }

    private boolean areGenesFixed() {
        return IntStream.of(numberOfGenomes()).allMatch((x -> x > 0));
    }

    @Override
    public int compareTo(Object o) {
        Animal animal = (Animal) o;
        return Integer.compare(animal.energy, this.energy);


    }

    void move() {
        this.turn();
        Vector2d oldPosition = this.position;
        Vector2d newPosition = oldPosition.movedIntoDirection(this.direction);
        newPosition = map.borderTeleport(newPosition);
        this.position = newPosition;
        positionChanged();
    }

    void turn() {
        Random random = new Random();
        this.direction += genes[random.nextInt(31)];
        this.direction %= 7;
    }

    //    void addObserver(IPositionChangeObserver observer) {
//        observers.add(observer);
//    }
//
//    void removeObserver(IPositionChangeObserver observer) {
//        observers.remove(observer);
//    }
//
    void positionChanged() {


        MapField mapField = map.fields.get(this.position);
        mapField.placeAnimal(this);

        this.field.removeAnimal(this);

        this.field = mapField;
    }


//    public JButton getJButton() {
//        updateButton();
//        return jButton;
//    }

    public JButton getJButton() {
        jButton.setBackground(energyColor());
        jButton.setText("");
        jButton.setMaximumSize(new Dimension(2, 2));
        return jButton;
    }

    public Color energyColor() {
        if (this.energy == 0) {
            return Color.black;
        }
        if (this.energy == 1) {
            return Color.red;
        }
        if (this.energy < 4) {
            return Color.orange;
        }
        if (this.energy < 8) {
            return Color.yellow;
        }
        if (this.energy < 12) {
            return Color.green;
        }
        return Color.BLUE;
    }
}
