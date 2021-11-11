package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Coach;

@Repository
public interface CoachRepository extends CrudRepository<Coach, Integer>{

}
