package app.fitness.FitnessApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.repository.OwnerRepository;

@Service
public class CustomerServiceTest {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Bean
	public void addCustomer() throws ParseException {
		String sDate="01/12/2000";  
	    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);  
		Customer customer1 = new Customer("admin", "password", "admin@gmail.com", "Jan", "Kowalski", date , "123456789");
		
		customerRepository.save(customer1);
	}
	
	@Bean
	public void addOwner() {
		Owner owner1 = new Owner("owner", "password", "mail@gmail.com", "Piotr", "Frankowski", new Date(), "987654321");
		ownerRepository.save(owner1);
	}
}
