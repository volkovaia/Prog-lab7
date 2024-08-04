package commands;

import exception.ExecuteCommandError;
import exception.IllegalValueException;
import utility.CollectionManager;
import utility.DataBaseHandler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * The type Add command.
 */
public class AddCommand implements Command, CommandClient, Serializable, AcceptString, AcceptUser{
    private static final long serialVersionUID = 3L;
    /**
     * The Collection manager.
     */
    static CollectionManager collectionManager;

    /**
     * Instantiates a new Add command.
     *
     * @param collectionManager the collection manager
     */
    public AddCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

    }

    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    @Override
    public String execute(String argument) {
        try {
            //System.out.println(argument);
            collectionManager.addFromInput(argument);
            List<String> parsedArgs = collectionManager.parseArgument(argument);
            dataBaseHandler.addNewElement(parsedArgs);
            return "Элемент добавлен в коллекцию!";
        } catch (ExecuteCommandError | IndexOutOfBoundsException | NumberFormatException | IllegalValueException e) {
            return e + e.getMessage();
        } catch (SQLException e) {
            return "ADD Command SQLException: " + e.getMessage();
        }

    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "команда, добавляющая новый элемент в коллекцию";
    }
}
