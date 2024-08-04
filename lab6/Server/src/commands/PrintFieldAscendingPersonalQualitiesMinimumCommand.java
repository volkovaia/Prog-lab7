package commands;

import data.LabWork;
import exception.CommandNotAcceptArgumentsException;
import utility.CollectionManager;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The type Print field ascending personal qualities minimum command.
 */
public class PrintFieldAscendingPersonalQualitiesMinimumCommand implements Command, CommandClient, Serializable, AcceptFloat {
    private static final long serialVersionUID = 7L;

    /**
     * The Collection manager.
     */
    CollectionManager collectionManager;

    /**
     * Instantiates a new Print field ascending personal qualities minimum command.
     *
     * @param collectionManager the collection manager
     */
    public PrintFieldAscendingPersonalQualitiesMinimumCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new CommandNotAcceptArgumentsException();
            }
            String answer = collectionManager.getLabWorkVector().stream()
                    .map(LabWork::getDiscipline)
                    .sorted()
                    .map(str -> str + "\n")
                    .collect(Collectors.joining(""));
            //System.out.println(answer);
            return answer;
        } catch (CommandNotAcceptArgumentsException e) {
            return "Этой команде не нужны аргументы!";
        }
    }
    @Override
    public String getName() {
        return "print_field_ascending_discipline";
    }

    @Override
    public String getDescription() {
        return "команда, выводящая значение поля discipline всех элементов в порядке возрастания";
    }
}
