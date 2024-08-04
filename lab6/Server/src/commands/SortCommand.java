package commands;

import data.LabWork;
import exception.CommandNotAcceptArgumentsException;
import utility.CollectionManager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Sort command.
 */
public class    SortCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 12L;
    /**
     * The Collection manager.
     */
    CollectionManager collectionManager;

    /**
     * Instantiates a new Sort command.
     *
     * @param collectionManager the collection manager
     */
    public SortCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
        @Override
        public String execute(String argument) {
            try {
                if (!argument.isEmpty()) {
                    throw new CommandNotAcceptArgumentsException();
                }
                String answer = collectionManager.getLabWorkVector().stream()
                    .sorted(Comparator.comparing(LabWork::getName))
                    .map(str -> str + "\n")
                    .collect(Collectors.joining(""));
                //System.out.println(answer);
                return answer;
            }catch (CommandNotAcceptArgumentsException e) {
                return "Этой команде не нужны аргументы!";
            }
    }

        @Override
        public String getName() {
            return "sort";
        }

        @Override
        public String getDescription() {
            return "команда, сортирующая коллекцию в естественном порядке";
        }
    }

