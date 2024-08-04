package commands;

import data.LabWork;
import exception.CommandNotAcceptArgumentsException;
import exception.ExecuteCommandError;
import utility.CollectionManager;
import utility.CommandManager;
import utility.DataBaseHandler;


import java.awt.event.ComponentAdapter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Clear command.
 */
//
///**
// * Команда очистить коллекцию
// */
//public class Clear implements Command {
//    private CollectionManager collectionManager = JavaCollectionManager.getInstance();
//
//    @Override
//    public void execute(String argument) {
//        try {
//            if (!argument.isEmpty()) throw new CommandNotAcceptArgumentsException();
//            collectionManager.clear();
//        } catch (CommandNotAcceptArgumentsException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public String getDescription() {
//        return null;
//    }
//}
public class ClearCommand implements Command, CommandClient, Serializable, AcceptUser {
    private static final long serialVersionUID = 5L;
    /**
     * Instantiates a new Clear command.
     *
     * @param collectionManager the collection manager
     */
    public ClearCommand(CollectionManager collectionManager, CommandManager commandManager){
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;

    }
    private CollectionManager collectionManager;
    private CommandManager commandManager;

    @Override
    public String execute(String argument) throws Exception {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String userName = argument.split(";", 2)[0];
        try {
            argument = argument.split(";", 2)[1];
            if (!argument.isBlank()) {
                throw new CommandNotAcceptArgumentsException();
            }
            collectionManager.getLabWorkVector().removeIf(elem -> {
                boolean equals = userName.equals(elem.getAuthorName());
                if (equals) System.out.println(elem);
                try {
                    dataBaseHandler.ClearUsersElements(userName);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при попытке удалить элементы: "+ e);
                }
                return equals;
            });
            return "Коллекция очищена!";
        } catch (CommandNotAcceptArgumentsException e){
            return "Этой команде не нужны аргументы!";
        }
    }


    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "команда, очищающая коллекцию";
    }

}