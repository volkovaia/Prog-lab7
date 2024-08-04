package commands;//package commands;

import exception.CommandNotAcceptArgumentsException;

import java.io.Serializable;

/**
 * The type Exit command.
 */
public class ExitCommand implements Command, CommandClient, Serializable, AcceptUser {

    private static final long serialVersionUID = 20L;

    @Override
    public String execute(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new CommandNotAcceptArgumentsException();}
            System.exit(0);
        } catch (CommandNotAcceptArgumentsException e){
            return "Этой команде не нужны аргументы!";
        }
        return "";
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "команда, завершающая программу (без сохранения в файл)";
    }
}