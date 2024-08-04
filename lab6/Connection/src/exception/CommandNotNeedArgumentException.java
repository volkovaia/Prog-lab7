package exception;

/**
 * The type Command not need argument exception.
 */
public class CommandNotNeedArgumentException extends Exception {
    /**
     * Instantiates a new Command not need argument exception.
     */
    public CommandNotNeedArgumentException() {
        super("Этой команде не нужны аргументы.");
    }

}