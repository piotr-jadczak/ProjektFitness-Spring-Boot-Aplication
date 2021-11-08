package app.fitness.FitnessApp.domain;

import java.time.LocalDateTime;

public class Owner extends User{

    public Owner() {
    }

    public Owner(String userName, String password, String email, String firstName, String lastName, LocalDateTime doB, String phoneNumber) {
        super(userName, password, email, firstName, lastName, doB, phoneNumber);
    }
}
