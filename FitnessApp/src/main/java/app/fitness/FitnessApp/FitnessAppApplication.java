package app.fitness.FitnessApp;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.TrainingCategory;
import app.fitness.FitnessApp.domain.extra.DayOfWeek;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class FitnessAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(FitnessAppApplication.class, args);

	}

	@Bean
	public CommandLineRunner setupApp(@Autowired UserManager userManager,
									  @Autowired @Qualifier("customer-prototypes") List<UserForm> customers,
									  @Autowired @Qualifier("coach-prototypes") List<UserForm> coaches,
									  @Autowired @Qualifier("owner-prototypes") List<UserForm> owners,
									  @Autowired ClubManager clubManager,
									  @Autowired @Qualifier("club-prototype") Club club,
									  @Autowired TrainingManager trainingManager) {
		return args -> {
			System.out.println("Application test SetUp");
			//injecting user
			for(UserForm u : customers)
				userManager.addUser(u);
			for(UserForm u : coaches)
				userManager.addUser(u);
			for(UserForm u : owners)
				userManager.addUser(u);
			//injecting club categories
			clubManager.addClubCategory(new ClubCategory("siłownia"));
			clubManager.addClubCategory(new ClubCategory("klub fitness"));
			clubManager.addClubCategory(new ClubCategory("basen"));
			clubManager.addClubCategory(new ClubCategory("aquapark"));
			clubManager.addClubCategory(new ClubCategory("kort tenisowy"));
			//injecting training categories
			trainingManager.addTrainingCategory(new TrainingCategory("Trening personalny"));
			trainingManager.addTrainingCategory(new TrainingCategory("Trening personalny dla dzieci"));
			trainingManager.addTrainingCategory(new TrainingCategory("Trening grupowy"));
			trainingManager.addTrainingCategory(new TrainingCategory("Trening grupowy dla dzieci"));
			//
			club.setOwner(userManager.findOwnerByLogin("owner"));
			club.setClubCategory(clubManager.getAllCategories().get(0));
			clubManager.addClub(club);

		};
	}

	@Bean
	@Qualifier("club-prototype")
	public Club addClubPrototype() {
		Club club = new Club("Best gym", "To najlepsza siłownia w Trojmieście", "Pomorska", "23/4", "Gdańsk");
		return club;
	}

	@Bean
	@Qualifier("customer-prototypes")
	public List<UserForm> addCustomerPrototypes() {
		List<UserForm> customers = new ArrayList<>();
		customers.add(new UserForm("customer", "password", "jan.kowalski@gmail.com", "Jan", "Kowalski", LocalDate.of(1990, 12, 23), "424123505", UserType.CUSTOMER));
		customers.add(new UserForm("customer2", "password", "bartosz.kowalski@gmail.com", "Bartosz", "Kowalski", LocalDate.of(1999, 2, 10), "522123505", UserType.CUSTOMER));
		customers.add(new UserForm("customer3", "password", "ignacy.walczak@wp.com", "Ignacy", "Walczak", LocalDate.of(2003, 1, 11), "482123505", UserType.CUSTOMER));
		customers.add(new UserForm("customer4", "password", "krzysztof.czerwinski@wp.com", "Krzysztof", "Czerwiński", LocalDate.of(1995, 5, 6), "612123505", UserType.CUSTOMER));
		customers.add(new UserForm("customer5", "password", "anita.adamska@gmail.com", "Anita", "Adamska", LocalDate.of(1993, 3, 8), "212123505", UserType.CUSTOMER));
		customers.add(new UserForm("customer6", "password", "teresa.tomaszewska@gmail.com", "Teresa", "Tomaszewska", LocalDate.of(1992, 4, 5), "712123505", UserType.CUSTOMER));
		return customers;
	}

	@Bean
	@Qualifier("coach-prototypes")
	public List<UserForm> addCoachPrototypes() {
		List<UserForm> coaches = new ArrayList<>();
		coaches.add(new UserForm("coach", "password", "marek.dabrowski@gmail.com", "Marek", "Dąbrowski", LocalDate.of(1992, 4, 11) , "343112615", UserType.COACH));
		coaches.add(new UserForm("coach2", "password", "martyna.kowalczyk@gmail.com", "Martyna", "Kowlaczyk", LocalDate.of(1998, 2, 4) , "543112615", UserType.COACH));
		coaches.add(new UserForm("coach3", "password", "aneta.jawor@gmail.com", "Aneta", "Jaworska", LocalDate.of(1991, 3, 12) , "643112615", UserType.COACH));
		coaches.add(new UserForm("coach4", "password", "mikolaj.sz@wp.com", "Mikołaj", "Szczepański", LocalDate.of(1988, 7, 21) , "483112615", UserType.COACH));
		return coaches;
	}

	@Bean
	@Qualifier("owner-prototypes")
	public List<UserForm> addOwnerPrototypes() {
		List<UserForm> owners = new ArrayList<>();
		owners.add(new UserForm("owner", "password", "kamil.nowak@gmail.com", "Kamil", "Nowak", LocalDate.of(1992, 4, 11) , "533123412", UserType.OWNER));
		owners.add(new UserForm("owner2", "password", "czeslaw.borkos@gmail.com", "Czesław", "Borkowski", LocalDate.of(1989, 2, 3) , "551123412", UserType.OWNER));
		owners.add(new UserForm("owner3", "password", "emaunel.wys@wp.com", "Emanuel", "Wysocki", LocalDate.of(1981, 5, 10) , "623123412", UserType.OWNER));
		return owners;
	}

}
