package utility;

import entity.Block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kuba on 01.05.2016.
 */
public class BlocksFactory {


    public static boolean checkBlocksCreationPossibility(String blocksData) {
        if(blocksData.isEmpty()) {
            return false;
        }

        BufferedReader bufReader = new BufferedReader(new StringReader(blocksData));
        try {
            String line;
            while((line = bufReader.readLine()) != null ) {
                if(!checkLineCorrectness(line)) {
                    bufReader.close();
                    return false;
                }
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static List<Block> createBlocks(String blocksData) {
        List<Block> blocks = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new StringReader(blocksData));
        try {
            String line;
            while((line = bufReader.readLine()) != null ) {
                blocks.addAll(createBlocksFromLine(line));
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    private static boolean checkLineCorrectness(String line) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, "x");
        if(!shouldHasTwoOrThreeTokens(stringTokenizer.countTokens())) {
            return false;
        }
        while (stringTokenizer.hasMoreTokens()) {
            if(!isInteger(stringTokenizer.nextToken())) {
                return false;
            }
        }
        return true;
    }

    private static boolean shouldHasTwoOrThreeTokens(int tokenCount) {
        return tokenCount == 2 || tokenCount == 3;
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

    private static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

}
