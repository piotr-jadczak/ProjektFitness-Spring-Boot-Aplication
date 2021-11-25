package app.fitness.FitnessApp.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends BaseUser {

	@ManyToMany
	private Set<Training> trainings;

	public Customer() {
		super();
	}

	public Customer(String login, String password, String email, String firstName, String lastName, Date dob,
			String phoneNumber) {
		super(login, password, email, firstName, lastName, dob, phoneNumber);

	}

	public Customer(BaseUser baseUser) {
		super(baseUser);
	};

	

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	@Override
	public String toString() {
		return "User [trainings=" + trainings + "]";
	}

}
