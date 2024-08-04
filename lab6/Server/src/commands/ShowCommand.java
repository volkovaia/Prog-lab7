package commands;

import data.LabWork;
import exception.CommandNotAcceptArgumentsException;
import exception.CommandNotFoundException;
import utility.CollectionManager;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Show command.
 */
public class ShowCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 10L;
    private final CollectionManager collectionManager;

    /**
     * Instantiates a new Show command.
     *
     * @param collectionManager the collection manager
     */
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String argument) {
        try {
            if (!argument.isEmpty()){
                throw new CommandNotAcceptArgumentsException();
            }

            if (collectionManager.getLabWorkVector().isEmpty()) {
                return "Коллекция пуста";

            } else  {
                String answer = collectionManager.getLabWorkVector().stream()
                        .map((index) -> {
                            return index + "\n";
                        })
                        .collect(Collectors.joining(""));
                //System.out.println(answer);
                return answer;
            }
    }catch (CommandNotAcceptArgumentsException e){
            return "Этой команде не нужны аргументы!";
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "команда, выводящая в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}