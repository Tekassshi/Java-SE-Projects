package data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.ZonedDateTime;

/**
 * Person class that contains only state fields. Used for storing it objects in collection.
 * */
@JacksonXmlRootElement(localName = "customer")
public class Person {

    private long id;
    private String name;
    private Coordinates coordinates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private java.time.ZonedDateTime creationDate;

    private Integer height;
    private Float weight;
    private Color eyeColor;
    private Country nationality;
    private Location location;

    /**
     * @return "id" field long value.
     * */
    public long getId() {
        return id;
    }

    /**
     * Setter method to set "id" filed value.
     * @param id long id value
     * */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return "name" field String value.
     * */
    public String getName() {
        return name;
    }

    /**
     * Setter method to set "name" filed value.
     * @param name String name value
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return "coordinates" field Coordinates object.
     * */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Setter method to set "coordinates" filed value.
     * @param coordinates "Coordinates" class object
     * */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return "creationDate" field ZonedDateTime object.
     * */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Setter method to set "creationDate" filed value.
     * @param creationDate "ZonedDateTime" class object
     * */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return "height" field Integer value.
     * */
    public Integer getHeight() {
        return height;
    }

    /**
     * Setter method to set "height" filed value.
     * @param height "Integer" height value.
     * */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return "weight" field Float value.
     * */
    public Float getWeight() {
        return weight;
    }

    /**
     * Setter method to set "weight" filed value.
     * @param weight "Float" weight value.
     * */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * @return "eyeColor" field "Color" class object.
     * */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Setter method to set "eyeColor" filed value.
     * @param eyeColor "Color" enum color value.
     * */
    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * @return "nationality" field "Country" class object.
     * */
    public Country getNationality() {
        return nationality;
    }

    /**
     * Setter method to set "nationality" filed value.
     * @param nationality "Country" enum nationality value.
     * */
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    /**
     * @return "location" field "Location" class object.
     * */
    public Location getLocation() {
        return location;
    }

    /**
     * Setter method to set "location" filed value.
     * @param location "Location" object.
     * */
    public void setLocation(Location location) {
        this.location = location;
    }
}
