package app.fitness.FitnessApp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Coach extends BaseUser {

	
	@OneToMany(mappedBy = "coach")
	private List<Training> trainings;

	@ManyToMany
	private List<Club> clubs;

	public Coach() {
		super();
	}

	public Coach(String login, String password, String email, String firstName, String lastName, Date dob,
			String phoneNumber) {
		super(login, password, email, firstName, lastName, dob, phoneNumber);
	}

	public Coach(BaseUser baseUser) {
		super(baseUser);
	}


	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public String toString() {
		return "Coach [trainings=" + trainings + ", clubs=" + clubs + "]";
	}

	

}
