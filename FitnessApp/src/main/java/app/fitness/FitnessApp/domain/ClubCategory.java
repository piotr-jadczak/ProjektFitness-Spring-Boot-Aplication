package app.fitness.FitnessApp.domain;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "club_category")
public class ClubCategory {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@NonNull
    private String name;

    @OneToMany(mappedBy = "clubCategory")
    private Set<Club> clubs;

	public ClubCategory() {
	}

	public ClubCategory(String name) {
		super();
		this.name = name;
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

	public Set<Club> getClubs() {
		return clubs;
	}

	public void setClubs(Set<Club> clubs) {
		this.clubs = clubs;
	}

}
