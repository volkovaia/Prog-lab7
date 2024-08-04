package commands;

import exception.CommandNeedArgumentException;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;
import utility.DataBaseHandler;

import java.io.Serializable;
import java.sql.SQLException;


/**
 * The type Remove by id command.
 */
public class RemoveByIdCommand implements Command, CommandClient, Serializable, AcceptInteger, AcceptUser {
    private static final long serialVersionUID = 8L;
    /**
     * Instantiates a new Remove by id command.
     *
     * @param collectionManager    the console manager
     * @param commandManager the collection manager
     */
    public RemoveByIdCommand(CollectionManager collectionManager, CommandManager commandManager){
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }
    private CollectionManager collectionManager;
    private final CommandManager commandManager;

    @Override
    public String execute(String argument) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            String userName = argument.split(";")[0];
            argument = argument.split(";")[1];
            if (argument.isEmpty()) throw new CommandNeedArgumentException("Это команде необходим аргумент типа int!");

            int id = Integer.parseInt(argument);
            if (collectionManager.getLabWorkVector().removeIf(labWork -> (labWork.getId() == id && labWork.getAuthorName().equals(userName)))) {
                try {
                    dataBaseHandler.DeleteUsersElementById(userName, id);
                } catch (SQLException e) {
                    throw new SQLException("Ошибка при выполнении комманды RemoveById на стороне базы данных." + e);
                }
                return "Удалён элемент по id: " + argument;
            } else {
                return "Элемента с таким id не существует!";
            }
        }catch (NumberFormatException | SQLException e){
            return "id должен иметь тип int";
        }


    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "команда, удаляющая элемент из коллекции по его id. Команда ожидает аргумент типа int!";
    }
}