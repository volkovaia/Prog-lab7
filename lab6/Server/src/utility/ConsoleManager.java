package utility;

import exception.CommandNotFoundException;
import exception.ExecuteCommandError;
import lombok.Data;

import java.io.Serializable;
import java.util.Scanner;

@Data
public class ConsoleManager implements Serializable {

    private Scanner scanner = new Scanner(System.in);

    CommandManager manager;

    private boolean isRunning = false;


    public ConsoleManager(CommandManager manager) {
        this.manager = manager;
        //manager.getCommands().put("execute_script", new ExecuteScriptCommand(this, new CollectionManager(new FileManager())));
    }
}