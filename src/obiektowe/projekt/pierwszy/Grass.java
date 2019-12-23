package obiektowe.projekt.pierwszy;

public class Grass implements IWorldMapElement{
    Vector2d position;
    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}
