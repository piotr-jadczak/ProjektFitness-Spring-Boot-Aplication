package app.fitness.FitnessApp.domain.extra;

public enum TrainingType {
    BLANK("--wybierz typ treningu--"),
    REGULAR("regularny"),
    ONETIME("jednorazowy");

    private final String displayValue;

    private TrainingType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
