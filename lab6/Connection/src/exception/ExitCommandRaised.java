package exception;

/**
 * Нет имени пользователя
 */
public class ExitCommandRaised extends Exception {
    private static final String message = "Остановка программы без авторизации пользователя";
    public ExitCommandRaised() {
        super(message);
    }

    public void getMessage(String s) {
    }
}
