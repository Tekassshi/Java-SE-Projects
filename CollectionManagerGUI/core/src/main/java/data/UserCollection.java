package data;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class to wrap given collection and store it metadata.
 * */
public class UserCollection {

    private ZonedDateTime init_date = null;

    private Deque<Person> collection = new ArrayDeque<>();

    /**
     * @return "collection" field "Deque" class object.
     * */
    public Deque<Person> getCollection() {
        return collection;
    }

    /**
     * Setter method to set "collection" filed value.
     * @param collection "Deque" class collection.
     * */
    public void setCollection(Deque<Person> collection) {
        this.collection = collection;
    }

    /**
     * @return "init_date" field value "ZonedDateTime" class object.
     * */
    public ZonedDateTime getInit_date() {
        return init_date;
    }

    /**
     * Setter method to set "init_date" filed value.
     * @param init_date "ZonedDateDime" class object.
     * */
    public void setInit_date(ZonedDateTime init_date) {
        this.init_date = init_date;
    }
}
