package util;

import data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CollectionWrapper {
    private static ObservableList<TableViewPerson> collection = FXCollections.observableArrayList();

    public static ObservableList<TableViewPerson> getCollection() {
        return collection;
    }

    public static void clearList(){
        collection.clear();
    }

    public static void addAll(ArrayList<TableViewPerson> list) {
        for (TableViewPerson person: list) {
            collection.add(person);
        }
    }
}