package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Club;

import java.util.List;

@Repository
public interface ClubRepository extends CrudRepository<Club, Integer>{
    public List<Club> findAllByOwner(Owner owner);
}
