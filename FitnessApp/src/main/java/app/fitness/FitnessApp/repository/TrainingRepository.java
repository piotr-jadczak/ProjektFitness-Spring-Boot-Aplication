package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Training;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Integer>{

}
