package app.fitness.FitnessApp.exception;

public class CoachNotInAnyClubException extends RuntimeException {
    public CoachNotInAnyClubException(String message) {
        super(message);
    }

    public CoachNotInAnyClubException(String message, Throwable cause) {
        super(message, cause);
    }
}
