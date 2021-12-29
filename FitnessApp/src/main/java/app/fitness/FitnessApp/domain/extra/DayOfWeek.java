package app.fitness.FitnessApp.domain.extra;

public enum DayOfWeek {
    MONDAY("poniedziałek"),
    TUESDAY("wtorek"),
    WEDNESDAY("środa"),
    THURSDAY("czwartek"),
    FRIDAY("piątek"),
    SATURDAY("sobota"),
    SUNDAY("niedziela");

    private final String displayValue;

    private DayOfWeek(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }


}
