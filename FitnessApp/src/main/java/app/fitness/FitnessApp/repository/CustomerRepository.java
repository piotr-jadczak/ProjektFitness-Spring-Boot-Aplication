package app.fitness.FitnessApp.repository;

import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    @Query("SELECT record From Customer record WHERE record.login=?1")
    public Customer findByLogin(String login);

}
