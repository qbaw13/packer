package model;

import javafx.geometry.Dimension2D;
import service.*;

import java.util.*;


/**
 * Created by Kuba on 01.05.2016.
 */
public class MainModel {


    public ItemsDistribution packShapes(Dimension2D binSize, List<Block> blocks) {
//        ShapePackerImpl shapePacker = new ShapePackerImpl();
//        shapePacker.setBinSize(binSize);
//        shapePacker.setShapes(shapes);
//        shapePacker.start();
//        try {
//            shapePacker.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return shapePacker.getBestItemsDistribution();

        Comparator<Block> comparator = (Block b1, Block b2) -> new Integer(Math.max(b2.getWidth(), b2.getHeight())).compareTo(Math.max(b1.getWidth(), b1.getHeight()));
        Collections.sort(blocks, comparator);

        Packer packer = new Packer();
        packer.fit(blocks);

        for (int i = 0; i < blocks.size(); i++) { //ustawianie id/kolejno�ci w pd
            blocks.get(i).setId(i + 1);
        }

        List<Node> minRoots = packer.findRootsWithMinArea();
        int index = 1;
        for (Node n : minRoots) { //dla kazdego korzenia z minimalna powierzchnia
            Map<Integer, Pair<Integer>> decisions = new HashMap<>();
            packer.getDecisions(n, decisions); //pobierz ciąg decyzji w pd
            System.out.println("Min ciąg decyzji nr: " + index);
            for (int j = 1; j <= decisions.size(); j++) { //wypisz decyzje
                Pair<Integer> decision = decisions.get(j);
                System.out.println(j + ": (" + blocks.get(j - 1).getWidth() + "," + blocks.get(j - 1).getHeight() + "): " + decision.getFirst() + "," + decision.getSecond());
            }
            index++;
        }

        return null;

    }
}
