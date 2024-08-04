package utility;

import data.LabWork;
import utility.LabWorkArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DBData {
    public static Vector<LabWork> getConnection() throws FileNotFoundException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String userName = null;
        String password = null;
        try {
           Scanner credentials = new Scanner(new FileReader("C:\\Users\\Ирина\\OneDrive\\Рабочий стол\\Прога\\7 лабораторная\\lab7 (2)\\lab7\\lab6\\Server\\json\\credentials.txt"));
            try {
                userName = credentials.nextLine().trim();
                password = credentials.nextLine().trim();
                //String jdbcURL = "jdbc:postgresql://pg:5432/studs"
                String jdbcURL = "jdbc:postgresql://localhost:5432/studs";
                DataBaseHandler dbHandler = new DataBaseHandler(jdbcURL, userName, password);
                dbHandler.connectToDatabase();
                //System.out.println(collection);
                return dbHandler.loadCollectionFromBD();

            } catch (NoSuchElementException e) {
                System.out.println("Не найдены данные для входа в бд");
                throw new NoSuchElementException();
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Не найден credentials.txt");
        }
    }
}