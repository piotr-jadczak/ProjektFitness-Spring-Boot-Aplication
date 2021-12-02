package app.fitness.FitnessApp.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner extends BaseUser {

	
	@OneToMany(mappedBy = "owner")
	private Set<Club> clubs;

	public Owner() {
		super();
	}

	public Owner(String login, String password, String email, String firstName, String lastName, LocalDate dob,
			String phoneNumber) {
		super(login, password, email, firstName, lastName, dob, phoneNumber);
	}

	public Owner (BaseUser baseUser) {
		super(baseUser);
	}

	public Set<Club> getClubs() {
		return clubs;
	}

	public void setClubs(Set<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public String toString() {
		return "Owner [clubs=" + clubs + "]";
	}


}
