package app.fitness.FitnessApp;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.login.UserForm;
import app.fitness.FitnessApp.login.UserType;
import app.fitness.FitnessApp.service.ClubManager;
import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class FitnessAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(FitnessAppApplication.class, args);

	}

	@Bean
	public CommandLineRunner setupApp(@Autowired UserManager userManager,
									  @Autowired @Qualifier("customer-prototype") UserForm customer,
									  @Autowired @Qualifier("coach-prototype") UserForm coach,
									  @Autowired @Qualifier("owner-prototype") UserForm owner,
									  @Autowired ClubManager clubManager) {
		return args -> {
			System.out.println("Application test SetUp");
			userManager.addUser(customer);
			userManager.addUser(coach);
			userManager.addUser(owner);
			clubManager.addClubCategory(new ClubCategory("siłownia"));
			clubManager.addClubCategory(new ClubCategory("klub fitness"));
			clubManager.addClubCategory(new ClubCategory("basen"));
			clubManager.addClubCategory(new ClubCategory("aquapark"));
			clubManager.addClubCategory(new ClubCategory("kort tenisowy"));
		};
	}

	@Bean
	@Qualifier("customer-prototype")
	public UserForm addCustomerPrototype() {
		UserForm customer = new UserForm("customer", "password", "jan@gmail.com", "Jan", "Kowalski", null , "123456789", UserType.CUSTOMER);
		return customer;
	}

	@Bean
	@Qualifier("coach-prototype")
	public UserForm addCoachPrototype() {
		UserForm coach = new UserForm("coach", "password", "jan@gmail.com", "Marek", "Dąbrowski", null , "123456789", UserType.COACH);
		return coach;
	}

	@Bean
	@Qualifier("owner-prototype")
	public UserForm addOwnerPrototype() {
		UserForm owner = new UserForm("owner", "password", "jan@gmail.com", "Kamil", "Nowak", null , "123456789", UserType.OWNER);
		return owner;
	}

}
