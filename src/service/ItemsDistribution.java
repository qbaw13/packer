package service;

import javafx.scene.shape.Shape;

import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public class ItemsDistribution {

    List<Shape> shapes;
    Double surfaceArea;
    Integer previousDistribution;

    public ItemsDistribution(List<Shape> shapes, Double surfaceArea, Integer previousDistribution) {
        this.shapes = shapes;
        this.surfaceArea = surfaceArea;
        this.previousDistribution = previousDistribution;
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

    public Integer getPreviousDistribution() {
        return previousDistribution;
    }

    public void setPreviousDistribution(Integer previousDistribution) {
        this.previousDistribution = previousDistribution;
    }
}
