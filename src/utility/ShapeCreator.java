package utility;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kuba on 01.05.2016.
 */
public class ShapeCreator {

    public static List<Shape> createShapes(String shapesData) {
        List<Shape> shapes = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new StringReader(shapesData));
        String line = null;
        try {
            while((line = bufReader.readLine()) != null ) {
                shapes.addAll(createShapesFromLine(line));
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shapes;
    }

    // TODO
    private static List<Shape> createShapesFromLine(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, "x");
        Double width = Double.parseDouble(stringTokenizer.nextToken());
        Double height = Double.parseDouble(stringTokenizer.nextToken());
        Integer count = 1;
        if(stringTokenizer.hasMoreTokens()){
            count = Integer.parseInt(stringTokenizer.nextToken());
        }
        return createRectangles(width, height, count);
    }

    private static List<Shape> createRectangles(Double width, Double height, Integer count) {
        List<Shape> shapes = new ArrayList<>();
        for(int i=0; i<count; i++) {
            shapes.add(new Rectangle(width, height));
        }
        return shapes;
    }

    public static int countLines(String str) {
        if(str == null || str.isEmpty())
        {
            return 0;
        }
        int lines = 1;
        int pos = 0;
        while ((pos = str.indexOf("\n", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }

}
