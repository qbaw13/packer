package model;

import api.ShapePacker;
import javafx.geometry.Dimension2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import service.ItemsDistribution;
import service.ShapePackerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public class MainModel {


    public ItemsDistribution packShapes(Dimension2D binSize, List<Shape> shapes) {
        ShapePackerImpl shapePacker = new ShapePackerImpl();
        shapePacker.setBinSize(binSize);
        shapePacker.setShapes(shapes);
        shapePacker.start();
        try {
            shapePacker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shapePacker.getBestItemsDistribution();
    }
}
