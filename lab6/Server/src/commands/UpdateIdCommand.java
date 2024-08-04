package commands;

import exception.ExecuteCommandError;
import exception.IllegalValueException;
import utility.CollectionManager;
import utility.DataBaseHandler;

import java.io.Serializable;

/**
 * The type Update id command.
 */
public class UpdateIdCommand implements Command, CommandClient, Serializable, AcceptString, AcceptUser {
    private static final long serialVersionUID = 13L;
    private final CollectionManager collectionManager;

    /**
     * Instantiates a new Update id command.
     *
     * @param collectionManager the collection manager
     */
    public UpdateIdCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

    }
    @Override
    public String execute(String argument) throws Exception {
        try {
            String userName = argument.split(";")[0];
            int id = Integer.parseInt(argument.split(";")[1]);
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            try {
                dataBaseHandler.DeleteUsersElementById(userName, id);
                collectionManager.updateByIdCollection(argument);
                dataBaseHandler.insertNewElement(collectionManager.parseArgument(argument));
            } catch (ExecuteCommandError | NumberFormatException | IndexOutOfBoundsException |
                     IllegalValueException e) {
                return e.getMessage();
            }
            return "Команда успешно обновлена";
        } catch (IndexOutOfBoundsException e) {
            return "Передано неверное количество аргументов";
        }
    }
    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public String getDescription() {
        //removeIf
        return "команда, обновить значение элемента коллекции, id которого равен заданному. Команда ожидает аргумент типа int!";
    }
}