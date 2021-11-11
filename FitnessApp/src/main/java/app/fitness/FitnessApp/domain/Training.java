package app.fitness.FitnessApp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Training {

	@Id
	@GeneratedValue
    private int id;
    private String name;
    private String description;
    private int maxNumberOfUsers;
    private Date startTime;
    private Date endTime;
    private int pointReward;
    private double price;

    @ManyToOne
    private Club club;
    
    @ManyToOne
    private TrainingCategory trainingCategory;
    
    @ManyToOne
    private Coach coach;
    
    @ManyToMany(mappedBy = "trainings")
    private List<Customer> customers;

	public Training() {
	}

	public Training(String name, String description, int maxNumberOfUsers, Date startTime, Date endTime,
			int pointReward, double price) {
		super();
		this.name = name;
		this.description = description;
		this.maxNumberOfUsers = maxNumberOfUsers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pointReward = pointReward;
		this.price = price;
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

	public int getMaxNumberOfUsers() {
		return maxNumberOfUsers;
	}

	public void setMaxNumberOfUsers(int maxNumberOfUsers) {
		this.maxNumberOfUsers = maxNumberOfUsers;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getPointReward() {
		return pointReward;
	}

	public void setPointReward(int pointReward) {
		this.pointReward = pointReward;
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

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", name=" + name + ", description=" + description + ", maxNumberOfUsers="
				+ maxNumberOfUsers + ", startTime=" + startTime + ", endTime=" + endTime + ", pointReward="
				+ pointReward + ", price=" + price + ", club=" + club + ", trainingCategory=" + trainingCategory
				+ ", coach=" + coach + ", customers=" + customers + "]";
	}
    
    
}
