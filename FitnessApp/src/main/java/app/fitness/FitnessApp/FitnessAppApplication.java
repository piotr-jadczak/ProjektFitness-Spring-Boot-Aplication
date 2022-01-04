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

import javax.transaction.Transactional;
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
									  @Autowired ClubManager clubManager,
									  @Autowired TrainingManager trainingManager,
									  @Autowired @Qualifier("customer-prototypes") List<UserForm> customers,
									  @Autowired @Qualifier("coach-prototypes") List<UserForm> coaches,
									  @Autowired @Qualifier("owner-prototypes") List<UserForm> owners,
									  @Autowired @Qualifier("club-prototypes") List<Club> clubs
									  ) {
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
			//injecting clubs
			{
				Club club = clubs.get(0);
				club.setOwner(userManager.findOwnerByLogin("owner"));
				club.setClubCategory(clubManager.getAllCategories().get(0));
				clubManager.addClub(club);
			}
			{
				Club club = clubs.get(1);
				club.setOwner(userManager.findOwnerByLogin("owner"));
				club.setClubCategory(clubManager.getAllCategories().get(1));
				clubManager.addClub(club);
			}
			{
				Club club = clubs.get(2);
				club.setOwner(userManager.findOwnerByLogin("owner2"));
				club.setClubCategory(clubManager.getAllCategories().get(3));
				clubManager.addClub(club);
			}
			{
				Club club = clubs.get(3);
				club.setOwner(userManager.findOwnerByLogin("owner3"));
				club.setClubCategory(clubManager.getAllCategories().get(4));
				clubManager.addClub(club);
			}
			//injecting add coaches to clubs
			clubManager.injectAddCoachToClub(userManager.findCoachByLogin("coach"), clubManager.getClub(1));
			clubManager.injectAddCoachToClub(userManager.findCoachByLogin("coach"), clubManager.getClub(2));
			clubManager.injectAddCoachToClub(userManager.findCoachByLogin("coach2"), clubManager.getClub(1));
			clubManager.injectAddCoachToClub(userManager.findCoachByLogin("coach2"), clubManager.getClub(2));
			clubManager.injectAddCoachToClub(userManager.findCoachByLogin("coach3"), clubManager.getClub(3));
			clubManager.injectAddCoachToClub(userManager.findCoachByLogin("coach4"), clubManager.getClub(4));

		};
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


	@Bean
	@Qualifier("club-prototype")
	public Club addClubPrototype() {
		Club club = new Club("Best gym", "To najlepsza siłownia w Trojmieście", "Pomorska", "23/4", "Gdańsk");
		return club;
	}

	@Bean
	@Qualifier("club-prototypes")
	public List<Club> addClubPrototypes() {
		List<Club> clubs = new ArrayList<>();
		String description = "Najlepsza siłownia w całym trójmieście. Posiadamy najnowocześniejszy sprzęt i wykwalifikowany personel, który pomoże Ci w treningu. Duża sala i nowoczesny wystrój ułatwi codzienny trening";
		clubs.add(new Club("Best gym", description, "Pomorska", "23/4", "Gdańsk"));
		String description2 = "Klub fitness dla każdego. Posiadamy szeroki wybór sprzętu do ćwiczeń, który sprawdzi się zarówno dla początkujących jak i zaawansowanych użytkowników. Dbaj o swoją kondycję rzem z nami";
		clubs.add(new Club("Super fitness", description2, "Piastowska", "12A", "Gdańsk"));
		String description3 = "Sopocki Aquapark jest bardzo atrakcyjny zarówno dla dorosłych jak i dzieci. Posiada duży basen rekreacyjny wyposażony w bicze wodne, sztuczny prąd rzeczny, leżanki do hydromasażu i grotę z wodospadem.";
		clubs.add(new Club("Aquapark Sopot", description3, "Zamkowa", "3", "Sopot"));
		String description4 = "Najlepsze korty tenisowe przy samym morzu. Ćwicz swój forhend i bekhend podziwiając nadmorską przyrodę. Korty dostępne zarówno dla grup jak i klientów indywidualnych.";
		clubs.add(new Club("Outdoor tennis", description4, "Orłowska", "4", "Gdynia"));
		return clubs;
	}

}
