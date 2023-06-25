package util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import commands.UpdateCollection;
import data.TableViewPerson;
import data.Person;
import interfaces.Command;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

import static guicontrollers.SessionController.loadErrPortChoosingField;

public class CollectionUpdater implements Runnable {

    private AuthorizationManager authorizationManager;
    private ConnectionManager connectionManager;

    public CollectionUpdater(AuthorizationManager authorizationManager, ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
        this.authorizationManager = authorizationManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Command updateCollection = new UpdateCollection(null);
                connectionManager.sendRequest(updateCollection, authorizationManager.getToken());
                ArrayList<Person> tmp = connectionManager.readUpdatedCollectionResponse();
                if (tmp != null) {
                    ArrayList<TableViewPerson> extended = getValues(tmp);
                    CollectionWrapper.clearList();
                    CollectionWrapper.addAll(extended);
                }
                Thread.sleep(2000);
            }
        }
        catch (InterruptedException e) {
            System.out.println("Collection updater stopped!");
        }
        catch (IOException e) {
            Platform.runLater(() -> {
                try {
                    loadErrPortChoosingField("Server error. Please, try later.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Thread.currentThread().interrupt();
        }
        catch (JWTVerificationException e) {
            Platform.runLater(() -> {
                try {
                    loadErrPortChoosingField("Session timeout. Connect again.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Thread.currentThread().interrupt();
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