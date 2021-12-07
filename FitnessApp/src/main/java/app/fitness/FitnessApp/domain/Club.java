package app.fitness.FitnessApp.domain;

import org.springframework.lang.NonNull;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "club")
public class Club {

	@Id
	@GeneratedValue
	private int id;

	@NonNull
	@Size(max = 32)
	@Column(length = 32)
    private String name;

	@NonNull
	@Size(max = 512)
	@Column(length = 512)
    private String description;

	@NonNull
	@Size(max = 32)
	@Column(length = 32)
    private String addressStreet;

	@NonNull
	@Size(max = 16)
	@Column(length = 16)
	private String addressNumber;

	@NonNull
	@Size(max = 32)
	@Column(length = 32)
	private String addressCity;

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

	public Club(String name, String description, String addressStreet, String addressNumber, String addressCity, Owner owner, ClubCategory clubCategory) {
		this.name = name;
		this.description = description;
		this.addressStreet = addressStreet;
		this.addressNumber = addressNumber;
		this.addressCity = addressCity;
		this.owner = owner;
		this.clubCategory = clubCategory;
	}

	public Club(String name, String description, String addressStreet, String addressNumber, String addressCity) {
		this.name = name;
		this.description = description;
		this.addressStreet = addressStreet;
		this.addressNumber = addressNumber;
		this.addressCity = addressCity;
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

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String address) {
		this.addressStreet = address;
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

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getFullAddress() {
		return addressStreet + " " + addressNumber + " " + addressCity;
	}
}
