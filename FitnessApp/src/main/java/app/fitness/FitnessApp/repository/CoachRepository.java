package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Coach;

@Repository
public interface CoachRepository extends CrudRepository<Coach, Integer>{
    @Query("SELECT record From Coach record WHERE record.login=?1")
    public Coach findByLogin(String login);

    @Query("SELECT record FROM Coach record WHERE record.email=?1")
    public Coach findByEmail(String email);

}
