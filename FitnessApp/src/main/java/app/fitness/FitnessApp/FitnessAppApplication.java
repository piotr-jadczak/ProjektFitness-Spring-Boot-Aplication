package app.fitness.FitnessApp;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.TrainingCategory;
import app.fitness.FitnessApp.login.UserForm;
import app.fitness.FitnessApp.login.UserType;
import app.fitness.FitnessApp.service.ClubManager;
import app.fitness.FitnessApp.service.TrainingManager;
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
									  @Autowired ClubManager clubManager,
									  @Autowired @Qualifier("coach-prototype2") UserForm coach2,
									  @Autowired @Qualifier("coach-prototype3") UserForm coach3,
									  @Autowired @Qualifier("club-prototype") Club club,
									  @Autowired TrainingManager trainingManager) {
		return args -> {
			System.out.println("Application test SetUp");
			userManager.addUser(customer);
			userManager.addUser(coach);
			userManager.addUser(owner);
			userManager.addUser(coach2);
			userManager.addUser(coach3);
			clubManager.addClubCategory(new ClubCategory("siłownia"));
			clubManager.addClubCategory(new ClubCategory("klub fitness"));
			clubManager.addClubCategory(new ClubCategory("basen"));
			clubManager.addClubCategory(new ClubCategory("aquapark"));
			clubManager.addClubCategory(new ClubCategory("kort tenisowy"));
			club.setOwner(userManager.findOwnerByLogin("owner"));
			club.setClubCategory(clubManager.getAllCategories().get(0));
			clubManager.addClub(club);
			trainingManager.addTrainingCategory(new TrainingCategory("Trening personalny"));
			trainingManager.addTrainingCategory(new TrainingCategory("Trening grupowy"));
			trainingManager.addTrainingCategory(new TrainingCategory("Trening personalny dla dzieci"));
			trainingManager.addTrainingCategory(new TrainingCategory("Trening grupowy dla dzieci"));

		};
	}

	@Bean
	@Qualifier("customer-prototype")
	public UserForm addCustomerPrototype() {
		UserForm customer = new UserForm("customer", "password", "jan1@gmail.com", "Jan", "Kowalski", null , "123456789", UserType.CUSTOMER);
		return customer;
	}

	@Bean
	@Qualifier("coach-prototype")
	public UserForm addCoachPrototype() {
		UserForm coach = new UserForm("coach", "password", "jan2@gmail.com", "Marek", "Dąbrowski", null , "123456789", UserType.COACH);
		return coach;
	}

	@Bean
	@Qualifier("owner-prototype")
	public UserForm addOwnerPrototype() {
		UserForm owner = new UserForm("owner", "password", "jan3@gmail.com", "Kamil", "Nowak", null , "123456789", UserType.OWNER);
		return owner;
	}

	@Bean
	@Qualifier("coach-prototype2")
	public UserForm addCoachPrototype2() {
		UserForm coach = new UserForm("coach2", "password", "jan4@gmail.com", "Martyna", "Kowlaczyk", null , "123456789", UserType.COACH);
		return coach;
	}

	@Bean
	@Qualifier("coach-prototype3")
	public UserForm addCoachPrototype3() {
		UserForm coach = new UserForm("coach3", "password", "jan5@gmail.com", "Aneta", "Jaworska", null , "123456789", UserType.COACH);
		return coach;
	}

	@Bean
	@Qualifier("club-prototype")
	public Club addClubPrototype() {
		Club club = new Club("Best gym", "To najlepsza siłownia w Trojmieście", "Pomorska", "23/4", "Gdańsk");
		return club;
	}

}
