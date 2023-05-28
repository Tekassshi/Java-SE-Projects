package concurrency;

import commands.AbstractCommand;
import commands.FilterByNationality;
import commands.PrintFieldDescendingHeight;
import commands.Show;
import data.Country;
import data.Person;
import managers.CollectionManager;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class LongResponseCommandExecutor extends RecursiveTask<ArrayList<Person>> {
    private CollectionManager collectionManager;
    private AbstractCommand command;

    public LongResponseCommandExecutor(CollectionManager collectionManager, AbstractCommand command) {
        this.collectionManager = collectionManager;
        this.command = command;
    }

    @Override
    protected ArrayList<Person> compute() {
        ArrayList<Person> tmp = null;

        if (command instanceof Show)
            tmp = new ArrayList<>(collectionManager.getCollection());
        else if (command instanceof FilterByNationality)
            tmp = collectionManager.getFilteredNationalityCollection(Country.valueOf(command.getArgument()));
        else if (command instanceof PrintFieldDescendingHeight) {
            tmp = collectionManager.getDescendingHeightCollection();
        }

        return tmp;
    }
}
