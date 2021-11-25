package app.fitness.FitnessApp.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TrainingCategory {

	@Id
	@GeneratedValue
	private int id;
    private String name;
    
    @OneToMany(mappedBy = "trainingCategory")
    private Set<Training> trainings;

	public TrainingCategory() {
	}

	public TrainingCategory(String name) {
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

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	@Override
	public String toString() {
		return "TrainingCategory [id=" + id + ", name=" + name + ", trainings=" + trainings + "]";
	}
    
    

}
