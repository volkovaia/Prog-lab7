package utility;

import data.Coordinates;
import data.Difficulty;
import data.Discipline;
import data.LabWork;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DataBaseHandler {

    private String URL;
    private String username;

    private String password;
    private static Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (login, password) VALUES (?, ?)";
    private static final String CHECK_USER_REQUEST = "SELECT login, password FROM users WHERE (?) = login AND (?) = password";
    private static final String CHECK_USER_EXISTS_REQUEST = "SELECT login FROM users WHERE (?) = login";
    //private static final String JOIN_COLLECTION_USERS_REQUEST = "SELECT * FROM COLLECTIONS INNER JOIN USERS ON USERS.login = COLLECTIONS.author";
    private static final String GET_COLLECTION_REQUEST = "SELECT * FROM COLLECTIONS";
    private static final String CLEAR_USERS_ELEMENTS_REQUEST = "DELETE FROM COLLECTIONS WHERE (?) = COLLECTIONS.author";

    private static final String REMOVE_USERS_ELEMENT_BY_QUAL_MIN_REQUEST = "DELETE FROM COLLECTIONS WHERE (?) = COLLECTIONS.author AND (?) = COLLECTIONS.personalqualitiesminimum";
    private static final String DELETE_USERS_ELEMENT_BY_ID_REQUEST = "DELETE FROM COLLECTIONS WHERE (?) = COLLECTIONS.author AND (?) = COLLECTIONS.id";
    private static final String ADD_NEW_ELEMENT = "INSERT INTO COLLECTIONS (id, author, name, x_coord, y_coord, localdate, minimalpoint, personalqualitiesminimum, averagepoint, difficulty, disciplinename, practicehours, labscount) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_NEW_ELEMENT = "INSERT INTO COLLECTIONS (id, author, name, x_coord, y_coord, localdate, minimalpoint, personalqualitiesminimum, averagepoint, difficulty, disciplinename, practicehours, labscount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String pepper = "fg0vfwq/.qp";
    public DataBaseHandler() {

    }


    public DataBaseHandler (String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }



    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Подключение к базе данных установлено");
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных:" + e.getMessage() + e.getSQLState());
            System.exit(0);
        }
    }

    public boolean registerUser(String username, String password) throws SQLException {
        //if (userExists(username)) return false;
        try {
        PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST);
        addStatement.setString(1, username);
        addStatement.setString(2, encryptStringMD2(password + pepper));
        //System.out.println("registerUser:" + username + encryptStringMD2(password + pepper));
        addStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
        return true;
    }

    public boolean userExists(String username) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(CHECK_USER_EXISTS_REQUEST);
        findStatement.setString(1, username);
        findStatement.executeQuery();
        ResultSet result = findStatement.executeQuery();
        //System.out.println(result);
        return result.next();
    }

    public boolean checkUser(String username, String password) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(CHECK_USER_REQUEST);
        findStatement.setString(1, username);
        findStatement.setString(2, encryptStringMD2(password + pepper));
        //System.out.println("checkUser: " + username + encryptStringMD2(password + pepper));
        ResultSet result = findStatement.executeQuery();
        return result.next();
    }

    public static String encryptStringMD2(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(str.getBytes());
            BigInteger numRepresentation = new BigInteger(1, digest);
            String hashedString = numRepresentation.toString(16);
            while (hashedString.length() < 32) {
                hashedString = "0" + hashedString;
            }
            return hashedString;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ошибка при попытке хэшировать пароль");;
        }
        return null;

    }

    public void addNewElement(List<String> args) throws SQLException {
        java.util.Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());
        PreparedStatement findStatement = connection.prepareStatement(ADD_NEW_ELEMENT);
        //System.out.println(args);
        findStatement.setString(1, args.get(0));
        findStatement.setString(2, args.get(1));
        findStatement.setInt(3, Integer.parseInt(args.get(2)));
        findStatement.setFloat(4, Float.parseFloat(args.get(3)));
        findStatement.setTimestamp(5, timestamp);
        findStatement.setLong(6, Long.parseLong(args.get(4)));
        findStatement.setFloat(7, Float.parseFloat(args.get(5)));
        findStatement.setFloat(8, Float.parseFloat(args.get(6)));
        findStatement.setString(9, String.valueOf(Difficulty.valueOf(Difficulty.putByNumber.get(Integer.parseInt(args.get(7))))));
        findStatement.setString(10, args.get(8));
        findStatement.setLong(11, Long.parseLong(args.get(9)));
        findStatement.setLong(12, Long.parseLong(args.get(10)));
        findStatement.execute();

    }

    public void insertNewElement(List<String> args) throws SQLException {
        java.util.Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());
        PreparedStatement findStatement = connection.prepareStatement(ADD_NEW_ELEMENT);
        //System.out.println(args);
        findStatement.setInt(1, Integer.parseInt(args.get(1)));
        findStatement.setString(2, args.get(0));
        findStatement.setString(3, args.get(2));
        findStatement.setInt(4, Integer.parseInt(args.get(3)));
        findStatement.setFloat(5, Float.parseFloat(args.get(4)));
        findStatement.setTimestamp(6, timestamp);
        findStatement.setLong(7, Long.parseLong(args.get(5)));
        findStatement.setFloat(8, Float.parseFloat(args.get(6)));
        findStatement.setFloat(9, Float.parseFloat(args.get(7)));
        findStatement.setString(10, String.valueOf(Difficulty.valueOf(Difficulty.putByNumber.get(Integer.parseInt(args.get(8))))));
        findStatement.setString(11, args.get(9));
        findStatement.setLong(12, Long.parseLong(args.get(10)));
        findStatement.setLong(13, Long.parseLong(args.get(11)));
        findStatement.execute();

    }



    public void ClearUsersElements (String UserName) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(CLEAR_USERS_ELEMENTS_REQUEST);
        findStatement.setString(1, UserName);
        findStatement.execute();

    }

    public void DeleteUsersElementById (String UserName, int id) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(DELETE_USERS_ELEMENT_BY_ID_REQUEST);
        findStatement.setString(1, UserName);
        findStatement.setInt(2, id);
        findStatement.execute();
    }

    public void RemoveUsersElemByQualMin (String UserName, Float qual) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(REMOVE_USERS_ELEMENT_BY_QUAL_MIN_REQUEST);
        findStatement.setString(1, UserName);
        findStatement.setFloat(2, qual);
        findStatement.execute();
    }

    public Vector<LabWork> loadCollectionFromBD() {
        Vector<LabWork> collection = new Vector<>();
        try {
            PreparedStatement joinStatement = connection.prepareStatement(GET_COLLECTION_REQUEST);
            ResultSet result = joinStatement.executeQuery();

            //System.out.println(result);

            while (result.next()) {
                collection.add(exctractLabworkFromResult(result));
            }
            joinStatement.close();
            System.out.println("Коллекция успешно загружена из базы данных");

        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке коллекция из базы данных: "+ e.getMessage()+ e.getSQLState());
            System.exit(0);
        }
        return collection;
    }

    private static LabWork exctractLabworkFromResult(ResultSet result) throws SQLException {
        int id = result.getInt("id");


        String authorName = result.getString("author");
        String name = result.getString("name");
        double x = result.getInt("x_coord");
        float y = result.getFloat("y_coord");

        Timestamp creationDate = result.getTimestamp("localdate");
        long minimalPoint = result.getLong("minimalPoint");
        float personalQualitiesMinimum = result.getFloat("personalQualitiesMinimum");
        float averagePoint = result.getFloat("averagePoint");
        String disciplineName = result.getString("disciplinename");
        long practiceHours = result.getLong("practiceHours");
        long labscount = result.getLong("labsCount");
        
        Difficulty difficulty = Difficulty.enterDifficultyFromCorrectString(result.getString("difficulty"));
        Discipline discipline = new Discipline(disciplineName, practiceHours, labscount);
        Coordinates coordinates = new Coordinates((int) x, y);

        return (LabWork) new LabWork(id, authorName, name, coordinates, creationDate, minimalPoint, personalQualitiesMinimum, averagePoint, difficulty, discipline);

    }
}
