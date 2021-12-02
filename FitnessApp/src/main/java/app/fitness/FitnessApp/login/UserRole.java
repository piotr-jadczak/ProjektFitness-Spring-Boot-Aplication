package app.fitness.FitnessApp.login;

public enum UserRole {
    CUSTOMER("ROLE_CUSTOMER"),
    COACH("ROLE_COACH"),
    OWNER("ROLE_OWNER");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
