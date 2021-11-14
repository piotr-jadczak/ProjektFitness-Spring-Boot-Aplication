package app.fitness.FitnessApp.domain;

public enum UserType {
    CUSTOMER("Użytkownik"),
    OWNER("Właściciel obiektu"),
    COACH("Trener");

    private final String displayValue;

    private UserType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
