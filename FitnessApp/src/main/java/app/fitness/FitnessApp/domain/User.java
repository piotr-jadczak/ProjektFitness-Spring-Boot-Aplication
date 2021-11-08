package app.fitness.FitnessApp.domain;

import java.time.LocalDateTime;

public class User extends BaseUser{

    private int UserId;

    public User() {
    }

    public User(String userName, String password, String email, String firstName, String lastName, LocalDateTime doB, String phoneNumber) {
        super(userName, password, email, firstName, lastName, doB, phoneNumber);
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
