//import commands.AcceptInteger;
import exception.CommandNotFoundException;
import exception.ExitCommandRaised;
import exception.UnknownUserException;
import exception.UnknownPasswordException;
import lombok.Data;

        import java.io.Serializable;

@Data
public class CommandData implements Serializable {

    private static final long serialVersionUID = 132454324L;

    private String command = "";
    private String args = "";
    private String userName = "";
    private String password = "";

    public CommandData(String input) throws CommandNotFoundException, UnknownUserException, UnknownPasswordException, ExitCommandRaised {
        String[] commandSplit = input.split(" ", 4);
        String userName = commandSplit[0];

        if (userName == null) {
            throw new UnknownUserException();
        }
        this.userName = userName;
        String commandArguments = "";

        if (commandSplit.length == 1) {
            if (userName.equals("exit")) {throw new ExitCommandRaised();}
            throw new ArrayIndexOutOfBoundsException();
        } else if (commandSplit.length == 2) {
            throw new UnknownPasswordException();
        } else {
            this.password = commandSplit[1];
            this.command = commandSplit[2];
            if (commandSplit.length == 4) {
                commandArguments = commandSplit[3];
            } else if (this.command.equals("")) {
                throw new CommandNotFoundException(this.command);
            }
        }
        this.args = commandArguments;

    }
    public String returnArgs(){
        return args;
    }
    public String returnCommandName(){
        return this.command;
    }
    public String returnUserName() {return this.userName; }
    public String returnUserPassword() {return this.password; }

    //вызывает интерфейс и проверяет на корректность введённых данных
}
