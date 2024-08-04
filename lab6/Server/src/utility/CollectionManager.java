package utility;

import data.*;
import exception.ExecuteCommandError;
import exception.IllegalValueException;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

import static utility.DBData.getConnection;


public class CollectionManager implements CollectionManagerInterface, Serializable {
    private Vector<LabWork> labWorkCollection = new Vector<>();

    @Override
    public Vector<LabWork> getLabWorkVector() {
        return labWorkCollection;
    }

    @Override
    public LocalDate getLastInitTime() {
        return null;
    }

    public CollectionManager() {}

    public Vector<LabWork> checkUniqueId(Vector<LabWork> toCheck) {
        Vector<LabWork> uniqueLabWork = new Vector<>();
        Set<Integer> uniqueIDs = new HashSet<>();
        for (LabWork labWork : toCheck) {
            int oldID = labWork.getId();
            if (!uniqueIDs.add(oldID)) {
                System.out.println("Проверьте id " + oldID + ", он не является уникальным!");
                int newID = getId();
                while (!uniqueIDs.add(newID)) {
                    newID = getId();
                }
                labWork.setId(newID);
                System.out.println("Заменён на уникальный id: " + newID);
            }
            uniqueLabWork.add(labWork);
        }
        return uniqueLabWork;
    }


    public List<String> parseArgument(String args){
        List<String> arguments = new ArrayList<>();
        while (args.contains(";")) {
            arguments.add(args.split(";")[0]);
            args = args.substring(args.split(";")[0].length() + 1, args.length());
        }
        arguments.add(args);
        return arguments;
    }

//    public String getAuthorName() {
//        List<String> arguments = parseArgument(args);
//        return arguments.get(0);
//    }


