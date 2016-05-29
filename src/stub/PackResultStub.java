package stub;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import entity.BlocksDistribution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 16.05.2016.
 */
public class PackResultStub {

    public static BlocksDistribution blocksDistribution;

    static {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Rectangle(0, 0, 100, 100));
        shapes.add(new Rectangle(100, 0, 50, 80));
        shapes.add(new Rectangle(150, 0, 30, 70));
//        shapes.add(new Rectangle(100, 0, 20, 100));
//        shapes.add(new Rectangle(100, 20, 70, 70));
//        shapes.add(new Rectangle(100, 100, 100, 100));
//        shapes.add(new Rectangle(200, 100, 100, 100));

        blocksDistribution = new BlocksDistribution(shapes);
    }
}
