package app.fitness.FitnessApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
