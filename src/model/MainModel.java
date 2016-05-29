package model;

import entity.Block;
import entity.BlocksDistribution;
import entity.Node;
import entity.Pair;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import service.*;
import utility.BlocksFactory;

import java.util.*;


/**
 * Created by Kuba on 01.05.2016.
 */
public class MainModel {

    Packer packer;

    public MainModel() {
        packer = new Packer();
    }


    public Packer packShapes(List<Block> blocks) {
        packer = new Packer();
        packer.setBlocks(blocks);
        packer.start();
        return packer;
    }

    public List<BlocksDistribution> fetchPackedShapes(List<Block> blocks) {
        List<BlocksDistribution> blocksDistributions = new ArrayList<>();

        for (int i = 0; i < blocks.size(); i++) { //ustawianie id/kolejności w pd
            blocks.get(i).setId(i + 1);
        }

        List<Node> minRoots = packer.findRootsWithMinArea();
        int index = 1;
        for (Node n : minRoots) { //dla kazdego korzenia z minimalna powierzchnia
            Map<Integer, Pair<Integer>> decisions = new HashMap<>();
            packer.getDecisions(n, decisions);
            List<Shape> shapes = new ArrayList<>();

            for (int j = 1; j <= decisions.size(); j++) { //wypisz decyzje
                Pair<Integer> decision = decisions.get(j);
                Shape shape = new Rectangle(decision.getFirst(), decision.getSecond(), blocks.get(j - 1).getWidth(), blocks.get(j - 1).getHeight());
                shapes.add(shape);
            }

            BlocksDistribution blocksDistribution = new BlocksDistribution(shapes);
            blocksDistributions.add(blocksDistribution);

            index++;
        }

        return blocksDistributions;
    }

    public String fetchDecisions(List<Block> blocks) {
        StringBuilder s_decisions = new StringBuilder("");

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).setId(i + 1);
        }

        s_decisions.append("min pole: " + packer.getMinimumOccupiedArea());
        s_decisions.append(System.getProperty("line.separator"));

        List<Node> minRoots = packer.findRootsWithMinArea();
        int index = 1;
        for (Node n : minRoots) {
            Map<Integer, Pair<Integer>> decisions = new HashMap<>();
            packer.getDecisions(n, decisions);

            s_decisions.append("Min ciąg decyzji nr: " + index);
            s_decisions.append(System.getProperty("line.separator"));

            for (int j = 1; j <= decisions.size(); j++) {
                Pair<Integer> decision = decisions.get(j);
                s_decisions.append(j + ": (" + blocks.get(j - 1).getWidth() + "," + blocks.get(j - 1).getHeight() + "): " + decision.getFirst() + "," + decision.getSecond());
                s_decisions.append(System.getProperty("line.separator"));
            }

            index++;
        }

        return s_decisions.toString();
    }

    public List<Block> getBlocks(String blocksData) {
        return BlocksFactory.createBlocks(blocksData);
    }

    public void sortBlocksDescending(List<Block> blocks) {
        Comparator<Block> comparator = (Block b1, Block b2) -> new Integer(Math.max(b2.getWidth(), b2.getHeight())).compareTo(Math.max(b1.getWidth(), b1.getHeight()));
        Collections.sort(blocks, comparator);
    }

    public void stopPacker() {
        if(packer.isRunning()){
            packer.cancel();
        }
    }

    public boolean checkEnteredBlocks(String text) {
        return BlocksFactory.checkBlocksCreationPossibility(text);
    }
}
