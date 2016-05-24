package utility;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import service.Block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kuba on 01.05.2016.
 */
public class ShapeCreator {

    public static List<Block> createBlocks(String blocksData) {
        List<Block> blocks = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new StringReader(blocksData));
        String line = null;
        try {
            while((line = bufReader.readLine()) != null ) {
                blocks.addAll(createBlocksFromLine(line));
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    private static List<Block> createBlocksFromLine(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, "x");
        int width = Integer.parseInt(stringTokenizer.nextToken());
        int height = Integer.parseInt(stringTokenizer.nextToken());
        Integer count = 1;
        if(stringTokenizer.hasMoreTokens()){
            count = Integer.parseInt(stringTokenizer.nextToken());
        }
        return createBlocks(width, height, count);
    }

    private static List<Block> createBlocks(int width, int height, Integer count) {
        List<Block> blocks = new ArrayList<>();
        for(int i=0; i<count; i++) {
            blocks.add(new Block(width, height));
        }
        return blocks;
    }

    public static int countLines(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        int lines = 1;
        int pos = 0;
        while ((pos = str.indexOf("\n", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }

}
