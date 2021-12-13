package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer>{
    @Query("SELECT record From Owner record WHERE record.login=?1")
    public Owner findByLogin(String login);

    @Query("SELECT record FROM Owner record WHERE record.email=?1")
    public Owner findByEmail(String email);

    public Owner findById(int id);
}
