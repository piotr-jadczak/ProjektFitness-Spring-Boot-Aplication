package app.fitness.FitnessApp.domain;

import app.fitness.FitnessApp.domain.extra.DayOfWeek;
import app.fitness.FitnessApp.domain.extra.TrainingForm;
import app.fitness.FitnessApp.domain.extra.TrainingType;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "training")
public class Training {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private TrainingType trainingType;
    private String name;
    private String description;
    private int maxParticipants;
	private int currentParticipants = 0;

    private double price;

    @ManyToOne
    private Club club;
    
    @ManyToOne
    private TrainingCategory trainingCategory;
    
    @ManyToOne
    private Coach coach;

    @ManyToMany
    @JoinTable (
            name = "customer_training",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "training_one_time_dates", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "one_time_date")
    private List<OneTimeDate> oneTimeDates;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "training_regular_dates", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "regular_date")
    private List<RegularDate> regularDates;

	public Training() {
	}

    public Training(TrainingType trainingType, String name, String description, int maxParticipants,
                    int currentParticipants, double price, Club club, TrainingCategory trainingCategory, Coach coach) {
        this.trainingType = trainingType;
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.price = price;
        this.club = club;
        this.trainingCategory = trainingCategory;
        this.coach = coach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public TrainingCategory getTrainingCategory() {
        return trainingCategory;
    }

    public void setTrainingCategory(TrainingCategory trainingCategory) {
        this.trainingCategory = trainingCategory;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public boolean isTrainingFull() {
        return availableSpots() == 0;
    }

    private int availableSpots() {
        return this.getMaxParticipants() - this.getCurrentParticipants();
    }

    public String getEnrolledCustomers() {
        return this.getCurrentParticipants() + "/" + this.getMaxParticipants();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
            customers.remove(customer);
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public List<OneTimeDate> getOneTimeDates() {
        return oneTimeDates;
    }

    public void setOneTimeDates(List<OneTimeDate> oneTimeDates) {
        this.oneTimeDates = oneTimeDates;
    }

    public List<RegularDate> getRegularDates() {
        return regularDates;
    }

    public void setRegularDates(List<RegularDate> regularDates) {
        this.regularDates = regularDates;
    }

    public void addOneTimeDate(OneTimeDate oneTimeDate) {
        oneTimeDates.add(oneTimeDate);
    }

    public void removeOneTimeDate(OneTimeDate oneTimeDate) {
        oneTimeDates.remove(oneTimeDate);
    }

    public void addRegularDate(RegularDate regularDate) {
        regularDates.add(regularDate);
    }

    public void removeRegularDate(RegularDate regularDate) {
        regularDates.remove(regularDate);
    }

    public void update(TrainingForm trainingForm) {
        oneTimeDates.clear();
        regularDates.clear();
        this.trainingType = trainingForm.getTrainingType();
        this.name = trainingForm.getName();
        this.description = trainingForm.getDescription();
        this.maxParticipants = trainingForm.getMaxParticipants();
        this.price = trainingForm.getPrice();
        this.club = trainingForm.getClub();
        this.trainingCategory = trainingForm.getTrainingCategory();
    }
}
