package app.fitness.FitnessApp.domain;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends BaseUser {

	@ManyToMany(mappedBy = "customers", fetch = FetchType.EAGER)
	private Set<Training> trainings;

	public Customer() {
		super();
	}

	public Customer(String login, String password, String email, String firstName, String lastName, LocalDate dob,
			String phoneNumber) {
		super(login, password, email, firstName, lastName, dob, phoneNumber);

	}

	public Customer(BaseUser baseUser) {
		super(baseUser);
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	public void addTraining(Training training) {
		trainings.add(training);
	}

	public void removeTraining(Training training) {
		trainings.remove(training);
	}


}
