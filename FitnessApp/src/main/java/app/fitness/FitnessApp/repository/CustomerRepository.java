package app.fitness.FitnessApp.repository;

import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Customer;

import java.time.LocalDate;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    @Query("SELECT record From Customer record WHERE record.login=?1")
    public Customer findByLogin(String login);

    @Query("SELECT record FROM Customer record WHERE record.email=?1")
    public Customer findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.firstName=?1, c.lastName=?2, c.email=?3, c.dob = ?4, c.phoneNumber=?5 WHERE c.id=?6")
    public void updateCustomerDetails(String firstName, String lastName, String email, LocalDate dob, String phoneNumber, int id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.password =?1 WHERE c.id = ?2")
    public void updateCustomerPassword(String password, int id);

}
