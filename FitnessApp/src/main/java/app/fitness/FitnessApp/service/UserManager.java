package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.BaseUser;
import app.fitness.FitnessApp.login.BaseUserLogin;
import org.springframework.security.core.Authentication;

public interface UserManager {

    public BaseUser addUser(BaseUserLogin user);
    String getAuthorityName(Authentication authentication);
}
