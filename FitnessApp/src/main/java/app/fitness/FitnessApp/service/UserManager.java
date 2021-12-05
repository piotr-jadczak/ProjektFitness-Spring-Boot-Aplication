package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.BaseUser;
import app.fitness.FitnessApp.login.UserForm;
import org.springframework.security.core.Authentication;

public interface UserManager {

    public BaseUser addUser(UserForm user);
    String getAuthorityName(Authentication authentication);
}
