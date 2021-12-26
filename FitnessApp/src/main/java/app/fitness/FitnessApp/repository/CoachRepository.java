package app.fitness.FitnessApp.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Coach;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface CoachRepository extends CrudRepository<Coach, Integer>{

    @Query("SELECT record From Coach record WHERE record.login=?1")
    public Coach findByLogin(String login);

    @Query("SELECT record FROM Coach record WHERE record.email=?1")
    public Coach findByEmail(String email);

    public Coach findById(int id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Coach c SET c.firstName=?1, c.lastName=?2, c.email=?3, c.dob = ?4, c.phoneNumber=?5 WHERE c.id=?6")
    public void updateCoachDetails(String firstName, String lastName, String email, LocalDate dob, String phoneNumber, int id);

}
