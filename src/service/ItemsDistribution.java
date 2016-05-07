package service;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public class ItemsDistribution {

    List<Shape> shapes;
    Double surfaceArea;
    ItemsDistribution previousDistribution;
    Double width;
    Double height;

    public ItemsDistribution(List<Shape> shapes, Double surfaceArea, ItemsDistribution previousDistribution) {
        this.shapes = shapes;
        this.surfaceArea = surfaceArea;
        this.previousDistribution = previousDistribution;
    }

    public ItemsDistribution(List<Shape> shapes, Double surfaceArea, ItemsDistribution previousDistribution, Double width, Double height) {
        this.shapes = shapes;
        this.surfaceArea = surfaceArea;
        this.previousDistribution = previousDistribution;
        this.width = width;
        this.height = height;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public ItemsDistribution getPreviousDistribution() {
        return previousDistribution;
    }

    public void setPreviousDistribution(ItemsDistribution previousDistribution) {
        this.previousDistribution = previousDistribution;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    // TODO do it generic (not only for rectangles)
    public ItemsDistribution deepCopy() {
        List<Shape> copiedShapes = new ArrayList<>();

        for(Shape shape : shapes) {
            Rectangle rectangleToCopy = (Rectangle) shape;
            Shape copiedRectangle = new Rectangle(rectangleToCopy.getX(), rectangleToCopy.getY(), rectangleToCopy.getWidth(), rectangleToCopy.getHeight());
            copiedShapes.add(copiedRectangle);
        }
        return new ItemsDistribution(copiedShapes, surfaceArea.doubleValue(), previousDistribution, width.doubleValue(), height.doubleValue());
    }

    @Override
    public String toString() {
        String result = "[" + surfaceArea +"] ";
        return result += shapes.toString();
    }
}
