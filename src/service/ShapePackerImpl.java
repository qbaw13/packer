package service;

import api.ShapePacker;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public class ShapePackerImpl extends Thread implements ShapePacker {

    private Dimension2D binSize;
    private List<Shape> shapes;
    private ItemsDistribution bestItemsDistribution;
    private Multimap<Integer, ItemsDistribution> allItemsDistribution;

    public ShapePackerImpl() {
        allItemsDistribution = ArrayListMultimap.create();
    }

    @Override
    public void run() {
//        List<Shape> shapes = new ArrayList<>();
//        ItemsDistribution itemsDistribution = new ItemsDistribution();
        sortShapesDescendingBySurfaceArea();

        for(int i=1; i<shapes.size(); i++) {
            System.out.println(calculateSurfaceArea(shapes.get(i)));
        }

    }

    private void insertShape(Shape shape, int shapeCount) {
//        ItemsDistribution itemsDistribution = new ItemsDistribution()
//        allItemsDistribution.put();
    }

    private List<Shape> sortShapesDescendingBySurfaceArea() {
        List<Shape> sortedShapes = new ArrayList<>();

        quickSort(0, shapes.size()-1);
        return sortedShapes;
    }

    private void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        Shape pivot = shapes.get(lowerIndex+(higherIndex-lowerIndex)/2);
        while (i <= j) {
            while (calculateSurfaceArea(shapes.get(i)) > calculateSurfaceArea(pivot)) {
                i++;
            }
            while (calculateSurfaceArea(shapes.get(j)) < calculateSurfaceArea(pivot)) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    private void exchangeNumbers(int i, int j) {
        Shape temp = shapes.get(i);
        shapes.set(i, shapes.get(j));
        shapes.set(j, temp);
    }

    private boolean checkIntersection(List<Shape> shapes, Shape shape) {
        for(int i=0; i<shapes.size(); i++) {
            if(((Path)Shape.intersect(shapes.get(i), shape)).getElements().size() <= 0) {
                return false;
            }
        }
        return true;
    }

    private double calculateSurfaceArea(Shape shape) {
        Bounds bounds = shape.getBoundsInLocal();
        return ((bounds.getMaxX() - bounds.getMinX()) * (bounds.getMaxY() - bounds.getMinY()));
    }

    @Override
    public void setBinSize(Dimension2D binSize) {
        this.binSize = binSize;
    }

    @Override
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public ItemsDistribution getBestItemsDistribution() {
        return bestItemsDistribution;
    }


}
