package app.fitness.FitnessApp.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Coach extends BaseUser {

	
	@OneToMany(mappedBy = "coach")
	private Set<Training> trainings;

	@ManyToMany
	private Set<Club> clubs;

	public Coach() {
		super();
	}

	public Coach(String login, String password, String email, String firstName, String lastName, LocalDate dob,
			String phoneNumber) {
		super(login, password, email, firstName, lastName, dob, phoneNumber);
	}

	public Coach(BaseUser baseUser) {
		super(baseUser);
	}


	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	public Set<Club> getClubs() {
		return clubs;
	}

	public void setClubs(Set<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public String toString() {
		return "Coach [trainings=" + trainings + ", clubs=" + clubs + "]";
	}

	

}
