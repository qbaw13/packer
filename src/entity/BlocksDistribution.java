package entity;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public class BlocksDistribution {

    List<Shape> shapes;
    Double width;
    Double height;

    public BlocksDistribution(List<Shape> shapes) {
        this.shapes = shapes;
        initHeight();
        initWidth();

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

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public List<Shape> blocksDeepCopy() {
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
