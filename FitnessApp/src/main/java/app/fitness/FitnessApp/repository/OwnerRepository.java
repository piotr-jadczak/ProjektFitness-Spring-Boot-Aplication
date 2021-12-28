package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Owner;

import java.time.LocalDate;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer>{
    @Query("SELECT record From Owner record WHERE record.login=?1")
    public Owner findByLogin(String login);

    @Query("SELECT record FROM Owner record WHERE record.email=?1")
    public Owner findByEmail(String email);

    public Owner findById(int id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Owner c SET c.firstName=?1, c.lastName=?2, c.email=?3, c.dob = ?4, c.phoneNumber=?5 WHERE c.id=?6")
    public void updateOwnerDetails(String firstName, String lastName, String email, LocalDate dob, String phoneNumber, int id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Owner c SET c.password =?1 WHERE c.id = ?2")
    public void updateOwnerPassword(String password, int id);

}
