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
    private int furthestX;
    private int furthestY;

    public Node(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.furthestX = 0;
        this.furthestY = 0;
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
        this.furthestX = node.furthestX;
        this.furthestY = node.furthestY;

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

    public void setFurthestX(int furthestX) {
        this.furthestX = furthestX;
    }

    public void setFurthestY(int furthestY) {
        this.furthestY = furthestY;
    }

    public int getFurthestX() {
        return furthestX;
    }

    public int getFurthestY() {
        return furthestY;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", used=" + used +
                ", block=" + block +
                ", furthestX=" + furthestX +
                ", furthestY=" + furthestY +
                ", right=" + right +
                ", down=" + down +
                '}';
    }
}
