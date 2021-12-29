package app.fitness.FitnessApp.domain.extra;

public class DayOfWeekExtension {

    public static String getStringValue(int i) {
        switch (i) {
            case 1: return "poniedziałek";
            case 2: return "wtorek";
            case 3: return "środa";
            case 4: return "czwartek";
            case 5: return "piątek";
            case 6: return "sobota";
            case 7: return "niedziela";
        }
        return "null";
    }
}
