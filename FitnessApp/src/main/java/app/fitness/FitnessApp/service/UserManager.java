package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.*;
import app.fitness.FitnessApp.domain.extra.ProfileForm;
import app.fitness.FitnessApp.login.UserForm;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Stream;

public interface UserManager {

    BaseUser addUser(UserForm user);
    String getAuthorityName(Authentication authentication);
    Owner findOwnerByLogin(String login);
    Coach findCoachById(int id);
    Coach findCoachByLogin(String login);
    Customer findCustomerByLogin(String login);
    ProfileForm castToProfileForm(BaseUser user);
    void updateUserDetails(ProfileForm profileForm, String login);
}
