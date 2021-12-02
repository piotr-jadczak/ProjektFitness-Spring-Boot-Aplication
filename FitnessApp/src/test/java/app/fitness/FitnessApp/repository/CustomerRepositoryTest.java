package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


/*@DataJpaTest //annotation for JPA Database test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //test works with actual database
@Rollback(value = false) //commit the changes in database
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

//    CustomerRepositoryTest(TestEntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    @Test
    public void testAddCustomer() throws ParseException {
		String sDate="01/12/2000";
	    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		Customer customer1 = new Customer("admin", "password", "admin@gmail.com", "Jan", "Kowalski", date , "123456789");

		Customer savedCustomer = customerRepository.save(customer1);

        Customer dbCustomer = entityManager.find(Customer.class, savedCustomer.getId());

        assertEquals(customer1.getLogin(), dbCustomer.getLogin());

	}

    @Test
    public void whenSaved_thenFindsByUsername() throws ParseException {
        String sDate="01/12/2000";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        Customer customer1 = new Customer("admin", "password", "admin@gmail.com", "Jan", "Kowalski", date , "123456789");
        Customer savedCustomer = customerRepository.save(customer1);

        assertEquals("admin", customerRepository.findByLogin("admin").getLogin());
    }
}*/
