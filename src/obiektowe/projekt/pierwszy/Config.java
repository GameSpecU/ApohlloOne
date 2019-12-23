package obiektowe.projekt.pierwszy;

import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Config {
    private JSONObject parameters;

    Config() {
        try {
            parameters = (JSONObject) (new JSONParser()).parse(new FileReader("parameters.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Object getConfig(String key) {
        return parameters.get(key);
    }

    public int getIntConfig(String key) {
        return Math.toIntExact((long) parameters.get(key));
    }

    private Pair<Vector2d, Vector2d> jungleCorners() {
        int mapWidth = getIntConfig("width");
        int mapHeight = getIntConfig("height");
        double jungleRatio = (double) parameters.get("jungleRatio");
        int jungleWidth = (int) (mapWidth * jungleRatio);
        int jungleHeight = (int) (mapHeight * jungleRatio);
        int jungleLeft = (mapWidth - jungleWidth) / 2;
        int jungleLow = (mapHeight - jungleHeight) / 2;
        int jungleRight = jungleLeft + jungleWidth - 1;
        int jungleUp = jungleLow + jungleHeight - 1;

        return new Pair<>(new Vector2d(jungleLeft, jungleLow), new Vector2d(jungleRight, jungleUp));
    }

    public Vector2d getLowerLeftOfJungle() {
        return jungleCorners().getKey();
    }

    public Vector2d getUpperRightofJungle() {
        return jungleCorners().getValue();
    }

}
