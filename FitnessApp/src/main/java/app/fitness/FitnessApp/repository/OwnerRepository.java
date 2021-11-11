package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer>{

}
