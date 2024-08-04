package exception;

/**
 * Нет имени пользователя
 */
public class UnknownUserException extends RuntimeException {
    private static final String message = "Имя пользователя не найдено при обращении к серверу";
    public UnknownUserException() {
        super(message);
    }

    public void getMessage(String s) {
    }
}
