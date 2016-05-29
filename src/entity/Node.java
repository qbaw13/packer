package entity;

/**
 * Węzeł drzewa konstuowanego w algorytmie ustawiania bloków na płaszczyźnie
 * Reprezentuje obszar na płaszczyźnie
 */
public class Node {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean used; // czy w obszarze znajduje się prostokąt/blok
    private Node right; //pole na prawo (powstaje kiedy used==true)
    private Node down; //pole na dole (powstaje kiedy used==true)
    private Block block; //blok zajmujący obszar (!=null kiedy used==true)

    public Node() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        used = false;
        right = null;
        down = null;
    }

    public Node(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        used = false;
        right = null;
        down = null;
    }

    public Node(Node node) {
        this.x = node.x;
        this.y = node.y;
        this.height = node.height;
        this.width = node.width;
        this.used = node.used;
        this.block = node.block;

        if (node.right != null) this.right = new Node(node.right);
        if (node.down != null) this.down = new Node(node.down);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

}
