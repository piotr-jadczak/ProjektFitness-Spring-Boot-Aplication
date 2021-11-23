package app.fitness.FitnessApp;

import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.login.BaseUserLogin;
import app.fitness.FitnessApp.domain.login.UserType;
import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FitnessAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(FitnessAppApplication.class, args);

	}

}
