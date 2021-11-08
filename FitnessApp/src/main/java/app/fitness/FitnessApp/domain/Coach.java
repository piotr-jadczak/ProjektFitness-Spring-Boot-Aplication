package app.fitness.FitnessApp.domain;

import java.time.LocalDateTime;

public class Coach extends User {

    public Coach() {
    }

    public Coach(String userName, String password, String email, String firstName, String lastName, LocalDateTime doB, String phoneNumber) {
        super(userName, password, email, firstName, lastName, doB, phoneNumber);

    }


}
