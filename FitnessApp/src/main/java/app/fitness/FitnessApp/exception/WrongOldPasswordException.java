package app.fitness.FitnessApp.exception;

public class WrongOldPasswordException extends RuntimeException {
    public WrongOldPasswordException(String message) {
        super(message);
    }

    public WrongOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
