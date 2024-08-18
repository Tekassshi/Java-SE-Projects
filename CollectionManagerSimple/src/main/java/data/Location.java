package data;

/**
 * Class for containing "x", "y" and "z" coordinates values of location.
 * */
public class Location {

    private Float x;
    private Integer y;
    private Double z;

    /**
     * @return "x" coordinate Float value.
     * */
    public Float getX() {
        return x;
    }

    /**
     * Setter method to set "x" coordinate value.
     * @param x coordinate Float value
     * */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * @return "y" coordinate Integer value.
     * */
    public Integer getY() {
        return y;
    }

    /**
     * Setter method to set "y" coordinate value.
     * @param y coordinate Integer value
     * */
    public void setY(Integer y) {
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
