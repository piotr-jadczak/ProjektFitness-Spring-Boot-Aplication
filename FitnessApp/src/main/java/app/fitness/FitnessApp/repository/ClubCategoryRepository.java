package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.ClubCategory;

import java.util.List;

@Repository
public interface ClubCategoryRepository extends CrudRepository<ClubCategory, Integer>{

}
