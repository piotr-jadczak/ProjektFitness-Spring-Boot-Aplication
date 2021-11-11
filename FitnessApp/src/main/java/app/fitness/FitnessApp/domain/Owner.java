package app.fitness.FitnessApp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Owner extends BaseUser {

	
	@OneToMany(mappedBy = "owner")
	private List<Club> clubs;

	public Owner() {
		super();
	}

	public Owner(String login, String password, String email, String firstName, String lastName, Date dob,
			String phoneNumber) {
		super(login, password, email, firstName, lastName, dob, phoneNumber);
	}

	

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public String toString() {
		return "Owner [clubs=" + clubs + "]";
	}


}
