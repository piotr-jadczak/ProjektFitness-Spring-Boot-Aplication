package app.fitness.FitnessApp;

import app.fitness.FitnessApp.domain.*;
import app.fitness.FitnessApp.domain.extra.TrainingForm;
import app.fitness.FitnessApp.domain.extra.TrainingType;
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
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
									  @Autowired @Qualifier("club-prototypes") List<Club> clubs,
									  @Autowired @Qualifier("training-prototypes") List<TrainingForm> trainings
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
			//injecting add trainings
			{
				TrainingForm training = trainings.get(0);
				training.setCoach(userManager.findCoachByLogin("coach"));
				training.setClub(clubManager.getClub(1));
				training.setTrainingCategory(trainingManager.getTrainingCategory(3));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(1);
				training.setCoach(userManager.findCoachByLogin("coach2"));
				training.setClub(clubManager.getClub(1));
				training.setTrainingCategory(trainingManager.getTrainingCategory(1));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(2);
				training.setCoach(userManager.findCoachByLogin("coach2"));
				training.setClub(clubManager.getClub(2));
				training.setTrainingCategory(trainingManager.getTrainingCategory(3));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(3);
				training.setCoach(userManager.findCoachByLogin("coach"));
				training.setClub(clubManager.getClub(2));
				training.setTrainingCategory(trainingManager.getTrainingCategory(1));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(4);
				training.setCoach(userManager.findCoachByLogin("coach3"));
				training.setClub(clubManager.getClub(3));
				training.setTrainingCategory(trainingManager.getTrainingCategory(4));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(5);
				training.setCoach(userManager.findCoachByLogin("coach3"));
				training.setClub(clubManager.getClub(3));
				training.setTrainingCategory(trainingManager.getTrainingCategory(2));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(6);
				training.setCoach(userManager.findCoachByLogin("coach4"));
				training.setClub(clubManager.getClub(4));
				training.setTrainingCategory(trainingManager.getTrainingCategory(4));
				trainingManager.addTraining(training);
			}
			{
				TrainingForm training = trainings.get(7);
				training.setCoach(userManager.findCoachByLogin("coach4"));
				training.setClub(clubManager.getClub(4));
				training.setTrainingCategory(trainingManager.getTrainingCategory(2));
				trainingManager.addTraining(training);
			}
			//injecting enroll customer on training
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer"), 1);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer2"), 1);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer2"), 2);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer3"), 3);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer4"), 3);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer5"), 5);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer6"), 8);
			trainingManager.enrollCustomer(userManager.findCustomerByLogin("customer5"), 7);
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
		String description = "Najlepsza siłownia w całym trójmieście. Posiadamy najnowocześniejszy sprzęt i wykwalifikowany personel, który pomoże Ci w treningu. Duża sala i nowoczesny wystrój ułatwi codzienny trening.";
		clubs.add(new Club("Best gym", description, "Pomorska", "23/4", "Gdańsk"));
		String description2 = "Klub fitness dla każdego. Posiadamy szeroki wybór sprzętu do ćwiczeń, który sprawdzi się zarówno dla początkujących jak i zaawansowanych użytkowników. Dbaj o swoją kondycję rzem z nami.";
		clubs.add(new Club("Super fitness", description2, "Piastowska", "12A", "Gdańsk"));
		String description3 = "Sopocki Aquapark jest bardzo atrakcyjny zarówno dla dorosłych jak i dzieci. Posiada duży basen rekreacyjny wyposażony w bicze wodne, sztuczny prąd rzeczny, leżanki do hydromasażu i grotę z wodospadem.";
		clubs.add(new Club("Aquapark Sopot", description3, "Zamkowa", "3", "Sopot"));
		String description4 = "Najlepsze korty tenisowe przy samym morzu. Ćwicz swój forhend i bekhend podziwiając nadmorską przyrodę. Korty dostępne zarówno dla grup jak i klientów indywidualnych.";
		clubs.add(new Club("Outdoor tennis", description4, "Orłowska", "4", "Gdynia"));
		return clubs;
	}

	@Bean
	@Qualifier("training-prototypes")
	public List<TrainingForm> addTrainingPrototypes() {
		List<TrainingForm> trainings = new ArrayList<>();
		String description = "Ten trening przeznaczony jest dla osób z doświadczeniem w treningu siłowym. Nauczy Cię odpowiednich technik oddechowych i ruchowych dzięki którym będziesz mógł ćwiczyć dłużej.";
		List<RegularDate> dates = Arrays.asList(new RegularDate(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(18, 0)), new RegularDate(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(18, 0)), new RegularDate(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(18, 0)));
		trainings.add(new TrainingForm(TrainingType.REGULAR, "Trening wytrzymałościowy", description, 12, 50, dates, null));
		String description2 = "Podczas tych zajęć nauczę Cię jak poprawnie ćwiczyń mięsnie całego ciałą.";
		List<OneTimeDate> dates2 = Arrays.asList(new OneTimeDate(LocalDate.of(2022, 1, 25), LocalTime.of(18, 30), LocalTime.of(19, 30)), new OneTimeDate(LocalDate.of(2022, 1, 27), LocalTime.of(18, 30), LocalTime.of(19, 30)));
		trainings.add(new TrainingForm(TrainingType.ONETIME, "Trening obwodowy", description2, 1, 150, null, dates2));
		String description3 = "Zrealizuj swoje postanowienie noworoczne i zgub zbędne kilogramy.";
		List<RegularDate> dates3 = Arrays.asList(new RegularDate(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(18, 0)), new RegularDate(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(18, 0)));
		trainings.add(new TrainingForm(TrainingType.REGULAR, "Trening odchudzający", description3, 20, 40, dates3, null));
		String description4 = "Ćwiczenia ogólnorozwojowe to takie, które angażują do pracy najważniejsze partie całego ciała jednocześnie, w trakcie jednej sesji.";
		List<OneTimeDate> dates4 = Arrays.asList(new OneTimeDate(LocalDate.of(2022, 1, 18), LocalTime.of(18, 30), LocalTime.of(19, 30)), new OneTimeDate(LocalDate.of(2022, 1, 20), LocalTime.of(18, 30), LocalTime.of(19, 30)));
		trainings.add(new TrainingForm(TrainingType.ONETIME, "Trening ogólnorozwojowy", description4, 1, 120, null, dates4));
		String description5 = "Łączymy zabawę z nauką. Podczas tych zajęć twoje dziecko nauczy się pływać najpopularniejszymy stylami a jednocześnie będzie się świetnie bawić.";
		List<RegularDate> dates5 = Arrays.asList(new RegularDate(DayOfWeek.TUESDAY, LocalTime.of(18, 15), LocalTime.of(19, 45)), new RegularDate(DayOfWeek.THURSDAY, LocalTime.of(18, 15), LocalTime.of(19, 45)));
		trainings.add(new TrainingForm(TrainingType.REGULAR, "Nauka pływnaia", description5, 6, 90, dates5, null));
		String description6 = "Nauka pływania dla dzieic i młodzieży dla każdego stopnia zaawansowania.";
		List<RegularDate> dates6 = Arrays.asList(new RegularDate(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)));
		trainings.add(new TrainingForm(TrainingType.REGULAR, "Nauka pływnaia", description6, 1, 100, dates6, null));
		String description7 = "Ucz się gry w tenisa ziemnego od najlepszych.";
		trainings.add(new TrainingForm(TrainingType.REGULAR,"Nauka gry w tenisa ziemnego", description7, 4, 200, dates, null));
		trainings.add(new TrainingForm(TrainingType.REGULAR,"Nauka gry w tenisa ziemnego", description7, 1, 120, dates6, null));
		return trainings;
	}

}
