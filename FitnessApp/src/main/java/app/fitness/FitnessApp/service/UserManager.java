package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.BaseUser;
import app.fitness.FitnessApp.domain.BaseUserLogin;

public interface UserManager {

    public BaseUser addUser(BaseUserLogin user);
}
