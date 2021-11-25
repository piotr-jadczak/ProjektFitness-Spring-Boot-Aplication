package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.BaseUser;
import app.fitness.FitnessApp.login.BaseUserLogin;

public interface UserManager {

    public BaseUser addUser(BaseUserLogin user);
}
