package util;

import commands.UpdateCollection;
import data.TableViewPerson;
import data.Person;
import interfaces.Command;

import java.io.IOException;
import java.util.ArrayList;

public class CollectionUpdater implements Runnable {

    private AuthorizationManager authorizationManager;
    private ConnectionManager connectionManager;

    public CollectionUpdater(AuthorizationManager authorizationManager, ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
        this.authorizationManager = authorizationManager;
    }

    @Override
    public void run() {
        while (true) {
            Command updateCollection = new UpdateCollection(null);
            try {
                connectionManager.sendRequest(updateCollection, authorizationManager.getToken());
                ArrayList<Person> tmp = connectionManager.readUpdatedCollectionResponse();

                if (tmp != null) {
                    ArrayList<TableViewPerson> extended = getValues(tmp);
                    CollectionWrapper.clearList();
                    CollectionWrapper.addAll(extended);
                }
                Thread.sleep(2000);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ArrayList<TableViewPerson> getValues(ArrayList<Person> list) {
        ArrayList<TableViewPerson> out = new ArrayList<>();

        for (Person person : list) {
            TableViewPerson tableViewPerson = new TableViewPerson();
            tableViewPerson.setId(person.getId());
            tableViewPerson.setUsername(person.getUsername());
            tableViewPerson.setName(person.getName());
            tableViewPerson.setXCoord(person.getCoordinates().getX());
            tableViewPerson.setYCoord(person.getCoordinates().getY());
            tableViewPerson.setCreationDate(person.getCreationDate());
            tableViewPerson.setHeight(person.getHeight());
            tableViewPerson.setWeight(person.getWeight());
            tableViewPerson.setEyeColor(person.getEyeColor());
            tableViewPerson.setNationality(person.getNationality());
            tableViewPerson.setXLooc(person.getLocation().getX());
            tableViewPerson.setYLooc(person.getLocation().getY());
            tableViewPerson.setZLooc(person.getLocation().getZ());
            out.add(tableViewPerson);
        }
        return out;
    }
}