package commands;

import exception.CommandNotFoundException;
import utility.CommandManager;
import utility.DataBaseHandler;

import javax.security.auth.login.LoginException;
import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginCommand implements CommandClient, Command, Serializable, AcceptString {
    private static final long serialVersionUID = 1L;
    private transient CommandManager commandManager;

    public LoginCommand(CommandManager commandManager) {

    }
    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    @Override
    public String execute(String argument) throws Exception {
        String userName = argument.split(" ")[0];
        String userPassword = argument.split(" ")[1];
        //System.out.println(userName + " " + userPassword);
        if (dataBaseHandler.checkUser(userName, userPassword)){
            return  "Пользователь успешно авторизован";
        }
        else {
            throw new LoginException("Ошибка авторизации. Проверьте логин и пароль.");
        }
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "авторизация пользователя. Ожидает ввода логина и пароля";
    }
}
