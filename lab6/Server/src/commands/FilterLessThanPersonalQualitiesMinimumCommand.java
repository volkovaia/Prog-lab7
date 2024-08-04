package commands;

import data.LabWork;
import exception.CommandNotFoundException;
import utility.CollectionManager;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Filter less than personal qualities minimum command.
 */
public class FilterLessThanPersonalQualitiesMinimumCommand implements Command, CommandClient, Serializable, AcceptFloat {
    private static final long serialVersionUID = 6L;

    /**
     * The Collection manager.
     */
    CollectionManager collectionManager;

    /**
     * Instantiates a new Filter less than personal qualities minimum command.
     *
     * @param collectionManager the collection manager
     */
    public FilterLessThanPersonalQualitiesMinimumCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String execute(String argument) {

        try {
            Float arg = Float.parseFloat(argument);
            List<LabWork> array;
            array = collectionManager.getLabWorkVector().stream()
                    .filter((labWork -> {
                        Float compare = labWork.getPersonalQualitiesMinimum();
                        return arg > compare;
                    })).collect(Collectors.toList());
            if (array.isEmpty()) {
                return "Нет полей personalQualitiesMinimum меньше заданного";
            } else {

                String answer = array.stream()
                        .map((labWork) -> {
                            return labWork.toString() + "\n";
                        })
                        .collect(Collectors.joining(""));
                //System.out.println(answer);
                return answer;


                //array.stream().forEach((labWork -> System.out.println(labWork.toString())));

            }

        }catch (NumberFormatException e){
            return "Команде нужен числовой аргумент, попробуйте снова!";
        }

    }

    @Override
    public String getName() {
        return "filter_less_than_personal_qualities_minimum";
    }

    @Override
    public String getDescription() {
        return "команда, выводящая элементы, значение поля personalQualitiesMinimum которых меньше заданного. Команда ожидает числовой аргумент";
    }
}

