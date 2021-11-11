package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Club;

@Repository
public interface ClubRepository extends CrudRepository<Club, Integer>{

}
