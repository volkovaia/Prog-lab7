package utility;

import commands.Command;
import exception.CommandNeedArgumentException;
import exception.CommandNotFoundException;
import exception.ExecuteCommandError;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
public class CommandManager implements Serializable {

    @Getter
    private final HashMap<String, Command> commands = new HashMap<>();
    //public String UserName = "";

    public CommandManager(){
    }

    public String executeCommand(String userName, String stringCommand) throws Exception {
        //UserName = userName;
        //System.out.println(stringCommand);
        String[] commandSplit = stringCommand.split(" ", 2);
        String commandName = commandSplit[0];
        String commandArguments = "";

        if (commandSplit.length == 2) {
            commandArguments = commandSplit[1];
        }
        Command command = commands.get(commandName);
        if (command == null) {
            throw new CommandNotFoundException(commandName);
        }
        try{
            String checkArg = command.getClass().getInterfaces()[3].getName();
            if (checkArg.equals("commands.AcceptInteger")) {
                try {
                    Integer.parseInt(commandArguments);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("CommandData: parseInt error");
                }
            } else if (checkArg.equals("commands.AcceptFloat")) {
                try {
                    Float.parseFloat(commandArguments);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("CommandData: parseFloat error");
                }
            } else if (checkArg.equals("commands.AcceptString")) {
                if (commandArguments.isBlank()) {
                    throw new CommandNeedArgumentException();}
            } else if (checkArg.equals("commands.AcceptUser")) {
                commandArguments = userName + ";" + commandArguments;
            }
            try {
                String checkArg4 = command.getClass().getInterfaces()[4].getName();

                if (checkArg4.equals("commands.AcceptUser")) {
                    commandArguments = userName + ";" + commandArguments;
                }
            } catch (IndexOutOfBoundsException ignored) {}

        } catch (IndexOutOfBoundsException e){
            if (commandArguments.length() > 0) {
                throw new IndexOutOfBoundsException("Команда не ожидает аргументов");
            }
        }
        try {
            String answer = command.execute(commandArguments);
            return answer;
        } catch (ExecuteCommandError | RuntimeException e) {
            return e.getMessage();
        }
    }

    public String showDescription(String stringCommand) throws CommandNotFoundException {

        String[] commandSplit = stringCommand.split(" ", 2);
        String commandName = commandSplit[0];
        String commandArguments = "";


        if (commandSplit.length == 2) {
            commandArguments = commandSplit[1];
        }

        Command command = commands.get(commandName);


        if (command == null) {
            throw new CommandNotFoundException(commandName);
        }
        try {
            String answer = command.getDescription();
            return answer;
        } catch (CommandNeedArgumentException e) {
            return e.getMessage();
        }
    }
}