package app.fitness.FitnessApp.domain;

import java.time.LocalDateTime;

public class Coach extends BaseUser {

    private int CoachId;

    public Coach() {
    }

    public Coach(String userName, String password, String email, String firstName, String lastName, LocalDateTime doB, String phoneNumber) {
        super(userName, password, email, firstName, lastName, doB, phoneNumber);

    }

    public int getCoachId() {
        return CoachId;
    }

    public void setCoachId(int coachId) {
        CoachId = coachId;
    }
}
