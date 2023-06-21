package data;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class TableViewPerson {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty name = new SimpleStringProperty();

    private java.time.ZonedDateTime creationDate;

    private IntegerProperty height = new SimpleIntegerProperty();
    private FloatProperty weight = new SimpleFloatProperty();
    private Color eyeColor;
    private Country nationality;

    private StringProperty username = new SimpleStringProperty();

    public LongProperty xCoord = new SimpleLongProperty();
    public LongProperty yCoord = new SimpleLongProperty();

    public IntegerProperty xLooc = new SimpleIntegerProperty();
    public FloatProperty yLooc = new SimpleFloatProperty();
    public DoubleProperty zLooc = new SimpleDoubleProperty();

    public long getId() {
        return id.getValue();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getHeight() {
        return height.getValue();
    }

    public void setHeight(Integer height) {
        this.height.set(height);
    }

    public Float getWeight() {
        return weight.getValue();
    }

    public void setWeight(Float weight) {
        this.weight.set(weight);
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public Long getXCoord() {
        return xCoord.getValue();
    }

    public void setXCoord(Long xCoord) {
        this.xCoord.set(xCoord);
    }

    public Long getYCoord() {
        return yCoord.getValue();
    }

    public void setYCoord(Long yCoord) {
        this.yCoord.set(yCoord);
    }

    public Integer getXLooc() {
        return xLooc.getValue();
    }

    public void setXLooc(Integer xLooc) {
        this.xLooc.set(xLooc);
    }

    public Float getYLooc() {
        return yLooc.getValue();
    }

    public void setYLooc(Float yLooc) {
        this.yLooc.set(yLooc);
    }

    public Double getZLooc() {
        return zLooc.getValue();
    }

    public void setZLooc(Double zLooc) {
        this.zLooc.set(zLooc);
    }

//    public String getOriginalEyeColor() {
//        return originalEyeColor;
//    }
//
//    public String getOriginalNationality() {
//        return originalNationality;
//    }
//
//    public void setOriginalEyeColor(String originalEyeColor) {
//        this.originalEyeColor = originalEyeColor;
//    }
//
//    public void setOriginalNationality(String originalNationality) {
//        this.originalNationality = originalNationality;
//    }
}
