package data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Person class that contains only state fields. Used for storing it objects in collection.
 * */
@JacksonXmlRootElement(localName = "customer")
public class Person implements Serializable {

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

    private String username;
    /**
     * Builder design pattern for Person class.
     * */
    public static class Builder {
        private Person person;

        public Builder() {
            person = new Person();
        }

        public Builder id (long id){
            person.id = id;
            return this;
        }

        public Builder name(String name){
            person.name = name;
            return this;
        }

        public Builder coordinates(Coordinates coordinates){
            person.coordinates = coordinates;
            return this;
        }

        public Builder dateTime(ZonedDateTime date){
            person.creationDate = date;
            return this;
        }

        public Builder height(Integer height){
            person.height = height;
            return this;
        }

        public Builder weight(Float weight){
            person.weight = weight;
            return this;
        }

        public Builder eyeColor(Color eyeColor){
            person.eyeColor = eyeColor;
            return this;
        }

        public Builder nationality(Country nationality){
            person.nationality = nationality;
            return this;
        }

        public Builder location(Location location){
            person.location = location;
            return this;
        }

        public Builder username(String username){
            person.username = username;
            return this;
        }

        public Person build(){
            return person;
        }
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}