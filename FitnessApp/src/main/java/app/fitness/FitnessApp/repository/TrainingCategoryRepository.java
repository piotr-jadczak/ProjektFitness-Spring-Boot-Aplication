package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.TrainingCategory;

@Repository
public interface TrainingCategoryRepository extends CrudRepository<TrainingCategory, Integer>{

    public TrainingCategory findById(int id);

}
