package service;

public class Block {
    private int width;
    private int heigth;
    private int id;

    public Block(int width, int height) {
        this.width = width;
        this.heigth = height;
        id = -1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return heigth;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[w=" + width + ", h=" + heigth + "]";
    }

    public void setId(int id) {
        this.id = id;
    }
}
