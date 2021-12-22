package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.Coach;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Training;

import java.util.List;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Integer>{

    List<Training> findAllByCoach(Coach coach);

}
