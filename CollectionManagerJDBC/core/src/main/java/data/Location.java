package data;

import java.io.Serializable;

/**
 * Class for containing "x", "y" and "z" coordinates values of location.
 * */
public class Location implements Serializable {

    private Integer x;
    private Float y;
    private Double z;

    public Location(Integer x, Float y, Double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return "x" coordinate Float value.
     * */
    public Integer getX() {
        return x;
    }

    /**
     * Setter method to set "x" coordinate value.
     * @param x coordinate Float value
     * */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * @return "y" coordinate Integer value.
     * */
    public Float getY() {
        return y;
    }

    /**
     * Setter method to set "y" coordinate value.
     * @param y coordinate Integer value
     * */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * @return "z" coordinate Double value.
     * */
    public Double getZ() {
        return z;
    }

    /**
     * Setter method to set "z" coordinate value.
     * @param z coordinate Double value
     * */
    public void setZ(Double z) {
        this.z = z;
    }
}
