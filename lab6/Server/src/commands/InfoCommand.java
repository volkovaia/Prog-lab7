package commands;

import exception.CommandNotAcceptArgumentsException;
import utility.CollectionManager;

import java.io.Serializable;

/**
 * The type Info command.
 */
public class InfoCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 2L;
    private CollectionManager collectionManager;


    /**
     * Instantiates a new Info command.
     *
     * @param collectionManager the collection manager
     */
    public InfoCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        try {
            if (!argument.isEmpty()){
                throw new CommandNotAcceptArgumentsException();
            }
            String answer = "";

            answer += "Сведения о коллекции:" + '\n';
            answer += "Тип: " + collectionManager.getLabWorkVector().getClass().getSimpleName() + "\n";
            answer += "Дата инициализации: " + collectionManager.getLastInitTime() + "\n";
            answer += "Количество элементов: " + collectionManager.getLabWorkVector().size() + "\n";
            return  answer;
            }
        catch (CommandNotAcceptArgumentsException e){
            return  "Этой команде не нужны аргументы!";
        }
    }


    @Override
    public String getName() {
        return "info";
    }


    @Override
    public String getDescription() {
        return "команда, выводящая в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.";
    }
}
