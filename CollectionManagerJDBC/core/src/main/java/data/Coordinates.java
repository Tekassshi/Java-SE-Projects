package data;

import java.io.Serializable;

/**
 * Class for containing "x" and "y" coordinates values.
 * */
public class Coordinates implements Serializable {

    private long x;
    private Long y;

    public Coordinates(long x, Long y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return "x" coordinate int value.
     * */
    public long getX() {
        return x;
    }

    /**
     * Setter method to set "x" coordinate value.
     * @param x coordinate int value
     * */
    public void setX(long x) {
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
