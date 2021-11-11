package app.fitness.FitnessApp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ClubCategory {

	@Id
	@GeneratedValue
	private int id;
    private String name;

    @OneToMany(mappedBy = "clubCategory")
    private List<Club> clubs;

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

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	@Override
	public String toString() {
		return "ClubCategory [id=" + id + ", name=" + name + ", clubs=" + clubs + "]";
	}
    
    
}
