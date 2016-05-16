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
    ItemsDistribution previousDistribution;
    Double width;
    Double height;

    public ItemsDistribution(List<Shape> shapes, ItemsDistribution previousDistribution) {
        this.shapes = shapes;
        this.previousDistribution = previousDistribution;
        initHeight();
        initWidth();

    }

    public ItemsDistribution(List<Shape> shapes, ItemsDistribution previousDistribution, Double width, Double height) {
        this.shapes = shapes;
        this.previousDistribution = previousDistribution;
        this.width = width;
        this.height = height;
    }

    private void initWidth() {
        Double furthestX = 0d;
        for(Shape distributedShape : shapes) {
            Rectangle distributedRectangle = (Rectangle) distributedShape;
            if(distributedRectangle.getX() + distributedRectangle.getWidth() > furthestX) {
                width = distributedRectangle.getX() + distributedRectangle.getWidth();
                furthestX = distributedRectangle.getX() + distributedRectangle.getWidth();
            }
        }
    }

    private void initHeight() {
        Double furthestY = 0d;
        for(Shape distributedShape : shapes) {
            Rectangle distributedRectangle = (Rectangle) distributedShape;
            if(distributedRectangle.getY() + distributedRectangle.getHeight() > furthestY) {
                height = distributedRectangle.getY() + distributedRectangle.getHeight();
                furthestY = distributedRectangle.getY() + distributedRectangle.getHeight();
            }
        }
    }

    public Double getOccupiedSurfaceArea() {
        return width * height;
    }

    public List<Shape> getShapes() {
        return shapes;
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

    public List<Shape> shapesDeepCopy() {
        List<Shape> copiedShapes = new ArrayList<>();
        for(Shape shape : shapes) {
            Shape copiedShape = null;
            if(shape instanceof Rectangle){
                Rectangle rectangleToCopy = (Rectangle) shape;
                copiedShape = new Rectangle(rectangleToCopy.getX(), rectangleToCopy.getY(), rectangleToCopy.getWidth(), rectangleToCopy.getHeight());
            }
            copiedShapes.add(copiedShape);
        }
        return copiedShapes;
    }

    @Override
    public String toString() {
        String result = "[" + getOccupiedSurfaceArea() +"] ";
        return result += shapes.toString();
    }
}
