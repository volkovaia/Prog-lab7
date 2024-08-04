package commands;

import exception.CommandNotFoundException;
import utility.CommandManager;
import utility.DataBaseHandler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegisterUserCommand implements CommandClient, Command, Serializable, AcceptString {
    private static final long serialVersionUID = 1L;
    private transient CommandManager commandManager;

    public RegisterUserCommand(CommandManager commandManager) {
    }
    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    @Override
    public String execute(String argument) throws Exception {
        try{
            String userName = argument.split(" ")[0];
            String userPassword = argument.split(" ")[1];
            //return "Пользователь успешно зарегистрирован";
            //System.out.println("register command: " + userName + userPassword);
            if (dataBaseHandler.userExists(userName)) {
                return "Пользователь с таким именем уже существует, попробуйте снова";
            } else {
                try {
                    dataBaseHandler.registerUser(userName, userPassword);
                    return "Регистрация пользователя прошла успешно";
                } catch (SQLException e) {
                    throw new SQLException("Ошибка регистрации пользователя.");
                }
            }
            } catch (IndexOutOfBoundsException e) {
                return "Пароль не может быть пустым";
        }

    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "регистрация нового пользователя. Ожидает ввода логина и пароля";
    }
}
