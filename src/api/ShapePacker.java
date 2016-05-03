package api;

import javafx.geometry.Dimension2D;
import javafx.scene.shape.Shape;
import service.Bin;
import service.ItemsDistribution;

import java.util.List;

/**
 * Created by Kuba on 01.05.2016.
 */
public interface ShapePacker {

    void setBinSize(Dimension2D binSize);

    void setShapes(List<Shape> shapes);

    ItemsDistribution getBestItemsDistribution();


//    Shape[] start();
//
//    void stop();

}
