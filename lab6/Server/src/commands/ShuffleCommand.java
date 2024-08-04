package commands;

import data.LabWork;
import exception.CommandNotAcceptArgumentsException;
import utility.CollectionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Shuffle command.
 */
public class ShuffleCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 11L;
    private final CollectionManager collectionManager;

    /**
     * Instantiates a new Shuffle command.
     *
     * @param collectionManager the collection manager
     */
    public ShuffleCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new CommandNotAcceptArgumentsException();
            }
            //Vector<LabWork> labWorkVector = collectionManager.getLabWorkVector();
            List<LabWork> labWorkList = new ArrayList<>(collectionManager.getLabWorkVector());
            Collections.shuffle(labWorkList);

            labWorkList.forEach(System.out::println);
            String answer = labWorkList.stream()
                    .map(str -> str+"\n")
                    .collect(Collectors.joining(""));
            //System.out.println(answer);
            return answer;

        } catch (CommandNotAcceptArgumentsException e) {
            return "Этой команде не нужны аргументы!";
        }
    }


    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "команда, перемешивающая все элементы коллекции в случайном порядке";
    }
}

