package app.fitness.FitnessApp.domain;

import app.fitness.FitnessApp.domain.extra.DayOfWeek;

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "training")
public class Training {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int maxParticipants;
	private int currentParticipants = 0;

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    private double price;

    @ManyToOne
    private Club club;
    
    @ManyToOne
    private TrainingCategory trainingCategory;
    
    @ManyToOne
    private Coach coach;
    
    @ManyToMany(mappedBy = "trainings")
    private Set<Customer> customers;

	public Training() {
	}

    public Training(String name, String description, int maxParticipants, int currentParticipants, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, double price, Club club, TrainingCategory trainingCategory, Coach coach, Set<Customer> customers) {
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.club = club;
        this.trainingCategory = trainingCategory;
        this.coach = coach;
        this.customers = customers;
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
}
