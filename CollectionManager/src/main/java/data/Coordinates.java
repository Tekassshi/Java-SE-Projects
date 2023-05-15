package data;

/**
 * Class for containing "x" and "y" coordinates values.
 * */
public class Coordinates {

    private int x;
    private Long y;

    /**
     * @return "x" coordinate int value.
     * */
    public int getX() {
        return x;
    }

    /**
     * Setter method to set "x" coordinate value.
     * @param x coordinate int value
     * */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return "y" coordinate Long value.
     * */
    public Long getY() {
        return y;
    }

    /**
     * Setter method to set "y" coordinate value.
     * @param y coordinate Long value
     * */
    public void setY(Long y) {
        this.y = y;
    }
}
