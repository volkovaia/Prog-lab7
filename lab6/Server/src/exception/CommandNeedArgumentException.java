package exception;

/**
 * Команда не получила аргумент, который ожидался.
 */
public class CommandNeedArgumentException extends RuntimeException {
    /**
     * Instantiates a new Command need argument exception.
     */
    public CommandNeedArgumentException() {
        super("Этой команде необходим аргумент.");
    }

    /**
     * Instantiates a new Command need argument exception.
     *
     * @param message the message
     */
    public CommandNeedArgumentException(String message) {
        super(message);
    }
}