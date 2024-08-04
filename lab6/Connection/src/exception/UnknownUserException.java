package exception;

/**
 * Нет имени пользователя
 */
public class UnknownUserException extends Exception {
    private static final String message = "Имя пользователя не найдено";
    public UnknownUserException() {
        super(message);
    }

    public void getMessage(String s) {
    }
}
