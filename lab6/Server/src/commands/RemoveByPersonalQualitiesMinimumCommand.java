package commands;
//
//import data.LabWork;
//import exception.CommandNotAcceptArgumentsException;
//import utility.CollectionManager;
//import utility.ConsoleManager;
//import utility.CreateNewElementManager;
//
//import java.util.ArrayList;
//
///**
// * Команда удаляет все элементы меньшие, чем переданный ей
// */
//public class RemoveAllByPersonalQualitiesMinimum implements Command {
//    private CollectionManager collectionManager = JavaCollectionManager.getInstance();
//
//    @Override
//    public void execute(String argument) {
//        try {
//            if (!argument.isEmpty()) throw new CommandNotAcceptArgumentsException();
//            LabWork labWork = CreateNewElementManager.createNewElement();
//            ArrayList<LabWork> removeArray = new ArrayList<>();
//            boolean flag = false;
//            for (LabWork index : collectionManager.getLabWorkCollection()) {
//                if (index.compareTo(labWork) < 1) {
//                    removeArray.add(index);
//                    flag = true;
//                }
//            }
//            if (flag) {
//                System.out.println("Подтвердите удаление элементов:");
//                for (LabWork index : removeArray) {
//                    System.out.println(index.toString());
//                }
//                System.out.println("Введите y/n");
//                while (true) {
//                    try {
//                        var userPrint = ConsoleManager.getUserPrint();
//                        if (userPrint.equals("y")) {
//                            for (LabWork index : removeArray) {
//                                collectionManager.getLabWorkCollection()
//                                        .removeIf(route2Delete -> (route2Delete.getId() == index.getId()));
//                            }
//                            System.out.println("Элементы успешно удалены");
//                            break;
//                        } else if (userPrint.equals("n")) {
//                            System.out.println("Удаление элементов ОТМЕНЕНО");
//                            break;
//                        } else throw new IllegalArgumentException("Введено что-то не то. Повторите попытку.");
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                System.out.println("Нет элементов больших, чем заданный.");
//            }
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

import utility.CollectionManager;
import utility.DataBaseHandler;

import java.io.Serializable;
import java.sql.SQLException;


/**
 * The type Remove by personal qualities minimum command.
 */
public class RemoveByPersonalQualitiesMinimumCommand implements Command, CommandClient, Serializable, AcceptFloat, AcceptUser {
    private static final long serialVersionUID = 9L;
    /**
     * Instantiates a new Remove by personal qualities minimum command.
     *
     * @param collectionManager the collection manager
     */
    public RemoveByPersonalQualitiesMinimumCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    private CollectionManager collectionManager;
    @Override
    public String execute(String argument) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            String userName = argument.split(";")[0];
            float personalQualitiesMinimum = Float.parseFloat(argument.split(";")[1]);
            collectionManager.getLabWorkVector().removeIf(labWork -> {
                boolean equals = userName.equals(labWork.getAuthorName()) && labWork.getPersonalQualitiesMinimum() == personalQualitiesMinimum;
                if (equals) {
                    try { dataBaseHandler.RemoveUsersElemByQualMin(userName, personalQualitiesMinimum);
                    } catch (SQLException e) {throw new RuntimeException("Ошибка при попытке удалить элементы: " + e);
                    }
                } return equals;
            });
            return "Удалён элемент по personalQualitiesMinimum: " + personalQualitiesMinimum;
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            return "Команде необходим аргумент типа float!";
        }
    }

    @Override
    public String getName() {
        return "remove_all_by_personal_qualities_minimum";
    }

    @Override
    public String getDescription() {
        return "команда, удаляющая из коллекции все элементы, значение поля personalQualitiesMinimum которых эквивалентно заданному. Команда ожидает аргумент типа float!";
    }
}