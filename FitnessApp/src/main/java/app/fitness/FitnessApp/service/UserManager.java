package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.BaseUser;
import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.login.UserForm;
import org.springframework.security.core.Authentication;

public interface UserManager {

    BaseUser addUser(UserForm user);
    String getAuthorityName(Authentication authentication);
    Owner findOwnerByLogin(String login);
    Coach findCoachById(int id);
    Coach findCoachByLogin(String login);
}
