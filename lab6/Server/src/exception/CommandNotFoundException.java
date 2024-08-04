package exception;

/**
 * Команда не найдена в списке доступных команд.
 */
public class CommandNotFoundException extends Exception {
    private static final String message = "Команда не найдена: ";

    /**
     * Instantiates a new Command not found exception.
     *
     * @param s the s
     */
    public CommandNotFoundException(String s) {
        super(message + s);
    }
}