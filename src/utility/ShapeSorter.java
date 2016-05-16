package utility;

import javafx.geometry.Bounds;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 04.05.2016.
 */
public class ShapeSorter {

    public static List<Shape> sortShapesDescendingBySurfaceArea(List<Shape> shapes) {
        List<Shape> sortedShapes = new ArrayList<>();

        return quickSort(0, shapes.size()-1, shapes);
    }

    private static List<Shape> quickSort(int lowerIndex, int higherIndex, List<Shape> shapes) {
        int i = lowerIndex;
        int j = higherIndex;
        Shape pivot = shapes.get(lowerIndex + (higherIndex - lowerIndex) / 2);
        while (i <= j) {
            while (calculateSurfaceArea(shapes.get(i)) > calculateSurfaceArea(pivot)) {
                i++;
            }
            while (calculateSurfaceArea(shapes.get(j)) < calculateSurfaceArea(pivot)) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j, shapes);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j, shapes);
        if (i < higherIndex)
            quickSort(i, higherIndex, shapes);

        return shapes;
    }

    private static void exchangeNumbers(int i, int j, List<Shape> shapes) {
        Shape temp = shapes.get(i);
        shapes.set(i, shapes.get(j));
        shapes.set(j, temp);
    }

    private static double calculateSurfaceArea(Shape shape) {
        Bounds bounds = shape.getBoundsInLocal();
        return ((bounds.getMaxX() - bounds.getMinX()) * (bounds.getMaxY() - bounds.getMinY()));
    }
}
