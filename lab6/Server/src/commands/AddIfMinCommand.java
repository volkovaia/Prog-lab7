package commands;

import data.ComparatorLabwork;
import data.LabWork;
import exception.ExecuteCommandError;
import utility.CollectionManager;
import utility.DataBaseHandler;

import java.io.Serializable;
import java.util.*;

/**
 * The type Add if min command.
 */
public class AddIfMinCommand implements Command, CommandClient, Serializable, AcceptString, AcceptUser{
    private static final long serialVersionUID = 4L;
    private CollectionManager collectionManager;

    /**
     * Instantiates a new Add if min command.
     *
     * @param collectionManager the collection manager
     */
    public AddIfMinCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    @Override
    public String execute(String argument) throws Exception {
        try {
            collectionManager.addIfMin(argument);
            List<String> parsedArgs = collectionManager.parseArgument(argument);
            dataBaseHandler.addNewElement(parsedArgs);
        } catch (ExecuteCommandError | NumberFormatException | IndexOutOfBoundsException e){
            return e.getMessage();
        }
        return "Элемент добавлен в коллекцию";
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "команда, добавляющая элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции. Команда ожидает аргумент числового типа";
    }
}