        public LabWork addFromInput(String args) throws ExecuteCommandError, IllegalValueException {
        List<String> arguments = parseArgument(args);
        try {
            if (arguments.get(1).isBlank()) {
                throw new ExecuteCommandError("Имя не может быть пустым");
            }
            if (arguments.get(2).isBlank()) {
                throw new ExecuteCommandError("Координата X не может быть пустой");
            }
            try {
                Integer.parseInt(arguments.get(3));
            } catch (NumberFormatException e){
                throw new ExecuteCommandError("Координата X должна быть типа int. Попробуйте снова!");
            }

            if (arguments.get(3).isBlank()) {
                throw new ExecuteCommandError("Координата Y не может быть пустой");
            }
            try {
                Integer.parseInt(arguments.get(3));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Координата Y должна быть типа Float. Попробуйте снова!");
            }
            if (arguments.get(4).isBlank()){
                throw new ExecuteCommandError("Минимальное значение не может быть пустым");
            }
            try {
                Long.parseLong(arguments.get(4));
            } catch (NumberFormatException e){
                throw new NumberFormatException("Минимальное значение должно быть типа long. Попробуйте снова!");
            } if (arguments.get(5).isBlank()){
                throw new ExecuteCommandError("Минимальные личностные качества не могут быть меньше 0 или пустым");
            }
            try {
                Float.parseFloat(arguments.get(5));
                if (Float.parseFloat(arguments.get(5)) < 0){
                    throw new ExecuteCommandError("Минимальные личностные качества не могут быть меньше 0");
                }
            } catch (NumberFormatException e){
                throw new NumberFormatException("Минимальные личностные качества должны быть типа Float и не могут быть меньше 0. Попробуйте снова!");
            }
            try {
                Float.parseFloat(arguments.get(6));
                if (Float.parseFloat(arguments.get(6)) < 0){
                    throw new NumberFormatException("Среднее значение не может быть меньше 0");
                }
            } catch (NumberFormatException e){
                throw new NumberFormatException("Среднее значение должно быть типа float. Попробуйте снова!");
            }
            try{
                Integer.parseInt(arguments.get(7));
                if (Integer.parseInt(arguments.get(7)) != 1 && Integer.parseInt(arguments.get(7)) != 2 && Integer.parseInt(arguments.get(7)) != 3){
                    throw new ExecuteCommandError("Нет такого уровня сложности!");}
            } catch (NumberFormatException e){
                throw new NumberFormatException("Введите уровень сложности то 1 до 3. Попробуйте снова!");
            }
            if (arguments.get(8).isBlank()){
                throw new ExecuteCommandError("Имя дисциплины не может быть пустым");
            }
            try{
                Long.parseLong(arguments.get(9));
            } catch (NumberFormatException e){
                throw new NumberFormatException("Часы практики должны быть типа Long. Попробуйте снова!");
            }
            try{
                Long.parseLong(arguments.get(10));
            } catch (NullPointerException e){
                throw new NumberFormatException("Количество лабораторных работ должно быть типа Long. Попробуйте снова!");
            }

            HashMap<Integer, String> putByNumber = new HashMap<>();
            putByNumber.put(1, "EASY");
            putByNumber.put(2, "NORMAL");
            putByNumber.put(3, "INSANE");

            //System.out.println(arguments);
            Date currentDate = new Date();
            Timestamp timestamp = new Timestamp(currentDate.getTime());
            //System.out.println("Текущий timestamp: " + timestamp);

                LabWork labWork = new LabWork(1, arguments.get(0), arguments.get(1),
                        new Coordinates(Integer.parseInt(arguments.get(2)),
                                Float.parseFloat(arguments.get(3))), timestamp,
                        Long.parseLong(arguments.get(4)),
                        Float.parseFloat(arguments.get(5)),
                        Float.parseFloat(arguments.get(6)),
                        Difficulty.valueOf(putByNumber.get(Integer.parseInt(arguments.get(7)))),
                        new Discipline(arguments.get(8),
                                Long.parseLong(arguments.get(9)),
                                Long.parseLong(arguments.get(10))));
                getLabWorkVector().add(labWork);
                checkUniqueId(getLabWorkVector());
                return labWork;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Передано некорректное количество аргументов");
        } catch (ExecuteCommandError e) {
            throw new ExecuteCommandError(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void clear(){
        labWorkCollection.clear();
    }


    @Override
    public LabWork addIfMin(String args) throws Exception {
        List<String> arguments = parseArgument(args);
        try {
            Float.parseFloat(arguments.get(6));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("averagePoint должен быть типа Float");
        }
        List<LabWork> Labworks = getLabWorkVector();
        Comparator choose = new ComparatorLabwork();
        Collections.sort(Labworks, choose);
        LabWork minAvgPoint = Labworks.get(0);
       // System.out.println(minAvgPoint);
        if (minAvgPoint.getAveragePoint() > Float.parseFloat(arguments.get(5))) {
            try {
                LabWork labwork = addFromInput(args);
                return labwork;
            } catch (ExecuteCommandError e) {
                throw new ExecuteCommandError(e.getMessage());
            } catch (NumberFormatException e){
                throw new NumberFormatException(e.getMessage());
            } catch (IndexOutOfBoundsException e){
                throw new IndexOutOfBoundsException(e.getMessage());
            }
        } else {
            throw new ExecuteCommandError("Значение averagePoint не является наименьшим");
        }
    }

    @Override
    public LabWork updateByIdCollection(String args) throws Exception {
        String[] fromArgs = args.split(";");
        int id;
        try {
            id = Integer.parseInt(fromArgs[0]);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Команде нужен аргумент типа int!");
        }
        String itemValues = String.join(";", Arrays.copyOfRange(fromArgs, 1, fromArgs.length));
        try {
                if (getLabWorkVector().removeIf((labWork -> labWork.getId() == id))) {
                    LabWork updLabwork = addFromInput(itemValues);
                    updLabwork.setId(id);
                    return updLabwork;
                }
                else {
                   throw new ExecuteCommandError("Нет элемента с таким id!");
                 }
            } catch (ExecuteCommandError e) {
            throw new ExecuteCommandError(e.getMessage());
        }
    }


    public void loadCollection() throws FileNotFoundException {
        try {
            labWorkCollection = getConnection();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    int lastId = 1;
    public int getId() {
            for (LabWork labWork : getLabWorkVector()) {
                while (labWork.getId() == lastId) {lastId += 1;}
            }
        return lastId;
    }
    public boolean checkExist(int id) {
        return labWorkCollection.stream()
                .anyMatch((x) -> x.getId() == id);
    }

}



