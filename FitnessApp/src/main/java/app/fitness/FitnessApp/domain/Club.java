package app.fitness.FitnessApp.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Club {

	@Id
	@GeneratedValue
	private int id;
    private String name;
    private String description;
    private String address;

    @ManyToOne
    private Owner owner;
    
    @ManyToOne
    private ClubCategory clubCategory;
    
    @ManyToMany(mappedBy = "clubs")
    private Set<Coach> coaches;
    
    @OneToMany(mappedBy = "club")
    private Set<Training> trainings;

	public Club() {
	}

	public Club(String name, String description, String address) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public ClubCategory getClubCategory() {
		return clubCategory;
	}

	public void setClubCategory(ClubCategory clubCategory) {
		this.clubCategory = clubCategory;
	}

	public Set<Coach> getCoaches() {
		return coaches;
	}

	public void setCoaches(Set<Coach> coaches) {
		this.coaches = coaches;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	@Override
	public String toString() {
		return "Club [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", owner=" + owner + ", clubCategory=" + clubCategory + ", coaches=" + coaches + ", trainings="
				+ trainings + "]";
	}
    
    
}
