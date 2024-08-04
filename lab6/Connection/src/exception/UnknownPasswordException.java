package exception;

/**
 * Нет имени пользователя
 */
public class UnknownPasswordException extends Exception {
    private static final String message = "Пароль пользователя не найден";
    public UnknownPasswordException() {
        super(message);
    }

    public void getMessage(String s) {
    }
}
