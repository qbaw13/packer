package service;

import api.ShapePacker;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.geometry.Dimension2D;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import utility.ShapeSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public class ShapePackerImpl extends Thread implements ShapePacker {

    private Dimension2D binSize;
    private List<Shape> shapes;
    private ItemsDistribution bestItemsDistribution;
    private Multimap<Integer, ItemsDistribution> allItemsDistributions;

    public ShapePackerImpl() {
        allItemsDistributions = ArrayListMultimap.create();
    }

    @Override
    public void run() {
        shapes = ShapeSorter.sortShapesDescendingBySurfaceArea(shapes);
        initItemsDistribution();
        for(int i = 1; i < shapes.size(); i++) {
            Shape currentShape = shapes.get(i);
            List<ItemsDistribution> prevItemsDistributions = (List<ItemsDistribution>) allItemsDistributions.get(i - 1);
            for(int j = 0; j < prevItemsDistributions.size(); j++) {
                ItemsDistribution currentDistribution = prevItemsDistributions.get(j);
                insertShape(i, currentShape, currentDistribution);
                System.out.println("i: " + i + ", j: "+ j);
            }
        }
        chooseBestItemsDistribution();
    }

    private void initItemsDistribution() {
        List<Shape> initShapes = new ArrayList<>();
        initShapes.add(shapes.get(0));
        ItemsDistribution itemsDistribution = new ItemsDistribution(initShapes, null, 0d, 0d);
        allItemsDistributions.put(0, itemsDistribution);
    }

    private void insertShape(int shapeCount, Shape shape, ItemsDistribution itemsDistribution) {
        Double width = itemsDistribution.getWidth();
        Double height = itemsDistribution.getHeight();

        Rectangle rectangle = (Rectangle) shape;
        Rectangle tempRectangle = new Rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());

        for(int k = 0; k <= height; k++) {
            tempRectangle.setY(k);
            for(int l = 0; l <= width; l++) {
                tempRectangle.setX(l);
                if(!intersects(itemsDistribution.getShapes(), tempRectangle)) {
                    List<Shape> copiedShapes = itemsDistribution.shapesDeepCopy();
                    copiedShapes.add(tempRectangle);
                    ItemsDistribution nextItemsDistribution = new ItemsDistribution(copiedShapes, itemsDistribution);
                    allItemsDistributions.put(shapeCount, nextItemsDistribution);
                    tempRectangle = new Rectangle(tempRectangle.getX(), tempRectangle.getY(), tempRectangle.getWidth(), tempRectangle.getHeight());
                }
            }
        }
    }

    private void chooseBestItemsDistribution() {
        List<ItemsDistribution> itemsDistributions = (List<ItemsDistribution>) allItemsDistributions.get(allItemsDistributions.size());
        int bestDistributionIndex = 0;
        Double smallestSurfaceArea = itemsDistributions.get(0).getOccupiedSurfaceArea();
        for(int i = 0; i < itemsDistributions.size(); i++) {
            if(itemsDistributions.get(i).getOccupiedSurfaceArea() < smallestSurfaceArea) {
                smallestSurfaceArea = itemsDistributions.get(i).getOccupiedSurfaceArea();
                bestDistributionIndex = i;
            }
        }
        bestItemsDistribution = itemsDistributions.get(bestDistributionIndex);
        System.out.println("best");
        System.out.println(bestItemsDistribution.toString());
    }

    private boolean intersects(List<Shape> shapes, Shape shape) {
        for(int i = 0; i < shapes.size(); i++) {
            if(((Path)Shape.intersect(shapes.get(i), shape)).getElements().size() > 0) {
                return true;
            }
        }
        return false;
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
